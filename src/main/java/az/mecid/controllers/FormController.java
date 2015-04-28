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
        Project project=adsDao.getProjectById(projectId);
        ProjectForm projectForm=new ProjectForm(project);
        List<User> allUserList=adsDao.getUsers(0);
        List<User> userListInProject=adsDao.getUsers(projectId);
        projectForm.setEditing(true);

        mav.addObject("userListInProject",userListInProject);
        mav.addObject("allUserList",allUserList);
        mav.addObject("login",principal.getName());
        mav.addObject("radioTypeList",listForRadio);
        mav.addObject("project",projectForm);

        mav.addObject("error",error);
        return mav;
    }

    @RequestMapping(value = "/createProject")
    public ModelAndView editProj(@RequestParam(required = false) String error,Principal principal){
        ModelAndView mav=new ModelAndView("editProj");
        List<ProjectType> listForRadio=new ArrayList<ProjectType>();
        listForRadio.add(ProjectType.Private);
        listForRadio.add(ProjectType.Public);
        List<User> allUserList=adsDao.getUsers(0);
        mav.addObject("allUserList",allUserList);
        mav.addObject("login",principal.getName());
        mav.addObject("radioTypeList",listForRadio);
        mav.addObject("project",new ProjectForm());
        mav.addObject("error",error);
        return mav;
    }

    @RequestMapping(value="/saveProject")
    public String processEditProj(ProjectForm saveForm, Principal principal){
        boolean editing=saveForm.getEditing();
        String managerStr = saveForm.getManager();
        User manager;                                 //це краще не трогати
        if(!managerStr.trim().isEmpty())
            if(adsDao.getUsersByLogin(managerStr).size()==0)
                if (saveForm.getEditing()==true)
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
            if (editing)
                return "redirect:/form/editProject/?error=valid";
            else
                return "redirect:/form/createProject/?error=valid";
        }

       if(!adsDao.isProjectByTitle(saveForm.getTitle()) || editing)
       {
           Project oldProject=adsDao.getProjectById(saveForm.getId());  //Буде тільки якщо редагується проект
           String[] arrUsers=saveForm.getUserList().split("--");
           List<User>newUserList=new ArrayList<User>();
           for (String login:arrUsers)
                newUserList.add(adsDao.getUserByLogin(login.trim()));

           Project newProj=new Project(saveForm,manager);
           History history=new History();
           history.setActor(manager);
           if(editing) {
               compareProjects(oldProject,newProj,history,newUserList);
               adsDao.update(newProj);
             /*  for (int i=0;i<arrUsers.length;i++)
                   if(arrUsers[i].trim().length()>1){
                        adsDao.addNewUserInProject(adsDao.getUserByLogin(arrUsers[i]),newProj);

                   }*/
           }
           else
           {
               history.setAction(HistoryAction.create_project);
               history.setObject(newProj.getName());
               newProj.setCreatedDate(new Date(new java.util.Date().getTime()));
               adsDao.save(newProj);
               adsDao.save(history);

               History t_uHistory=new History();
               t_uHistory.setActor(manager);
               t_uHistory.setAction(HistoryAction.add_user_to_project);
               User user;
               for (int i=0;i<arrUsers.length;i++)
                   if(arrUsers[i].trim().length()>1){
                       user=adsDao.getUserByLogin(arrUsers[i]);
                       adsDao.addNewUserInProject(user,newProj);
                       t_uHistory.setObject(user.getLogin()+" в проект "+saveForm.getTitle());
                       adsDao.save(t_uHistory);
                   }

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
        taskForm.setEditing(true);
        ModelAndView mav=new ModelAndView("editTask","TaskForm",taskForm);
        List<User> userList=adsDao.getUsers(task.getProject().getId());
        List<Task_User> t_uList=adsDao.getUsersRolesInTask(taskId);
        mav.addObject("userList",userList);
        mav.addObject("t_uList",t_uList);
        mav.addObject("login",principal.getName());

            mav.addObject("taskId",taskId);
            mav.addObject("task",taskForm);
            mav.addObject("projectId",task.getProject().getId());


        return mav;
    }

    @RequestMapping(value = "/createTask/{projectId}")
    public ModelAndView createTask(@PathVariable("projectId") int projectId,@RequestParam(required = false) String error,Principal principal){
        ModelAndView mav=new ModelAndView("editTask");
        List<User> userList=adsDao.getUsers(projectId);
        mav.addObject("userList",userList);
        mav.addObject("login",principal.getName());
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
        boolean editing=saveForm.getEditing();
        if (saveTaskValidator.hasErrors(saveForm)) {
            System.out.println("Помилка: "+result.getAllErrors().get(0).getDefaultMessage());
            System.out.println("Якісь помилки при заповненні сталися. перенаправляє назад на ЕдітПродж");
                if (editing)
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

    public void compareTwoObjects(DataEntity old,DataEntity _new, boolean isProj,History history){           //TODO!! шо за нах? Зробити аналогічно Проекту
        //Історія зберігається тут
        /*if(oldTask.getDescription()!=newTask.getDescription()) history.setAction(HistoryAction.change_task_description);
        if(oldTask.getStatus()!=newTask.getStatus()) history.setAction(HistoryAction.change_task_status);
        */
        if(!isProj)
        {
            Task oldTask=(Task) old;
            Task newTask=(Task) _new;
            history.setObject(oldTask.getTitle());
            if(oldTask.getDescription()!=newTask.getDescription())history.setAction(HistoryAction.change_task_description);
            if(oldTask.getStatus()!=newTask.getStatus())history.setAction(HistoryAction.change_task_status);
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
                history.setObject(t_u.getUser().getLogin() + "(" + t_u.getAccess() + ")");
                history.setInTask(newTask.getTitle());
                adsDao.save(history);
            }

        }
    }

    public void compareProjects(Project oldProj,Project newProj, History history,List<User> newUserList){

        history.setObject(oldProj.getName());
        if(oldProj.getDescription()!=newProj.getDescription()) history.setAction(HistoryAction.change_project_description);
        if(oldProj.getType()!=newProj.getType()) history.setAction(HistoryAction.change_project_type);
        if(oldProj.getManager()!=newProj.getManager()) history.setAction(HistoryAction.change_project_manager);
        if(history.getAction()!=null) adsDao.save(history);

        History usersHistory=new History();
        usersHistory.setActor(oldProj.getManager());

        List<User>oldUserList=adsDao.getUsers(oldProj.getId());
        List<String> oldUserLoginList=new ArrayList<String>();
        List<String> newUserLoginList=new ArrayList<String>();
        for(User user:oldUserList)  {
            System.out.println("OLD_userId:"+user.getLogin());
            oldUserLoginList.add(user.getLogin());
        }

        for(User user:newUserList) {
            System.out.println("NEW_userId:"+user.getLogin());
            newUserLoginList.add(user.getLogin());
        }                                                                                         //// костилі( 2 години роботи ...(

        User user;
        for(String userLogin:oldUserLoginList){
            if(!newUserLoginList.contains(userLogin))
            {
                user=adsDao.getUserByLogin(userLogin);
                System.out.println("Видалено користувача "+user.getLogin());
                usersHistory.setAction(HistoryAction.delete_user_from_project);
                usersHistory.setObject(user.getLogin()+" з проекту "+newProj.getName());
                adsDao.deleteUserFromProject(user,newProj);
                adsDao.save(usersHistory);
            }
        }
        for(String userLogin:newUserLoginList)
            if(!oldUserLoginList.contains(userLogin))
            {
                user=adsDao.getUserByLogin(userLogin);
                System.out.println("Додано користувача "+user.getLogin());
                usersHistory.setAction(HistoryAction.add_user_to_project);
                usersHistory.setObject(user.getLogin() + " в проект " + newProj.getName());
                adsDao.addNewUserInProject(user,newProj);
                adsDao.save(usersHistory);
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
