package az.mecid.models;

import az.mecid.enums.HistoryAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="history")
public class History implements DataEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="actor")
    private User actor;

    @Column(name="action")
    @Enumerated(EnumType.STRING)
    private HistoryAction action;

    @Column(name="object")
    private String object;

    @Column(name="inTask")
    private String inTask;

    public History(User actor, HistoryAction action, String object) {
        this.actor = actor;
        this.action = action;
        this.object = object;
        this.date=new java.sql.Date(new java.util.Date().getTime());
    }

    public History() {
        this.date=new java.sql.Date(new java.util.Date().getTime());
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInTask() {
        return inTask;
    }

    public void setInTask(String inTask) {
        this.inTask = inTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public HistoryAction getAction() {
        return action;
    }

    public void setAction(HistoryAction action) {
        this.action = action;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }



}
