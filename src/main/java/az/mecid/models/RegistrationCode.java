package az.mecid.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="registration_code")
public class RegistrationCode implements DataEntity  {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="date")
    private Date date;

    @Column(name="code")
    private String code;

    @Column(name="is_registered")
    private boolean isRegistered;

    @OneToOne
    @JoinColumn(name="invited_by")
    private User invitedBy;

    @Column(name="is_manager")
    private boolean isManager;

    @OneToOne
    @JoinColumn(name="registered_user")
    private User user;

    public RegistrationCode() {
    }

    public RegistrationCode(String code, boolean isManager, User invitedBy) {
        this.code=code;
        this.isManager=isManager;
        this.date=new Date(new java.util.Date().getTime());
        this.invitedBy=invitedBy;
    }

    public User getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(User invitedBy) {
        this.invitedBy = invitedBy;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
