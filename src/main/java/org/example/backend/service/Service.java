package org.example.backend.service;
import jakarta.persistence.EntityManagerFactory;
import org.example.backend.database.DatabaseManager;
import org.example.backend.model.Response;

public abstract class Service{
    protected static final EntityManagerFactory emf = DatabaseManager.getInstance().getEmf();


    public static <T> void handleException(Response<T> response, String message, int statusCode) {
        response.setResponseMsg(message);
        response.setStatusCode(statusCode);
        response.setData(null);
    }

    protected static <T> void handleSuccess(Response<T> response, String message, int statusCode, T data) {
        response.setResponseMsg(message);
        response.setStatusCode(statusCode);
        response.setData(data);
    }

}