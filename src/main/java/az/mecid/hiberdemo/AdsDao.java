package az.mecid.hiberdemo;


import az.mecid.enums.ProjectType;
import az.mecid.models.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class AdsDao extends HibernateDaoSupport {

    public void save(DataEntity entity) {
        getHibernateTemplate().save(entity);
    }
    public void update(DataEntity entity) {
        getHibernateTemplate().update(entity);
    }

    public List<History> getHistory(List<Project> projectList){
        List<String> list=new ArrayList<String>();
        for (Project proj:projectList)
            list.add(proj.getName());
        return (List<History>) getHibernateTemplate().findByNamedParam("SELECT post FROM History as post WHERE post.action IN ('create_project','delete_project', 'change_project_description','change_project_manager','change_project_type')"+
              " AND post.object IN (:list)","list",list); //Сюди не дивись, так зроблено просто шо б ерору не було. А так-то воно вибирає історію з певних проектів.
    }
    public List<History> getHistory(Project project){
        List<String> list=new ArrayList<String>();
        list.add(project.getName());
        for(Task task:getTasksInProject(project.getId()))
            list.add(task.getTitle());
        return (List<History>) getHibernateTemplate().findByNamedParam("SELECT post FROM History as post WHERE post.action IN ('change_project_description','change_project_manager','change_project_type','create_task','delete_task','change_task')" +
                " AND post.object IN (:list)","list",list); //Сюди не дивись, так зроблено просто шо б ерору не було. А так-то воно вибирає історію з певних проектів.
    }

    public List<History> getHistory(Task task){
        List<String> list=new ArrayList<String>();
            list.add(task.getTitle());

        return (List<History>) getHibernateTemplate().find("SELECT post FROM History as post WHERE post.action IN ('add_user','delete_user','change_task_description','change_task_status')" +
                " AND post.object=? or post.inTask=?)",task.getTitle(),task.getId().toString()); //Сюди не дивись, так зроблено просто шо б ерору не було. А так-то воно вибирає історію з певних проектів.
    }

    public List<Project> getAllProjects() {
        return getHibernateTemplate().loadAll(Project.class);
    }

    public Project getProjectById(int id) {
        return getHibernateTemplate().get(Project.class, id);
    }

    public List<Project> getProjectsForUser(User user) {
        List<Task_User> t_uList=getTasksByUser(user.getId());
        List<Project> open_projectsList=new ArrayList<Project>();
        open_projectsList.addAll((List<Project>) getHibernateTemplate().find("SELECT project FROM Project as project WHERE project.type=?", ProjectType.Public));
        for(Task_User t_u: t_uList)
           if(!open_projectsList.contains(t_u.getTask().getProject())) open_projectsList.add(t_u.getTask().getProject());

        return open_projectsList;
    }

    public boolean isProjectByTitle(String title){    //TODO
          List<Project>list=(List<Project>)getHibernateTemplate().find("select project FROM Project as project WHERE project.name=?",title);
        if(list.size()==0)return false;
        else return true;
    }

    public boolean isTaskByTitle(String title){    //TODO
        List<Project>list=(List<Project>)getHibernateTemplate().find("select task FROM Task as task WHERE task.title=?",title);
        if(list.size()==0)return false;
        else return true;
    }


    public User getUserById(int id){
        return  getHibernateTemplate().get(User.class, id);
    }

    public User getUserByLogin(String login){

        return (User) getHibernateTemplate().find("SELECT user FROM User as user WHERE user.login=?",login).get(0);
    }
    public List<User> getUsersByLogin(String login){

        return (List<User>) getHibernateTemplate().find("SELECT user FROM User as user WHERE user.login=?",login);
    }


    public List<User> getUsers (int projectId){
        if(projectId==0)
            return getHibernateTemplate().loadAll(User.class);
      else
      {
          List<Task> taskList=getTasksInProject(projectId);

          List<User> userList=new ArrayList<User>();
          List<User>taskUserList=new ArrayList<User>();
          List<String> loginList=new ArrayList<String>();
          for(Task task: taskList)
          {
              System.out.println( getHibernateTemplate().find("select user from Task_User as t_u  INNER JOIN t_u.user as user WHERE t_u.task=?",task).size()+"-к-сть користувачів у таску "+task.getTitle());
              taskUserList=((List<User>) getHibernateTemplate().find("select user from Task_User as t_u  INNER JOIN t_u.user as user WHERE t_u.task=?",task));
              for(User user:taskUserList)
              {
                  if(!loginList.contains(user.getLogin()))
                  {
                      loginList.add(user.getLogin());
                      userList.add(user);
                  }

              }
          }
          return userList;
      }
   }

    public  List<Task_User> getUserRoleInTask (int taskId) {
        List<Task_User> t_uList=new ArrayList<Task_User>();
        t_uList=(List<Task_User>) getHibernateTemplate().find("SELECT t_u FROM Task_User as t_u WHERE t_u.task.id=?",taskId);
        return t_uList;
    }

    public List<User> getUsersInTask (int taskId){
            List<User> userList=new ArrayList<User>();
            Task task=getTaskById(taskId);
                userList.addAll((List<User>) getHibernateTemplate().find("select user from Task_User as t_u  INNER JOIN t_u.user as user WHERE t_u.task=?",task ));

            return userList;

    }
    public List<Task> getTasksInProject(int projectId){
        return (List<Task>) getHibernateTemplate().find("select t FROM Task as t INNER JOIN t.project as project WHERE project.id=? ORDER BY t.dateOfCreating DESC",projectId);
    }

    public List<Task_User> getTasksByUser(int id){
        return (List<Task_User>) getHibernateTemplate().find("SELECT t_u FROM Task_User as t_u INNER JOIN t_u.user as user WHERE user.id=?",id);
    }

    public Task_User getAccessInTask(User user, Task task){
        List<Task_User>list=(List<Task_User>)getHibernateTemplate().find("SELECT t_u FROM Task_User as t_u WHERE t_u.user=? AND t_u.task=?",user,task);
        if(list.size()>0)
            return list.get(0);
        else return null;
    }

    public Task getTaskById(int id){
        return getHibernateTemplate().get(Task.class, id);
    }

    public List<Comment> getCommentsInTask(int id){
        return (List<Comment>) getHibernateTemplate().find("SELECT comm FROM Comment as comm INNER JOIN comm.task as task WHERE task.id="+id+" ORDER BY task.id");
    }

}