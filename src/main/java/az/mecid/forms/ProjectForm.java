package az.mecid.forms;

import az.mecid.enums.ProjectType;
import az.mecid.models.Project;

import javax.validation.constraints.Size;

public class ProjectForm {
    @Size(min=2, max=30, message = "Не має бути пустим")
    private String title;
    private String description;
    private String manager;
    private ProjectType type;
    private String editing;

    public String getEditing() {
        return editing;
    }

    public void setEditing(String editing) {
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


    public ProjectForm() {

    }

    public ProjectForm(Project project) {
        this.title=project.getName();
        this.description=project.getDescription();
       // this.manager=project.getManager();
        this.type=project.getType();
    }
}
