package az.mecid.models;

import az.mecid.enums.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="_user")
public class User implements DataEntity {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="e_mail")
    private String eMail;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="enabled")
    private boolean enabled=true;

    @OneToMany(mappedBy="manager",fetch = FetchType.EAGER)
    private List<Project> projectList;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Task_User> taskAndUserList;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Set<Task_User> getTaskAndUserList() {
        return taskAndUserList;
    }

    public void setTaskAndUserList(Set<Task_User> taskAndUserList) {
        this.taskAndUserList = taskAndUserList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

      public User(){

      }

}