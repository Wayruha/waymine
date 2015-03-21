package az.mecid.forms.validators;

import az.mecid.forms.TaskForm;

public class SaveTaskValidator{

    public boolean hasErrors(Object target) {

        TaskForm saveForm = (TaskForm) target;

      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Title must not be empty.");
        String title = saveForm.getTitle();
        String creator = saveForm.getCreator();
        System.out.println(creator+"креійтор--------");
        if ((title.length()) >100) {
            System.out.println("требал в Тітлку");

            return true;
        }
        if(creator.isEmpty()) {
            System.out.println("требал в ніку");
            return true;
        }
        return false;
    }

}
