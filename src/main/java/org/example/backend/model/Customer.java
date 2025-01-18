package org.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String contactInformation;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}




package org.example.backend.model;

        import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String showDetails;
    private String reservedSeats;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(String showDetails) {
        this.showDetails = showDetails;
    }

    public String getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(String reservedSeats) {
        this.reservedSeats = reservedSeats;
    }
}


package org.example.backend.service;

        import jakarta.persistence.EntityManager;
        import jakarta.persistence.EntityManagerFactory;
        import org.example.backend.database.DatabaseManager;
        import org.example.backend.model.User;

        import java.util.List;

public class UserService {
    private final EntityManagerFactory emf;

    public UserService() {
        this.emf = DatabaseManager.getInstance().getEmf();
    }

    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public User findUserById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public List<User> searchUsers(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}




package org.example.backend.service;

        import jakarta.persistence.EntityManager;
        import jakarta.persistence.EntityManagerFactory;
        import org.example.backend.database.DatabaseManager;
        import org.example.backend.model.Booking;

        import java.util.List;

public class BookingService {
    private final EntityManagerFactory emf;

    public BookingService() {
        this.emf = DatabaseManager.getInstance().getEmf();
    }

    public void addBooking(Booking booking) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateBooking(Booking booking) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(booking);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Booking findBookingById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Booking.class, id);
        } finally {
            em.close();
        }
    }

    public List<Booking> searchBookings(String showDetails) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Booking b WHERE b.showDetails LIKE :showDetails", Booking.class)
                    .setParameter("showDetails", "%" + showDetails + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}



package org.example.backend.controller;

        import org.example.backend.model.User;
        import org.example.backend.service.UserService;

        import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void addUser(User user) {
        userService.addUser(user);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public User getUserById(Integer id) {
        return userService.findUserById(id);
    }

    public List<User> searchUsers(String name) {
        return userService.searchUsers(name);
    }
}



package org.example.backend.controller;

        import org.example.backend.model.Booking;
        import org.example.backend.service.BookingService;

        import java.util.List;

public class BookingController {
    private final BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService();
    }

    public void addBooking(Booking booking) {
        bookingService.addBooking(booking);
    }

    public void updateBooking(Booking booking) {
        bookingService.updateBooking(booking);
    }

    public Booking getBookingById(Integer id) {
        return bookingService.findBookingById(id);
    }

    public List<Booking> searchBookings(String showDetails) {
        return bookingService.searchBookings(showDetails);
    }
}



    UserController userController = new UserController();
    User user = new User();
user.setName("John Doe");
        user.setContactInformation("123456789");
        userController.addUser(user);
