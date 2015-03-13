package az.mecid.models;

import az.mecid.enums.TaskStatus;
import az.mecid.forms.TaskForm;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="task")
public class Task implements DataEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="date_of_creating")
    private Date dateOfCreating;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name="part_is_done")
    private String partIsDone;

    @ManyToOne
    @JoinColumn(name="creator")
    private User creator;

    @OneToMany(mappedBy = "task",fetch = FetchType.EAGER)
    private Set<Task_User> taskAndUserList;


    public Task(){

    }
    public Task(TaskForm taskForm, User creator) {
        this.id=taskForm.getId();
        this.title=taskForm.getTitle();
        this.description=taskForm.getDescription();
        this.status=taskForm.getStatus();
        this.creator=creator;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Task_User> getTaskAndUserList() {
        return taskAndUserList;
    }

    public void setTaskAndUserList(Set<Task_User> taskAndUserList) {
        this.taskAndUserList = taskAndUserList;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateOfCreating() {
        return dateOfCreating;
    }

    public void setDateOfCreating(Date dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getPartIsDone() {
        return partIsDone;
    }

    public void setPartIsDone(String partIsDone) {
        this.partIsDone = partIsDone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString(){
        return new String(id+"."+title);
    }
}