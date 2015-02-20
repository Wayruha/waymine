package az.mecid.forms;

import az.mecid.enums.TaskStatus;
import az.mecid.models.Task;
import az.mecid.models.User;

public class TaskForm {
    private String title;
    private String description;
    private TaskStatus status;
    private String userList;
    private String accessList;
    private User creator;

    public TaskForm() {
    }
    public TaskForm(Task task) {
    }

    public String getAccessList() {
        return accessList;
    }

    public void setAccessList(String accessList) {
        this.accessList = accessList;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
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
