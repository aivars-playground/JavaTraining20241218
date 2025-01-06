import com.mysql.cj.jdbc.exceptions.SQLError;
import hib_inheritancestrategies.*;
import hib_transaction.TrCheck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;

public class HibTransactionsApp {


    public static void main(String[] args) {


        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            {
                em.getTransaction().begin();
                TrCheck trCheck = new TrCheck();
                trCheck.setId(1);
                trCheck.setName("TrCheck");
                em.persist(trCheck);
                em.getTransaction().commit();
            }
            {
                em.getTransaction().begin();
                var trCheck = em.find(TrCheck.class, 1);
                trCheck.setName("TrChanged");
                em.getTransaction().commit();
            }
        }

        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            em.getTransaction().begin();
            for (long i = 0; i < 10; i++) {
                TrCheck trCheck = new TrCheck();
                trCheck.setId(i);
                trCheck.setName("TrCheck" + i);
                em.persist(trCheck);
            }
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.out.println("RollbackException => " + e.getMessage());
        }
    }
}
