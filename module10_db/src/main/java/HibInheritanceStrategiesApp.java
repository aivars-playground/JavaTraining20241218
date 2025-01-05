import hib_inheritance.TrainTicket;
import hib_inheritance.TrainTicketOneWay;
import hib_inheritance.TrainTicketReturn;
import hib_inheritance.TrainTicketReturnWithExtras;
import hib_inheritancestrategies.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class HibInheritanceStrategiesApp {


    public static void main(String[] args) {

        OneTableChildAAA oneTableChildAAA = new OneTableChildAAA();
        oneTableChildAAA.setNumber("something common A");
        oneTableChildAAA.setValueAAA("specific to AAA");
        oneTableChildAAA.setSameTypeOverlap("overlaps A");
        oneTableChildAAA.setDifferentTypeOverlap("type A");

        OneTableChildBBB oneTableChildBBB = new OneTableChildBBB();
        oneTableChildBBB.setNumber("something common B");
        oneTableChildBBB.setValueBBB("specific to BBB");
        oneTableChildBBB.setSameTypeOverlap("overlaps B");
        oneTableChildBBB.setDifferentTypeOverlap(123);

        JointTableChildAAA jointTableChildAAA = new JointTableChildAAA();
        jointTableChildAAA.setNumber("something common A");
        jointTableChildAAA.setValueAAA("specific to AAA");
        jointTableChildAAA.setSameTypeOverlap("overlaps A");
        jointTableChildAAA.setDifferentTypeOverlap("type A");

        JointTableChildBBB jointTableChildBBB = new JointTableChildBBB();
        jointTableChildBBB.setNumber("something common B");
        jointTableChildBBB.setValueBBB("specific to BBB");
        jointTableChildBBB.setSameTypeOverlap("overlaps B");
        jointTableChildBBB.setDifferentTypeOverlap(123);


        PerClassTableChildAAA perclassTableChildAAA = new PerClassTableChildAAA();
        perclassTableChildAAA.setNumber("something common A");
        perclassTableChildAAA.setValueAAA("specific to AAA");
        perclassTableChildAAA.setSameTypeOverlap("overlaps A");
        perclassTableChildAAA.setDifferentTypeOverlap("type A");

        PerClassTableChildBBB perclassTableChildBBB = new PerClassTableChildBBB();
        perclassTableChildBBB.setNumber("something common B");
        perclassTableChildBBB.setValueBBB("specific to BBB");
        perclassTableChildBBB.setSameTypeOverlap("overlaps B");
        perclassTableChildBBB.setDifferentTypeOverlap(123);

        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            em.getTransaction().begin();

            em.persist(jointTableChildAAA);
            em.persist(jointTableChildBBB);

            em.persist(perclassTableChildAAA);
            em.persist(perclassTableChildBBB);

            em.persist(oneTableChildAAA);
            em.persist(oneTableChildBBB);

            em.getTransaction().commit();


            System.out.println("aaa->" + (em.find(OneTableChildAAA.class, oneTableChildAAA.getId()).getDifferentTypeOverlap() + 123));
            System.out.println("bbb->" + (em.find(OneTableChildBBB.class, oneTableChildBBB.getId()).getDifferentTypeOverlap() + 123));

        }
    }
}
