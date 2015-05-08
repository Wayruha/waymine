package az.mecid.controllers;

import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.Task_User;
import az.mecid.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value="userinfo/{user}")
    public ModelAndView getUserProfile(@PathVariable("user") int id){
        ModelAndView mav=new ModelAndView("userProfile");
        User user =adsDao.getUserById(id);
        if(user==null) return  new ModelAndView("error403");
        List<Task_User> list=adsDao.getTasksByUser(id);
        mav.addObject("user",user);
        mav.addObject("task_userList",list);
        return mav;
    }


    @RequestMapping(value = "getUsers/{from}")
    public ModelAndView getUsers (@PathVariable("from") int projectId){
        ModelAndView mav=new ModelAndView("users");
        List<User> usersList=adsDao.getUsers(projectId);
        mav.addObject("usersList",usersList);
        return mav;
    }
    @RequestMapping(value = "getUsersInTask/{from}")
    public ModelAndView getUsersInTask (@PathVariable("from") int taskId){
        ModelAndView mav=new ModelAndView("users");
        List<Task_User> t_uList=adsDao.getUsersRolesInTask(taskId);
        mav.addObject("t_uList",t_uList);

        return mav;
    }


}
