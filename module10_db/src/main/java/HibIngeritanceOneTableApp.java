import hib_inheritance.TrainTicketOneWay;
import hib_inheritance.TrainTicketReturn;
import hib_inheritance.TrainTicketReturnWithExtras;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class HibIngeritanceOneTableApp {


    public static void main(String[] args) {

        TrainTicketOneWay trainTicketOneWay = new TrainTicketOneWay();
        trainTicketOneWay.setNumber("A-0001");
        trainTicketOneWay.setDepartureDate(LocalDate.of(2020, 1, 1));

        TrainTicketReturn trainTicketReturn = new TrainTicketReturn();
        trainTicketReturn.setNumber("B-0001");
        trainTicketReturn.setDepartureDate(LocalDate.of(2020, 1, 1));
        trainTicketReturn.setReturnDate(LocalDate.of(2030, 1, 1));

        TrainTicketReturnWithExtras trainTicketReturnExtras = new TrainTicketReturnWithExtras();
        trainTicketReturnExtras.setNumber("C-0001");
        trainTicketReturnExtras.setDepartureDate(LocalDate.of(2021, 1, 1));
        trainTicketReturnExtras.setReturnDate(LocalDate.of(2031, 1, 1));
        trainTicketReturnExtras.setExtras("COFFEE");


        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            em.getTransaction().begin();
            em.persist(trainTicketOneWay);
            em.persist(trainTicketReturn);
            em.persist(trainTicketReturnExtras);
            em.getTransaction().commit();
        }
    }
}
