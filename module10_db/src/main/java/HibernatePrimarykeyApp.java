import hib_primarykey.ItemWithCompKey;
import hib_primarykey.MyCompositeKey;
import hib_primarykey.SimpleItem;
import hib_secondary_repo.NicePerson;
import hib_secondary_repo.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class HibernatePrimarykeyApp {
    public static void main(String[] args) {

        switchToSLF4JBridge();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        SimpleItem simpleItem = new SimpleItem();
        em.persist(simpleItem);

        SimpleItem simpleItem2 = new SimpleItem();
        em.persist(simpleItem2);

        MyCompositeKey myCompositeKey = new MyCompositeKey();
        myCompositeKey.setNumber("CK_NR_1");
        myCompositeKey.setSeries("SERIES_1");
        ItemWithCompKey itemWithCompKey = new ItemWithCompKey();
        itemWithCompKey.setId(myCompositeKey);
        itemWithCompKey.setValue("AAAAAAAAAAAAAAAAAAAAAAAAA");
        itemWithCompKey.setValueNotPersisted("THIS SHOULD NOT GO TO A DB");
        em.persist(itemWithCompKey);


        em.getTransaction().commit();
    }

    static void switchToSLF4JBridge() {
        //redirecting all logging to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
