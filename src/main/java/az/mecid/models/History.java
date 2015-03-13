package az.mecid.models;

import az.mecid.enums.HistoryAction;

import javax.persistence.*;

@Entity
@Table(name="history")
public class History implements DataEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="actor")
    private User actor;

    @Column(name="action")
    @Enumerated(EnumType.STRING)
    private HistoryAction action;

    @Column(name="object")
    private String object;

    public History(User actor, HistoryAction action, String object) {
        this.actor = actor;
        this.action = action;
        this.object = object;
    }

    public History() {
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
