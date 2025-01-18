package org.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String movieName;
    private String showTiming;
    private String seatAvailability;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getShowTiming() {
        return showTiming;
    }

    public void setShowTiming(String showTiming) {
        this.showTiming = showTiming;
    }

    public String getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(String seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
}



package org.example.backend.service;

        import jakarta.persistence.EntityManager;
        import jakarta.persistence.EntityManagerFactory;
        import org.example.backend.database.DatabaseManager;
        import org.example.backend.model.Screen;

        import java.util.List;

public class ScreenService {
    private final EntityManagerFactory emf;

    public ScreenService() {
        this.emf = DatabaseManager.getInstance().getEmf();
    }

    public void addScreen(Screen screen) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(screen);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateScreen(Screen screen) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(screen);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteScreen(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Screen screen = em.find(Screen.class, id);
            if (screen != null) {
                em.remove(screen);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Screen> searchScreens(String movieName) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Screen s WHERE s.movieName LIKE :movieName ORDER BY s.showTiming ASC", Screen.class)
                    .setParameter("movieName", "%" + movieName + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}


package org.example.backend.controller;

        import org.example.backend.model.Screen;
        import org.example.backend.service.ScreenService;

        import java.util.List;

public class ScreenController {
    private final ScreenService screenService;

    public ScreenController() {
        this.screenService = new ScreenService();
    }

    public void addScreen(Screen screen) {
        screenService.addScreen(screen);
    }

    public void updateScreen(Screen screen) {
        screenService.updateScreen(screen);
    }

    public void deleteScreen(Integer id) {
        screenService.deleteScreen(id);
    }

    public List<Screen> searchScreens(String movieName) {
        return screenService.searchScreens(movieName);
    }
}



package org.example.backend.model;

        import jakarta.persistence.*;
        import java.util.List;

@Entity
@Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Screen> screens;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }
}


package org.example.backend.service;

        import jakarta.persistence.EntityManager;
        import jakarta.persistence.EntityManagerFactory;
        import org.example.backend.database.DatabaseManager;
        import org.example.backend.model.Theater;

        import java.util.List;

public class TheaterService {
    private final EntityManagerFactory emf;

    public TheaterService() {
        this.emf = DatabaseManager.getInstance().getEmf();
    }

    public void addTheater(Theater theater) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(theater);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateTheater(Theater theater) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(theater);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteTheater(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Theater theater = em.find(Theater.class, id);
            if (theater != null) {
                em.remove(theater);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Theater> searchTheaters(String nameOrAddress) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Theater t WHERE t.name LIKE :query OR t.address LIKE :query ORDER BY t.name ASC", Theater.class)
                    .setParameter("query", "%" + nameOrAddress + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}



package org.example.backend.controller;

        import org.example.backend.model.Theater;
        import org.example.backend.service.TheaterService;

        import java.util.List;

public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController() {
        this.theaterService = new TheaterService();
    }

    public void addTheater(Theater theater) {
        theaterService.addTheater(theater);
    }

    public void updateTheater(Theater theater) {
        theaterService.updateTheater(theater);
    }

    public void deleteTheater(Integer id) {
        theaterService.deleteTheater(id);
    }

    public List<Theater> searchTheaters(String nameOrAddress) {
        return theaterService.searchTheaters(nameOrAddress);
    }
}


