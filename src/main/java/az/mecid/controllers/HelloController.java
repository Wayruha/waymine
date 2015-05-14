package az.mecid.controllers;

import az.mecid.hiberdemo.AdsDao;
import az.mecid.mail.MyMail;
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
        if(principal!=null) return "redirect:/userinfo/"+adsDao.getUserByLogin(principal.getName()).getId();
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


    @RequestMapping(value = "{someAddress}",method = RequestMethod.GET)
    public ModelAndView error404(){
        return new ModelAndView("error404");

    }

    @RequestMapping(value = {"/top"},method = RequestMethod.GET)
    public ModelAndView top(Principal principal){
        ModelAndView mav=new ModelAndView("top");
        if(principal!=null)
            mav.addObject("login",principal.getName());
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
        String generetedKey=encrypt(e_mail);


        try
        {
            ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
            MyMail mail = (MyMail) context.getBean("myMail");
            mail.sendMail("Wayruha@WayMine.com", e_mail, "Registration in WayMine ", "Hello, mzfk. Here is your registration link. \r\n http://localhost:8080/registration?c="+generetedKey+"&ml="+e_mail+" But there isn`t our site in the WEB yet. ");
            System.out.println("Mail sended to " + e_mail);
        } catch (Exception e){

           return "redirect:/inviteUser?status=error";
        }
        return "redirect:/inviteUser?status=success";
    }


    @RequestMapping(value="/registration",method=RequestMethod.GET)
    public ModelAndView registration(@RequestParam("c")String code,@RequestParam("ml")String e_mail,@RequestParam(value="error",required = false) String error, Principal principal){
        ModelAndView mav=new ModelAndView();
        RegistrationCode regCode=adsDao.getRegistrationCode(code);
            if(!code.equals(encrypt(e_mail)))            //умова дешифрації
            mav.setViewName("error403");
        else {
            mav.setViewName("registration");
            mav.addObject("regCode", regCode);
            mav.addObject("error", error);
        }

        return mav;
    }

    @RequestMapping(value="/completeRegistration")
    public String registration(User user,HttpServletRequest request){
        String code=request.getParameter("registrationCode");

        if(adsDao.getUserByLogin(user.getLogin())!=null) return "registration?c="+code+"&error=login";
        adsDao.save(user);   //todo ЗБЕРЕГТИ історію


        return "redirect:/";      //реєсрація вдалася
    }

    @ModelAttribute("User")
    public User getUser() {
        return new User();
    }

    public String encrypt(String text)
    {
        char[] chrArr=text.toCharArray();
        StringBuilder strBld=new StringBuilder();
        for(int i=0;i<chrArr.length;i++) {
           char chr = chrArr[i];
            strBld.append(Character.toChars(chr * (chrArr.length - i + 1) % 74 + 48) );
        }
        return strBld.toString();
    }
   /* public String decrypt(String text)
    {
        int con=Integer.getInteger(text.substring(0, 3));
        text=text.substring(3);
        char[] chrArr=text.toCharArray();

        StringBuilder strBld=new StringBuilder();
        for(int i=0;i<chrArr.length;i++) {
            char chr = chrArr[i];
           // char newChr=chr+con*(chrArr.length-i+1)*256;
            char chsr=(char)3000;
            //strBld.append(newChr);
        }
        return strBld.toString();
    }*/
  /*  public String encrypt(String text, String keyWord)
    {
        byte[] arr = text.getBytes();
        byte[] keyarr = keyWord.getBytes();
        byte[] result = new byte[arr.length];
        for(int i = 0; i< arr.length; i++)
        {
            result[i] = (byte) (arr[i] ^ keyarr[i % keyarr.length]);
        }
        String res=new String(result);
        return res;
    }*/
   /* public String decrypt(String string, String keyWord)
    {
        byte[] text=string.getBytes();
        byte[] result  = new byte[text.length];
        byte[] keyarr = keyWord.getBytes();
        for(int i = 0; i < text.length;i++)
        {
            result[i] = (byte) (text[i] ^ keyarr[i% keyarr.length]);
        }
        return new String(result);
    }*/


}
