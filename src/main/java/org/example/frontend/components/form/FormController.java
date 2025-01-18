package org.example.frontend.components.form;
/**
 * @author <Butter>
 */
import javafx.event.ActionEvent;
import org.example.backend.model.Response;
import org.example.frontend.AlertManager;
import org.example.frontend.MissingRequiredFieldException;

import org.example.frontend.TaskRunner;
import org.example.frontend.components.form.fields.FormField;


import java.util.List;
import java.util.ArrayList;


public abstract class FormController<T> {
    // T is the type that is returned after the form is successfully submitted

    protected List<FormField> requiredFields;

    public FormController() {
        requiredFields = new ArrayList<>();
    }

    protected void addRequiredField(FormField field) {
        // Use this method in the initialize() method for your controller.
        requiredFields.add(field);
    }

    private List<FormField> getMissingFields() {
        List<FormField> missingFields = new ArrayList<>();

        for (FormField field: requiredFields) {
            if (field.isEmpty()) {
                missingFields.add(field);
            }
        }

        return missingFields;
    }

    protected void checkRequiredFields() throws MissingRequiredFieldException {
        // This method will throw a MissingRequiredFieldException when a required field is missing.
        // Make sure to catch this exception before form submission.

        List<FormField> missingFields = getMissingFields();
        if (missingFields.size() > 0) {
            throw new MissingRequiredFieldException(missingFields);
        };
    }

    public abstract Response<T> sendFormRequest();

    public boolean preSubmit() {
        return true;
    }

    // Override this to execute code after successful form submission (e.g. redirect pages)
    public void onSuccessfulSubmit() {};

    public void onSubmit(ActionEvent event) {
        if (!preSubmit()) return;

        try {
            checkRequiredFields();

            TaskRunner<Response<T>> runner = new TaskRunner<>(this::sendFormRequest, res -> {
                if (res == null) {
                    AlertManager.showError("Something went wrong when trying to submit, please try again.");
                    return;
                }
                if (!res.isOk()) {
                    AlertManager.showError(res.getResponseMsg());
                    return;
                };

                AlertManager.showInfo(res.getResponseMsg());
                onSuccessfulSubmit();
            });

            runner.run();
        } catch (MissingRequiredFieldException e) {
            AlertManager.showError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            AlertManager.showError(e.getMessage());
        }
    }
}