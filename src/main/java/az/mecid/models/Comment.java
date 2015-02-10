package az.mecid.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="comment")
public class Comment  implements   DataEntity{

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="_user")
    private String user;

    @Column(name="date")
    private Date date;

    @Column(name="text")
    private String text;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment(Task task, String userLogin, String text) {
        this.task=task;
        this.user=userLogin;
        this.text=text;
        java.util.Date utilDate=new java.util.Date();
        this.date=new Date(utilDate.getTime());
    }

    public Comment() {
    }
}
