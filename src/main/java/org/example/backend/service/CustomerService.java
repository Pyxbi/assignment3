//package org.example.backend.service;
//
//import org.example.backend.model.Customer;
//import org.example.backend.model.Response;
//
//import java.beans.Customizer;
//
//public class CustomerService extends Service{
//    public Response<Customer> add(Customer entity) {
//        Response<Customer> response = new Response<>();
//
//        try{
//            CustomerRepo.add(entity);
//            handleSuccess(response,"Add Success", 200,entity);
//        } catch (CustomException e) {
//            handleException(response, e.getMessage(),404);
//        }
//        return response;    }
//}
