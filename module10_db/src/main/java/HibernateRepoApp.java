import hib_repository.Airport;
import hib_repository.Passenger;
import hib_repository.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class HibernateRepoApp {
    public static void main(String[] args) {

        switchToSLF4JBridge();

        Airport airport = new Airport(1,"ABX");

        Passenger john = new Passenger(1,"John Doe");
        Passenger jane = new Passenger(2,"Jane Doe");

        Ticket toKansas = new Ticket(1,"KA0001");
        Ticket toLa1 = new Ticket(21,"AAA-0000-0001");
        Ticket toLa2 = new Ticket(22,"AAA-0000-0002");

        airport.addPassenger(john);
        airport.addPassenger(jane);
        john.setAirport(airport);
        jane.setAirport(airport);

        john.addTicket(toKansas);
        john.addTicket(toLa1);
        toKansas.setPassenger(john);
        toLa1.setPassenger(john);

        jane.addTicket(toLa2);
        toLa2.setPassenger(jane);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.persist(airport);

        em.persist(john);
        em.persist(jane);

        em.persist(toKansas);
        em.persist(toLa1);
        em.persist(toLa2);

        em.getTransaction().commit();
        em.close();
    }

    static void switchToSLF4JBridge() {
        //redirecting all logging to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
