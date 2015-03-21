package az.mecid.forms.validators;

import az.mecid.forms.ProjectForm;

public class SaveProjectValidator {

        public boolean hasErrors(Object target) {

            ProjectForm saveForm = (ProjectForm) target;
            String title = saveForm.getTitle();
            if ((title.length()) > 60) {
                System.out.println("требал в Тітлку");

                return true;
            }

            return false;
        }
}
