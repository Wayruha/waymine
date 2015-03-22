package az.mecid.models;

import az.mecid.enums.Access;

import javax.persistence.*;

@Entity
@Table(name="task_user")
public class Task_User implements DataEntity {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="access")
    @Enumerated(EnumType.STRING)
    private Access access;

    public Task_User(){

    }

    public Task_User(Task task, User user, Access access){
        this.task=task;
        this.user=user;
        this.access=access;

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



}
