package az.mecid.controllers;

import az.mecid.enums.Access;
import az.mecid.enums.HistoryAction;
import az.mecid.enums.ProjectType;
import az.mecid.forms.ProjectForm;
import az.mecid.forms.TaskForm;
import az.mecid.forms.validators.SaveProjectValidator;
import az.mecid.forms.validators.SaveTaskValidator;
import az.mecid.hiberdemo.AdsDao;
import az.mecid.models.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value="/form")
public class FormController {

    private SaveProjectValidator savePrjValidator=new SaveProjectValidator();
    private SaveTaskValidator saveTaskValidator=new SaveTaskValidator();
    @Autowired
    private AdsDao adsDao;

    @RequestMapping(value = "/editProject/{projectId}")
    public ModelAndView editProj(@PathVariable("projectId") int projectId,@RequestParam(required = false) String error,Principal principal){
        ModelAndView mav=new ModelAndView("editProj");
        List<ProjectType> listForRadio=new ArrayList<ProjectType>();
        listForRadio.add(ProjectType.Private);
        listForRadio.add(ProjectType.Public);
        Project proj=adsDao.getProjectById(projectId);
        ProjectForm savePrjForm=new ProjectForm(proj);

        mav.addObject("login",principal.getName());
        mav.addObject("radioTypeList",listForRadio);
        mav.addObject("project",savePrjForm);
        mav.addObject("editing",true);
        mav.addObject("error",error);
        return mav;
    }

    @RequestMapping(value = "/createProject")
    public ModelAndView editProj(@RequestParam(required = false) String error,Principal principal){
        ModelAndView mav=new ModelAndView("editProj");
        List<ProjectType> listForRadio=new ArrayList<ProjectType>();
        listForRadio.add(ProjectType.Private);
        listForRadio.add(ProjectType.Public);

        mav.addObject("login",principal.getName());
        mav.addObject("radioTypeList",listForRadio);
        mav.addObject("project",new ProjectForm());
        mav.addObject("error",error);
        return mav;
    }

    @RequestMapping(value="/saveProject")
    public String processEditProj(ProjectForm saveForm, Principal principal){
        boolean editing=saveForm.getEditing()!=null;
        String managerStr = saveForm.getManager();


        User manager;                                 //це краще не трогати
        if(!managerStr.isEmpty())
            if(adsDao.getUsersByLogin(managerStr).size()==0)
                if (saveForm.getEditing()!=null)
                    return "redirect:/form/editProject/?error=manager";
                else
                    return "redirect:/form/createProject/?error=manager";
            else
                manager=adsDao.getUserByLogin(saveForm.getManager());
        else
        {
            manager=adsDao.getUserByLogin(principal.getName());
        }
                                                                    //перевірка на коректність введеного менеджера

        if (savePrjValidator.hasErrors(saveForm)) {
           System.out.println("Якісь помилки при заповненні сталися. перенаправляє назад на ЕдітПродж");
            if (saveForm.getEditing()!=null)
                return "redirect:/form/editProject/?error=valid";
            else
                return "redirect:/form/createProject/?error=valid";
        }

       if(!adsDao.isProjectByTitle(saveForm.getTitle()) || editing)
       {
           Project oldProject=adsDao.getProjectById(saveForm.getId());



           Project newProj=new Project(saveForm,manager);
           History history=new History();
           history.setActor(manager);
           if(editing) {
               compareTwoObjects(oldProject,newProj,true,history);
               adsDao.update(newProj);
           }
           else
           {
               history.setAction(HistoryAction.create_project);
               history.setObject(newProj.getName());
               adsDao.save(newProj);
           }

           return "redirect:/projects";
       } else {
           //Значить виникли трабли   з назвою
           return "redirect:/form/createProject?error=title";
       }
    }

    @RequestMapping(value = "/editTask/{taskId}")
    public ModelAndView editTask(@PathVariable("taskId") int taskId,Principal principal){
        Task task=adsDao.getTaskById(taskId);
        TaskForm taskForm=new TaskForm(task);
        ModelAndView mav=new ModelAndView("editTask","TaskForm",taskForm);
        List<User> userList=adsDao.getUsers(0);
        List<Task_User> t_uList=adsDao.getUserRoleInTask(taskId);
       // mav.addObject("userList",userList);                     // Цюс трічку впринципі можна викидати, і юзати тільки Т_УЛІст
        mav.addObject("t_uList",t_uList);
        mav.addObject("login",principal.getName());
        if(taskId>0){
            mav.addObject("taskId",taskId);
            mav.addObject("task",taskForm);
            mav.addObject("projectId",task.getProject().getId());
            mav.addObject("editing",true);

        } else mav.addObject("project",new TaskForm());

        return mav;
    }

    @RequestMapping(value = "/createTask/{projectId}")
    public ModelAndView createTask(@PathVariable("projectId") int projectId,@RequestParam(required = false) String error,Principal principal){
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

        System.out.println(saveForm.getTitle()+" тітл Сейв таск");
        System.out.println(saveForm.getId()+" ID Сейв таск");
        System.out.println(saveForm.getEditing()+" --Едітінг");
        boolean editing=saveForm.getEditing()!=null?true:false;
        if (saveTaskValidator.hasErrors(saveForm)) {
            System.out.println("Помилка: "+result.getAllErrors().get(0).getDefaultMessage());
            System.out.println("Якісь помилки при заповненні сталися. перенаправляє назад на ЕдітПродж");
                if (saveForm.getEditing()!=null)
                    return "redirect:/form/editTask/"+saveForm.getId()+"?error=valid";
                else
                    return "redirect:/form/createTask/"+projectId+"?error=valid";
        }
        if(!adsDao.isTaskByTitle(saveForm.getTitle()) || editing) //Якщо редагується або нік не існує
        {
            String[] arrUsers=saveForm.getUserList().split("--");
            String[] arrAccesses=saveForm.getAccessList().split("--");
            User creator=adsDao.getUserByLogin(saveForm.getCreator());
            Task newTask=new Task(saveForm,creator);
            newTask.setProject(adsDao.getProjectById(projectId));
            newTask.setDateOfCreating(new Date(new java.util.Date().getTime()));
            Set<Task_User> t_uList= new HashSet<Task_User>();                   /////////////

            for (int i=0;i<arrUsers.length;i++){                                 //Генеруємо масив <Task_User>
                if(arrUsers[i].trim().length()>1){
                    Task_User t_u=new Task_User();
                    t_u.setUser(adsDao.getUserByLogin(arrUsers[i]));
                    t_u.setTask(newTask);
                    System.out.println(arrAccesses[i]+"-");
                    t_u.setAccess(Access.valueOf(arrAccesses[i].trim()));
                    t_uList.add(t_u);
                }
            }
            newTask.setTaskAndUserList(t_uList);
            Task oldTask=adsDao.getTaskById(saveForm.getId());
            //Порівнюємо старий і новий таски
            History history=new History();
            history.setActor(creator);
            if(editing) {
                compareTwoObjects(oldTask,newTask, false,history);
                adsDao.update(newTask);
            }
            else
            {
                history.setAction(HistoryAction.create_task);
                history.setObject(newTask.getTitle());
                adsDao.save(newTask);
            }

            for(int i=0;i<arrUsers.length;i++){
                if(arrUsers[i].trim().length()>1)
                {
                    User user= adsDao.getUserByLogin(arrUsers[i].trim());
                    Access access=Access.valueOf(arrAccesses[i].trim());
                    Task_User t_u=new Task_User(newTask,user,access);
                    adsDao.save(t_u);
                }
            }
            //ІСТОРІЯ!!
          //  History history=new History(creator,HistoryAction.create_task,newTask.getTitle());    //TODO тут ЗМІНА! ВІдслідкувати по аналогії з СейвПроект


            return "redirect:/projects/"+projectId;
        } else {
            //Зробити ЕРОРИ
            System.out.println("Тітл уже існує!");
            return "redirect:/form/createTask/"+projectId+"?error=title";
        }
    }

    public void compareTwoObjects(DataEntity old,DataEntity _new, boolean isProj,History history){
        //Історія зберігається тут
        /*if(oldTask.getDescription()!=newTask.getDescription()) history.setAction(HistoryAction.change_task_description);
        if(oldTask.getStatus()!=newTask.getStatus()) history.setAction(HistoryAction.change_task_status);
        */
        if(!isProj)
        {
            Task oldTask=(Task) old;
            Task newTask=(Task) _new;
            history.setObject(oldTask.getTitle());
            if(oldTask.getDescription()!=newTask.getDescription())System.out.println("ТСК ДЕСКР"); //history.setAction(HistoryAction.change_task_description);
            if(oldTask.getStatus()!=newTask.getStatus())System.out.println("ТСК САТУ");// history.setAction(HistoryAction.change_task_status);
            if(history.getAction()!=null) adsDao.save(history);

            List<Task_User> t_uList=new ArrayList<Task_User>();
            Set<Task_User> t_uListOld=oldTask.getTaskAndUserList();
            Set<Task_User> t_uListNew=newTask.getTaskAndUserList();
            for(Task_User t_u:oldTask.getTaskAndUserList())
            if(!t_uListNew.contains(t_u))
            {
                System.out.println("Видалено користувача "+t_u.getUser().getLogin());
                history.setAction(HistoryAction.delete_user);
                history.setObject(t_u.getUser().getLogin()+"("+t_u.getAccess()+")");
                adsDao.save(history);
            }

            for(Task_User t_u:newTask.getTaskAndUserList())
            if(!t_uListOld.contains(t_u))
            {
                System.out.println("Додано користувача "+t_u.getUser().getLogin());
                history.setAction(HistoryAction.add_user);
                history.setObject(t_u.getUser().getLogin()+"("+t_u.getAccess()+")");
                adsDao.save(history);
            }

        }else
        {
            Project oldProj=(Project)old;
            Project newProj=(Project)_new;
            history.setObject(oldProj.getName());
            if(oldProj.getDescription()!=newProj.getDescription()) history.setAction(HistoryAction.change_project_description);
            if(oldProj.getType()!=newProj.getType()) history.setAction(HistoryAction.change_project_type);
            if(oldProj.getManager()!=newProj.getManager()) history.setAction(HistoryAction.change_project_manager);
            if(history.getAction()!=null) adsDao.save(history);

        }


    }

    public void validateManager(){

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
