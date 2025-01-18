package org.example.frontend.components.customer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.backend.controller.CustomerController;
import org.example.backend.model.Customer;
import org.example.backend.model.Response;
import org.example.frontend.AlertManager;
import org.example.frontend.components.ComponentController;
import org.example.frontend.components.form.FormController;
import org.example.frontend.components.form.fields.FormTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAddController extends FormController<Customer> implements ComponentController, Initializable {
    @FXML
    public TextField fullNameField;
    @FXML

    public TextField phoneField;
    @FXML

    public TextField addressField;

    @FXML
    private void handleCreate() {
        Response<Customer> response = sendFormRequest();
        if (response.isOk()) {
            onSuccessfulSubmit();
            AlertManager.showInfo("Success creating customer");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addRequiredField(new FormTextField((fullNameField),"name"));
        addRequiredField(new FormTextField((phoneField),"phone"));
        addRequiredField(new FormTextField((addressField),"address"));
    }

    @Override
    public Response<Customer> sendFormRequest() {
        Customer customer = new Customer();
        customer.setName(fullNameField.getText());
        customer.setPhoneNumber(phoneField.getText());
        customer.setAddress(addressField.getText());
        CustomerController controller  = new CustomerController();

        return controller.addCustomer(customer);
    }
    @Override
    public void onSuccessfulSubmit() {
        AlertManager.showInfo("Create Successfully!");
    }
}
