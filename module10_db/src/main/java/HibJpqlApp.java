import hib_repository.Airport;
import hib_repository.Passenger;
import hib_repository.Ticket;
import hib_transaction_locking.TrCheckWithLock;
import hib_transaction_locking.TrCheckWithNoLock;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.concurrent.atomic.AtomicInteger;

public class HibJpqlApp {

    public static void main(String[] args) throws InterruptedException {

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

        try(
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
            EntityManager em = emf.createEntityManager();
        ) {
            em.getTransaction().begin();
            em.persist(airport);
            em.persist(john);
            em.persist(jane);
            em.persist(toKansas);
            em.persist(toLa1);
            em.persist(toLa2);
            em.getTransaction().commit();

            em.getTransaction().begin();

            var pas = em.find(Passenger.class, 1);
            System.out.println("--@@@@@-:"+pas.getName());


            Query query = em.createQuery("select p from Passenger p");
            System.out.println("=========================Query");
            query.getResultList().forEach(p -> System.out.println("---Passenger:"+((Passenger)p).getName()));

            TypedQuery<Passenger> tq = em.createQuery("select p from Passenger p", Passenger.class);
            System.out.println("=========================TypedQuery");
            tq.getResultList().forEach(p -> System.out.println("---Passenger:"+p.getName()));

            TypedQuery<Passenger> tqAll = em.createNamedQuery("Passenger.findAll", Passenger.class);
            System.out.println("=========================NamedQuery");
            tqAll.getResultList().forEach(p -> System.out.println("---Passenger:"+p.getName()));

            TypedQuery<Passenger> tqName = em.createNamedQuery("Passenger.findByName", Passenger.class);
            tqName.setParameter("name", "John Doe");
            tqName.getResultList().forEach(p -> System.out.println("---Passenger:"+p.getName()));

            TypedQuery<Passenger> tqName2 = em.createQuery("select p from Passenger p where p.name = :name", Passenger.class)
                    .setParameter("name", "John Doe");
            tqName.getResultList().forEach(p -> System.out.println("---Passenger:"+p.getName()));



            TypedQuery<Long> countAggregate = em.createQuery("select count(p) from Passenger p", Long.class);
            System.out.println("====count:"+ countAggregate.getSingleResult());

            em.createQuery("insert into Passenger(id,name) values(:id,:name)")
                            .setParameter("id", 31)
                            .setParameter("name", "John Doe")
                            .executeUpdate();

            em.createQuery("insert into Airport(id,name) values(:airportId,:airportName)")
                            .setParameter("airportId", 31)
                            .setParameter("airportName", "ABA")
                            .executeUpdate();

            Airport ap = em.find(Airport.class, 31);
            System.out.println("---Airport:"+ap.getName()+ " id;"+ap.getId());

            em.createQuery("update Passenger p set p.name=:name, p.airport=:airport where p.id=:id")
                            .setParameter("name", "AAA")
                            .setParameter("airport", ap)
                            .setParameter("id", 1)
                            .executeUpdate();

            em.createQuery("insert into Airport(id,name) values(:airportId,:airportName)")
                    .setParameter("airportId", 55)
                    .setParameter("airportName", "LOL")
                    .executeUpdate();
            em.createQuery("delete from Airport a where a.id=:id")
                            .setParameter("id", 55)
                                    .executeUpdate();


            CriteriaBuilder cb = em.getCriteriaBuilder();

            System.out.println("---CriteriaBuilder----------------------");
            CriteriaQuery<Passenger> criteriaQuery = cb.createQuery(Passenger.class);
            Root<Passenger> root = criteriaQuery.from(Passenger.class);
            em.createQuery(criteriaQuery).getResultList().stream().forEach(p -> System.out.println("---Passenger:"+p.getName()));

            var nameToFind = "Jane Doe";
            criteriaQuery.select(root).where(cb.equal(root.get("name"), nameToFind));
            em.createQuery(criteriaQuery).getResultList().forEach(p -> System.out.println("-@@@@@@@@@@@@@@@@@@@@@@@@@@--Passenger:"+p.getName()));



            em.getTransaction().commit();

        }
    }
}
