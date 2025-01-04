import hib_repository.Airport;
import hib_repository.Passenger;
import hib_repository.Ticket;
import hib_secondary_repo.NicePerson;
import hib_secondary_repo.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class HibernateSecondaryApp {
    public static void main(String[] args) {

        switchToSLF4JBridge();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
        EntityManager em = emf.createEntityManager();

        Person person = new Person(1,"John", "address street 1");
        NicePerson nicePerson = new NicePerson(2, "Smith", "address street 2", 200);

        em.getTransaction().begin();

        em.persist(person);
        em.persist(nicePerson);

        em.getTransaction().commit();
    }

    static void switchToSLF4JBridge() {
        //redirecting all logging to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
