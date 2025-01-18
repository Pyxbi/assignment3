package org.example.frontend.components.customer;

import org.example.frontend.components.ControlledComponent;

public class CustomerAdd extends ControlledComponent<CustomerAddController> {
    public CustomerAdd() {
        super(CustomerAdd.class.getResource("CreateCustomerForm.fxml"));
    }
}
