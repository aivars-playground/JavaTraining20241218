import hib_inheritance.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class HibIngeritanceMultiTableApp {


    public static void main(String[] args) {

        BusPassenger busPassenger = new BusPassenger();
        busPassenger.setName("John Doe");

        BusTicketOneWay firstLeg = new BusTicketOneWay();
        firstLeg.setTravelDate(LocalDate.now());
        firstLeg.setNumber("A-0001");
        firstLeg.setBusPassenger(busPassenger);

        BusTicketReturn secondLeg = new BusTicketReturn();
        secondLeg.setReturnDate(LocalDate.now());
        secondLeg.setNumber("B-0001");
        secondLeg.setBusPassenger(busPassenger);



        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            em.getTransaction().begin();
            em.persist(busPassenger);
            em.persist(firstLeg);
            em.persist(secondLeg);
            em.getTransaction().commit();

            var found = em.find(BusTicketOneWay.class, 1);
            System.out.println("========================================== found:" + found);
        }
    }
}
