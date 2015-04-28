package az.mecid.controllers;

import az.mecid.enums.Access;
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
@RequestMapping(value = "/projects")
public class projectURIController {
    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    //    добавити в параметри методу. Доступ до імені користувача
    public ModelAndView base(Principal principal) {
        ModelAndView mav = new ModelAndView("base");
        User user = adsDao.getUserByLogin(principal.getName());
        List<Project> projectsList = adsDao.getProjectsForUser(user);
        List<History> history = adsDao.getHistory(projectsList);
        mav.addObject("history", history);
        mav.addObject("projectsList", projectsList);

        return mav;
    }

    @RequestMapping(value = "/info/{projectId}")
    public ModelAndView projectInfo(@PathVariable("projectId") int projectId, Principal principal) {
        ModelAndView mav = new ModelAndView("projectInfo");
        Project project = adsDao.getProjectById(projectId);

        User user = adsDao.getUserByLogin(principal.getName());
        List<Project> accessGrantedProjectsList = adsDao.getProjectsForUser(user);            //TODO Доступ мають мати тільки менеджери і Адмін

        boolean contains = false;
        for (Project proj : accessGrantedProjectsList)
            if (proj.getId() == projectId) contains = true;
        if (!contains)        //Перевірка на доступ
            return new ModelAndView("error403");

        List<Task> tasksList = adsDao.getTasksInProject(projectId);
        taskListSort(tasksList);
        List<Task_User> t_uList = adsDao.getTUByProject(project);
        List<Integer> spentTimeList = new ArrayList<Integer>();                       //TODO можуть бути помилки з порівнянням(
        for(Task task:tasksList){
            int countHours = 0;
            for(Task_User t_u:t_uList)
                if(task.getTitle().equals(t_u.getTask().getTitle())) if(t_u.getSpentTime()!=null) countHours+=t_u.getSpentTime();
            spentTimeList.add(countHours);

        }

        mav.addObject("project", project);
        mav.addObject("spentTimeList", spentTimeList);
        mav.addObject("tasksList", tasksList);
        return mav;
    }


    @RequestMapping(value = "/{projectId}")
    public ModelAndView projectResolver(@PathVariable("projectId") int projectId, Principal principal) {
        ModelAndView mav = new ModelAndView("project");
        Project project = adsDao.getProjectById(projectId);
        if(project==null) return new ModelAndView("error404");

        List<Task> tasksList = adsDao.getTasksInProject(projectId);
        User user = adsDao.getUserByLogin(principal.getName());
        List<Project> accessGrantedProjectsList = adsDao.getProjectsForUser(user);

        boolean contains = false;
        for (Project proj : accessGrantedProjectsList)
            if (proj.getId() == projectId) contains = true;
        if (!contains)        //Перевірка на доступ
            return new ModelAndView("error403");


        List<History> history = adsDao.getHistory(project);
        taskListSort(tasksList);
        mav.addObject("project", project);
        mav.addObject("history", history);
        mav.addObject("tasksList", tasksList);
        return mav;
    }

    @RequestMapping(value = "/task/{task}")
    public ModelAndView taskResolver(@PathVariable("task") int taskId, Principal principal) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("task");
        User user = adsDao.getUserByLogin(principal.getName());
        Task task = adsDao.getTaskById(taskId);
        if(task==null) return new ModelAndView("error404");
        Project project = task.getProject();

        boolean contains = false;
        for (Project proj : adsDao.getProjectsForUser(user))
            if (proj.getName().equals(project.getName()))
                contains = true;

        if (!contains)        //Перевірка на доступ
            return new ModelAndView("error403");

        List<History> history = adsDao.getHistory(task);
        List<Comment> commentList = adsDao.getCommentsInTask(taskId);
        Task_User t_u = adsDao.getAccessInTask(user, task);

        if(t_u!=null){
        if (t_u.getSpentTime() == null) t_u.setSpentTime(0);
        if (t_u.getLastFixedActivity() == null) t_u.setLastFixedActivity(t_u.getTask().getDateOfCreating());
        if (t_u.getTask().getPlannedTime() == null) t_u.getTask().setPlannedTime(0);
        }

        Access role;

        if (t_u == null) role = null;
        else role = t_u.getAccess();
        if (project.getManager().getLogin().equals(user.getLogin())) role = Access.Observer; //not null

        mav.addObject("role", role);
        mav.addObject("task",task);
        mav.addObject("t_u", t_u);
        mav.addObject("history", history);
        mav.addObject("comments", commentList);
        return mav;
    }

    @RequestMapping(value = "addComment", method = RequestMethod.GET)
    public String addComment(@RequestParam("taskId") int taskId, @RequestParam("text") String text, Principal principal) {
        Task task = adsDao.getTaskById(taskId);
        Comment comm = new Comment(task, principal.getName(), text);
        adsDao.save(comm);
        return "redirect:/projects/task/" + taskId;
    }

    public void taskListSort(List<Task> taskList) {
        List<Task> secondList = new ArrayList<Task>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getStatus() == TaskStatus.Done) {
                taskList.remove(task);
                secondList.add(task);
            }

        }
        taskList.addAll(secondList);

    }                                    //Або ж вернути новий таскЛіст, якшо будуть трабли

    public void checkForAccess() {

    }
}
