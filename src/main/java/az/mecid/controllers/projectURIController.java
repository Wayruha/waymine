package az.mecid.controllers;

import az.mecid.enums.TaskStatus;
import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.Comment;
import az.mecid.models.Project;
import az.mecid.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/projects")
public class projectURIController {
    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ModelAndView base(){
        List<Project> projectsList = adsDao.getAllProjects();
        ModelAndView mav=new ModelAndView();
        mav.setViewName("base");
        mav.addObject("projectsList",projectsList);
        return mav;
    }

    @RequestMapping(value = "/{project}")
    public ModelAndView projectResolver(@PathVariable("project") String projectName){
     ModelAndView mav=new ModelAndView();
                 mav.setViewName("project");
                 mav.addObject("projectName",projectName);
     List<Task> tasksList=adsDao.getTasksInProject(projectName);
        taskListSort(tasksList);
        mav.addObject("tasksList",tasksList);
        return mav;
    }

    @RequestMapping(value = "/task/{task}")
    public ModelAndView taskResolver(@PathVariable("task") int taskId){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("task");
        Task task=adsDao.getTaskById(taskId);
       List<Comment> commentList=adsDao.getCommentsInTask(taskId);
        mav.addObject("task",task);
        mav.addObject("comments", commentList);
   return mav;
    }

    @RequestMapping(value="addComment",method = RequestMethod.GET)
    public String addComment(@RequestParam("taskId") int taskId, @RequestParam("login") String login, @RequestParam("text") String text){
        Task task=adsDao.getTaskById(taskId);
        Comment comm=new Comment(task,login,text);
        adsDao.save(comm);
       return "redirect:/projects/task/"+taskId;
    }

    public void taskListSort(List<Task> taskList)
    {
       List<Task> secondList=new ArrayList<Task>();
       for(Task task:taskList)
           if(task.getStatus()== TaskStatus.Done){
               taskList.remove(task);
               secondList.add(task);
           } taskList.addAll(secondList);
                                           //Або ж вернути новий таскЛіст, якшо будуть трабли
    }

}
