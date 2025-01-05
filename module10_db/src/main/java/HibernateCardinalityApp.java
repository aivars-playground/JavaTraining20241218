import hib_cardinality.LeftSide;
import hib_cardinality.RightSide;
import hib_secondary_repo.NicePerson;
import hib_secondary_repo.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;
import java.util.logging.LogManager;

public class HibernateCardinalityApp {
    public static void main(String[] args) {

        switchToSLF4JBridge();

        RightSide rightSide1 = new RightSide("rightSide1");
        LeftSide leftSide1 = new LeftSide("leftside1");

        LeftSide leftSide2 = new LeftSide("leftside2");
        RightSide rightSide2 = new RightSide("rightSide2");



        rightSide1.setLeftSides(List.of(leftSide1,leftSide2));
        rightSide2.setLeftSides(List.of(leftSide1,leftSide2));
        leftSide1.setRightSides(List.of(rightSide1,rightSide2));
        leftSide2.setRightSides(List.of(rightSide1,rightSide2));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.persist(rightSide1);
        em.persist(rightSide2);
        em.persist(leftSide1);
        em.persist(leftSide2);

        em.getTransaction().commit();

        var rs1 = em.find(RightSide.class, 1);
        rs1.getLeftSides().stream().flatMap(a -> a.getRightSides().stream()).map(RightSide::getName).forEach(System.out::println);


        em.close();
    }

    static void switchToSLF4JBridge() {
        //redirecting all logging to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
