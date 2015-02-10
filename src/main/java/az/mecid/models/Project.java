package az.mecid.models;

import az.mecid.enums.ProjectType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="project")
public class Project implements DataEntity {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;


    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private ProjectType type;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private User manager;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;



    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User creator) {
        this.manager = creator;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public Project(){

    }

}
