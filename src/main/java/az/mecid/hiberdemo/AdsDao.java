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

    public List<Project> getAllProjects() {
        return getHibernateTemplate().loadAll(Project.class);
    }

    public Project getProjectById(int id) {
        return getHibernateTemplate().get(Project.class, id);
    }

    public List<Project> getProjectsForUser(String login) {
        User user =  getUserByLogin(login);
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
              taskUserList=((List<User>) getHibernateTemplate().find("select user from Task_User as t_u  INNER JOIN t_u.user as user WHERE t_u.task=?",task ));
              for(User user:taskUserList)
              {          System.out.println(user.getLogin());
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

    public Task getTaskById(int id){
        return getHibernateTemplate().get(Task.class, id);
    }

    public List<Comment> getCommentsInTask(int id){
        return (List<Comment>) getHibernateTemplate().find("SELECT comm FROM Comment as comm INNER JOIN comm.task as task WHERE task.id="+id+" ORDER BY task.id");
    }



}