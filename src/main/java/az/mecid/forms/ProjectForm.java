package az.mecid.forms;

import az.mecid.enums.ProjectType;
import az.mecid.models.Project;

import javax.validation.constraints.Size;

public class ProjectForm {
    private int id;
    @Size(min=2, max=30, message = "Не має бути пустим")
    private String title;
    private String description;
    private String manager;
    private ProjectType type;
    private boolean editing;
    private String userList;

    public ProjectForm() {

    }

    public ProjectForm(Project project) {
        this.id=project.getId();
        this.title=project.getName();
        this.description=project.getDescription();
        this.manager=project.getManager()==null?"":project.getManager().getLogin();
        this.type=project.getType();
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }



}
