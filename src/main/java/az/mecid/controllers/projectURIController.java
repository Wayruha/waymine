package az.mecid.controllers;

import az.mecid.enums.TaskStatus;
import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/projects")
public class projectURIController {
    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value = "",method = RequestMethod.GET)        //    добавити в параметри методу. Доступ до імені користувача
    public ModelAndView base(Principal principal){
        ModelAndView mav=new ModelAndView("base");
        List<Project> projectsList=adsDao.getProjectsForUser(principal.getName());
        List<History> history=adsDao.getHistory(projectsList);
        mav.addObject("history",history);
        mav.addObject("projectsList",projectsList);

        return mav;
    }

    @RequestMapping(value = "/{projectId}")
    public ModelAndView projectResolver(@PathVariable("projectId") int projectId){
     ModelAndView mav=new ModelAndView("project");
           Project project=adsDao.getProjectById(projectId);
     List<Task> tasksList=adsDao.getTasksInProject(projectId);
        List<History> history=adsDao.getHistory(project);
        taskListSort(tasksList);
        mav.addObject("project",project);
        mav.addObject("history",history);
        mav.addObject("tasksList",tasksList);
        return mav;
    }

    @RequestMapping(value = "/task/{task}")
    public ModelAndView taskResolver(@PathVariable("task") int taskId){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("task");
        Task task=adsDao.getTaskById(taskId);
        List<History> history=adsDao.getHistory(task);
       List<Comment> commentList=adsDao.getCommentsInTask(taskId);
        mav.addObject("task",task);
        mav.addObject("history",history);
        mav.addObject("comments", commentList);
   return mav;
    }

    @RequestMapping(value="addComment",method = RequestMethod.GET)
    public String addComment(@RequestParam("taskId") int taskId, @RequestParam("text") String text, Principal principal){
        Task task=adsDao.getTaskById(taskId);
        Comment comm=new Comment(task,principal.getName(),text);
        adsDao.save(comm);
       return "redirect:/projects/task/"+taskId;
    }

    public void taskListSort(List<Task> taskList)
    {
       List<Task> secondList=new ArrayList<Task>();
       for(int i=0;i<taskList.size();i++){
            Task task=taskList.get(i);
           if(task.getStatus()== TaskStatus.Done){
               taskList.remove(task);
               secondList.add(task);
           }

       }
    taskList.addAll(secondList);

    }                                    //Або ж вернути новий таскЛіст, якшо будуть трабли


}
