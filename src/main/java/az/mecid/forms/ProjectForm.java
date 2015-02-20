package az.mecid.forms;

import az.mecid.enums.ProjectType;
import az.mecid.models.Project;

public class ProjectForm {
    private String title;
    private String description;
    private String manager;
    private ProjectType type;


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
