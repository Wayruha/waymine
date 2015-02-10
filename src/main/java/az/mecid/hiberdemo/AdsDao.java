package az.mecid.hiberdemo;


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

    public User getUserById(int id){
        return getHibernateTemplate().get(User.class, id);
    }

   public List<User> getUsers (String projectName){
      if(projectName.equals("all"))
          return getHibernateTemplate().loadAll(User.class);
      else
      {
            List<Task> taskList=getTasksInProject(projectName);
          List<User> userList=new ArrayList<User>();
          for(Task task: taskList)
          {
              userList.addAll((List<User>) getHibernateTemplate().find("select user from Task_User as t_u  INNER JOIN t_u.user as user WHERE t_u.task=?",task ));
          }
          return userList;
      }
   }
    public List<Task> getTasksInProject(String projectName){
        return (List<Task>) getHibernateTemplate().find("select t FROM Task as t INNER JOIN t.project as project WHERE project.name=? ORDER BY t.dateOfCreating DESC",projectName);
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