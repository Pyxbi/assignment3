package org.example.backend.database;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseManager {
    private static  DatabaseManager instance;
    private static volatile EntityManagerFactory emf;

    public DatabaseManager() {
        emf = Persistence.createEntityManagerFactory("assignment3");
    }

    public static DatabaseManager getInstance(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    public  EntityManagerFactory getEmf() {
        return emf;
    }
}
