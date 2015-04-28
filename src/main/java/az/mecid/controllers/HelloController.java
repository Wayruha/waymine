package az.mecid.controllers;

import az.mecid.enums.HistoryAction;
import az.mecid.hiberdemo.AdsDao;
import az.mecid.mail.MyMail;
import az.mecid.models.History;
import az.mecid.models.Project;
import az.mecid.models.RegistrationCode;
import az.mecid.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HelloController {

    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value = {"/","","index","index.jsp"})
    public String index(Principal principal){
        if(principal!=null) return "home";
        else return "index";


    }

 @RequestMapping(value = {"/topFromProj","topFromProj"})
 public ModelAndView top(@RequestParam("project") int projectId,Principal principal){
     ModelAndView mav=new ModelAndView("top");
     mav.addObject("isProjectPage", true);
     Project project=adsDao.getProjectById(projectId);
     mav.addObject("project",project);
     mav.addObject("login",principal.getName());
     return mav;
 }


    @RequestMapping(value = "saveWorkedTime/{taskId}/{spentTime}")
    public String saveWorkedTime(@PathVariable("taskId") int taskId,@PathVariable("spentTime") int spentTime,@RequestParam("workedTime") int workedTime,HttpServletRequest request,Principal principal){
        adsDao.increaseSpentTime(taskId,principal.getName(),spentTime+workedTime);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;

    }


    @RequestMapping(value = {"/top","{top}"},method = RequestMethod.GET)
    public ModelAndView to(Principal principal){
        ModelAndView mav=new ModelAndView("top");
        if(principal!=null){
            System.out.println(principal.getName());
            mav.addObject("login",principal.getName());
        }
        return mav;
    }


    @RequestMapping(value = "delete/project/{projectId}")
    public String create(@PathVariable("projectId") int projectId) {
        adsDao.deleteProject(projectId);
       return "redirect:/error403";
    }

    @RequestMapping(value = "/inviteUser", method = RequestMethod.GET)
    public ModelAndView inviteUser(@RequestParam(value="status",required = false)String status,Principal principal) {
        ModelAndView mav=new ModelAndView("inviteUser");
        User user=adsDao.getUserByLogin(principal.getName());
        mav.addObject("user",user);
        mav.addObject("status",status);
        return mav;
    }

    @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
    public String inviteUserSendMail(HttpServletRequest request,Principal principal) {
        String e_mail=request.getParameter("e_mail");
        String code=request.getParameter("generatedCode");
        User user=adsDao.getUserByLogin(principal.getName());
            boolean type = request.getParameter("isManager") == "true" ? true : false; //TODO відправляється повідомлення на меіл. Робиться запис в БД з кодом доступу
            if (code.length() < 2) return "Back";
            while (adsDao.getRegistrationCode(code)!=null)    //якшо такий код є то модифікуєм його)
                code += "-2";
            RegistrationCode regCode = new RegistrationCode(code, type,user);
            adsDao.save(regCode);
        try
        {
            ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
            MyMail mail = (MyMail) context.getBean("myMail");
            mail.sendMail("Wayruha@WayMine.com", e_mail, "Registration in WayMine ", "Hello, mzfk. Here is your registration link. \r\n http.www.waymine.com/registration?c="+code+" But there isn`t our site in the WEB yet. ");
            System.out.println("Mail sended to " + e_mail);
        } catch (Exception e){
            adsDao.delete(regCode);
           return "redirect:/inviteUser?status=error";
        }
        return "redirect:/inviteUser?status=success";
    }


    @RequestMapping(value="/registration",method=RequestMethod.GET)
    public ModelAndView registration(@RequestParam("c")String code,@RequestParam(value="error",required = false) String error, Principal principal){
        ModelAndView mav=new ModelAndView();
        RegistrationCode regCode=adsDao.getRegistrationCode(code);                     /////ПРОБЛЕМИ
        if(regCode==null || regCode.getIsRegistered()==true)
            mav.setViewName("error403");
        else {
            mav.setViewName("registration");
            mav.addObject("regCode", regCode);
            mav.addObject("error", error);
        }

        return mav;
    }

    @RequestMapping(value="/completeRegistration",method=RequestMethod.POST)
    public String registration(User user,HttpServletRequest request){
        String code=request.getParameter("registrationCode");
        RegistrationCode regCode=adsDao.getRegistrationCode(code);
        if(regCode==null) return "registration?c="+code+"&error=unknown";
        if(adsDao.getUserByLogin(user.getLogin())!=null) return "registration?c="+code+"&error=login";
        adsDao.save(user);   //todo ЗБЕРЕГТИ історію
        regCode.setIsRegistered(true);
        regCode.setUser(user);

        adsDao.update(regCode);

        adsDao.save(new History(regCode.getInvitedBy(), HistoryAction.invite_user,user.getLogin()));
        return "redirect:/";      //реєсрація вдалася
    }

    @ModelAttribute("User")
    public User getUser() {
        return new User();
    }

}
