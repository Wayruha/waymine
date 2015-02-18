package az.mecid.controllers;

import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HelloController {

    @Autowired
    private AdsDao adsDao;

 /*   @RequestMapping(value = "redirect/{to}",method = RequestMethod.GET)
    public String redirectTo(@PathVariable("to") String name){

        return "redirect:/"+name;
    }                         */
 @RequestMapping(value = {"/topFromProj}","topFromProj"},method = RequestMethod.GET)
 public ModelAndView top(){
     ModelAndView mav=new ModelAndView("top");
      System.out.println("here");
     mav.addObject("isProjectPage", true);
     return mav;
 }

    @RequestMapping(value = {"/{page}","{page}"},method = RequestMethod.GET)
    public String to(@PathVariable("page") String pageName){
        return pageName;
    }


    @RequestMapping(value = "create.form")
    public String create(@RequestParam("login") String login,ModelMap model) {
        User user1 = new User();
        user1.setId(10);
        user1.setFirstName("Паша");

        user1.setSecondName("Павлов");
        user1.setLogin("unknown");
        user1.setPassword("password");
        user1.seteMail("e-mail2");

        adsDao.save(user1);
        return "hello";
    }

    @RequestMapping(value = "update.form",method = RequestMethod.GET)
    public String loginController(@RequestParam("login") String login, ModelMap model){
        model.addAttribute("login",login);
        return "index";
    }



    @RequestMapping(value = "getUsers/{from}")
    public ModelAndView getUsers (@PathVariable("from") String projectName){
        ModelAndView mav=new ModelAndView("users");
        List<User> usersList=adsDao.getUsers(projectName);
        mav.addObject("usersList",usersList);

        return mav;
    }


    @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET,headers = {"Accept=text/xml, application/json"})
    @ResponseBody
    public  Set<String> ajaxTest() {
        Set<String> records = new HashSet<String>();
        records.add("Record #1");
        records.add("Record #2");

        return records;
    }


}
