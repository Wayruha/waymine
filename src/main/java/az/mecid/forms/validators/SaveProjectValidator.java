package az.mecid.forms.validators;

import az.mecid.forms.ProjectForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//@Component
public class SaveProjectValidator implements Validator {


        public boolean supports(Class<?> clazz) {
            return ProjectForm.class.isAssignableFrom(clazz);
        }

        public void validate(Object target, Errors errors) {
            ProjectForm saveForm = (ProjectForm) target;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Title must not be empty.");
           /* String title = saveForm.getTitle();
            if ((title.length()) > 16) {
                errors.rejectValue("title", "username.tooLong", "Username must not more than 16 characters.");
            }     */

          /* ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty", "description must not be empty.");
            if (!(saveForm.getPassword()).equals(saveForm
                    .getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "confirmPassword.passwordDontMatch", "Passwords don't match.");
            }    */

        }
}
