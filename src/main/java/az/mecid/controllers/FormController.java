package az.mecid.controllers;

import az.mecid.enums.Access;
import az.mecid.enums.ProjectType;
import az.mecid.forms.ProjectForm;
import az.mecid.forms.TaskForm;
import az.mecid.forms.validators.SaveProjectValidator;
import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.Project;
import az.mecid.models.Task;
import az.mecid.models.Task_User;
import az.mecid.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping(value="/form")
public class FormController {
    @Autowired
    private SaveProjectValidator savePrjValidator;
    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value = "/editProject/{projectId}")
    public ModelAndView editProj(@PathVariable("projectId") int projectId,Principal principal){
        ModelAndView mav=new ModelAndView("editProj");
        List<ProjectType> listForRadio=new ArrayList<ProjectType>();
        listForRadio.add(ProjectType.Private);
        listForRadio.add(ProjectType.Public);
        mav.addObject("login",principal.getName());
        mav.addObject("radioTypeList",listForRadio);
        if(projectId>0){
            Project proj=adsDao.getProjectById(projectId);
            ProjectForm savePrjForm=new ProjectForm(proj);
            mav.addObject("project",savePrjForm);
        } else mav.addObject("project",new ProjectForm());



        return mav;
    }


    @RequestMapping(value="/saveProject")
    public String processEditProj(ProjectForm saveForm, BindingResult result){
        //ModelAndView mav=new ModelAndView("base");
       /* savePrjValidator.validate(saveForm,result);
        if (result.hasErrors()) {
            System.out.println("Якісь помилки при заповненні сталися. перенаправляє назад на ЕдітПродж");
            return new ModelAndView("editProj");
        } */
       if(!adsDao.isProjectByTitle(saveForm.getTitle()))
       {
           User manager=adsDao.getUserByLogin(saveForm.getManager());
           Project newProj=new Project(saveForm,manager);
           adsDao.save(newProj);
         //  adsDao.save(newProj.getManager());

           return "redirect:/projects";
       } else {
           return "redirect:/form/editProject/0";
       }
    }

    @RequestMapping(value = "/editTask/{taskId}")
    public ModelAndView editTask(@PathVariable("taskId") int taskId,Principal principal){
        Task task=adsDao.getTaskById(taskId);
        TaskForm taskForm=new TaskForm(task);
        ModelAndView mav=new ModelAndView("editTask","TaskForm",taskForm);
        List<User> userList=adsDao.getUsers(0);
        List<Task_User> t_uList=adsDao.getUserRoleInTask(taskId);
        mav.addObject("userList",userList);                     // Цюс трічку впринципі можна викидати, і юзати тільки Т_УЛІст
        mav.addObject("t_uList",t_uList);
        mav.addObject("login",principal.getName());
        if(taskId>0){

            mav.addObject("task",taskForm);
            mav.addObject("projectId",task.getProject().getId());
            mav.addObject("editing",true);

        } else mav.addObject("project",new TaskForm());

        return mav;
    }
    @RequestMapping(value = "/createTask/{projectId}")
    public ModelAndView createTask(@PathVariable("projectId") int projectId,@RequestParam(required = false) String error,Principal principal){
        System.out.println(error);
        ModelAndView mav=new ModelAndView("editTask");
        List<User> userList=adsDao.getUsers(projectId);
        mav. addObject("login",principal.getName());
        mav.addObject("userList",userList);
        mav.addObject("task",new TaskForm());
        mav.addObject("projectId",projectId);
        mav.addObject("error",error);

        return mav;
    }



    @RequestMapping(value = "/saveTask")
    public String processEditTask(@RequestParam("project") int projectId,TaskForm saveForm, BindingResult result){
     //   ModelAndView mav=new ModelAndView("base");
       /* savePrjValidator.validate(saveForm,result);
        if (result.hasErrors()) {
            System.out.println("Якісь помилки при заповненні сталися. перенаправляє назад на ЕдітПродж");
            return new ModelAndView("editProj");
        } */
        ModelAndView mav=new ModelAndView();
        if(!adsDao.isTaskByTitle(saveForm.getTitle()) || saveForm.getEditing()!=null)
        {

            String[] arrUsers=saveForm.getUserList().split(" %%");
            String[] arrAccesses=saveForm.getAccessList().split(" %%");
            List<Task_User> userList= new ArrayList<Task_User>();
            System.out.println("creator"+saveForm.getCreator());                                  /////////////
            Task newTask=new Task(saveForm);
            newTask.setProject(adsDao.getProjectById(projectId));
                  Date date=new Date(new java.util.Date().getTime());
            newTask.setDateOfCreating(date);

            adsDao.save(newTask);
            for(int i=0;i<arrUsers.length;i++){
                System.out.println(arrUsers[i]+" - Один з користувачів");
                 User user= adsDao.getUserByLogin(arrUsers[i].trim());
                Access access=Access.valueOf(arrAccesses[i].trim());
                 Task_User t_u=new Task_User(newTask,user,access);
                 adsDao.save(t_u);
            }
            return "redirect:/projects/"+projectId;
        } else {
            //Зробити ЕРОРИ
            System.out.println("Виникли трабли. Повернули назад на сторінку");
            mav.setViewName("editTask");
            mav.addObject("projectId", projectId);
            return "redirect:/form/createTask/"+projectId+"?error=title";
        }
    }
    @ModelAttribute("ProjectForm")
    public ProjectForm getProjectForm() {
        return new ProjectForm();
    }
    @ModelAttribute("TaskForm")
    public TaskForm getTaskForm() {
        return new TaskForm();
    }

}
