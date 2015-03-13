package az.mecid.forms.validators;

import az.mecid.forms.TaskForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SaveTaskValidator implements Validator {


    public boolean supports(Class<?> clazz) {
        return TaskForm.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

        TaskForm saveForm = (TaskForm) target;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Title must not be empty.");
        String title = saveForm.getTitle();
        String creator = saveForm.getCreator();
        System.out.println(creator+"креійтор--------");
        if ((title.length()) >100) {
            errors.rejectValue("title", "username.tooLong", "Username must not more than 16 characters.");
        }
        if(creator.isEmpty())
            errors.rejectValue("manager", "manager.empty", "Manager must not be empty" );


    }
}
