package az.mecid.controllers;

import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.Project;
import az.mecid.models.Task;
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

    @RequestMapping(value = "editProject/{projectId}")
    public ModelAndView editProj(@PathVariable("projectId") int projectId){
        ModelAndView mav=new ModelAndView("editProj");
        if(projectId>0){
            Project proj=adsDao.getProjectById(projectId);
           mav.addObject("project",proj);
          //  System.out.println(proj.get);
        }

        return mav;
    }

    @RequestMapping(value = "editTask/{taskId}")
    public ModelAndView editTask(@PathVariable("taskId") int taskId){
        ModelAndView mav=new ModelAndView("editTask");
        if(taskId>0){
            Task task=adsDao.getTaskById(taskId);
            List<User> userList=adsDao.getUsers("all");
            mav.addObject("task",task);
            mav.addObject("userList",userList);
        }

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
