package az.mecid.forms;

import az.mecid.enums.TaskStatus;
import az.mecid.models.Task;

public class TaskForm {
   private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private String userList;
    private String accessList;
    private String creator;
    private boolean editing=false;
    private Integer plannedTime=0;

    public TaskForm() {
    }
    public TaskForm(Task task) {
        this.title=task.getTitle();
        this.description=task.getDescription();
    //    this.status=task.getStatus();
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


    public String getAccessList() {
        return accessList;
    }

    public void setAccessList(String accessList) {
        this.accessList = accessList;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
