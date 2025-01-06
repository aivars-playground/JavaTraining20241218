import hib_transaction_locking.TrCheckWithNoLock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.concurrent.atomic.AtomicInteger;

public class HibTransactionsLocksNoLockApp {

    static AtomicInteger counter = new AtomicInteger(0);

    class NoLocks {
        public static Thread getInitThread() {
                return new Thread(() -> {
                    try (
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                            EntityManager em = emf.createEntityManager();
                    ) {
                        em.getTransaction().begin();
                        TrCheckWithNoLock trCheck = new TrCheckWithNoLock();
                        trCheck.setId(1);
                        trCheck.setName("test_"+counter.getAndIncrement());
                        em.persist(trCheck);
                        em.getTransaction().commit();
                        em.getTransaction().begin();
                        var trCheckDone = em.find(TrCheckWithNoLock.class, 1);
                        em.getTransaction().commit();
                        System.out.println("************ INIT " + trCheck.getName());

                    }
                });
        }

        public static Thread getUpdateThread(int delay) {
            return new Thread(() -> {
                try (
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                        EntityManager em = emf.createEntityManager();
                ) {
                    em.getTransaction().begin();
                    var trCheck = em.find(TrCheckWithNoLock.class, 1);
                    System.out.println("************ START @"+delay+" ->"+trCheck.getName());
                    trCheck.setName("test_"+counter.getAndIncrement());
                    System.out.println("************ SET @"+delay+" ->"+trCheck.getName());
                    Thread.sleep(delay);
                    System.out.println("************ END @"+delay+" ->"+trCheck.getName());
                    em.getTransaction().commit();
                    System.out.println("************ POST @"+delay+" ->"+trCheck.getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("******************************************************");
        System.out.println("Starting HibTransactionsLocksApp");
        var initial = NoLocks.getInitThread();
        initial.start();
        initial.join();

        var update1 = NoLocks.getUpdateThread(1000);
        var update2 = NoLocks.getUpdateThread(0);

        update1.start();
        Thread.sleep(200);
        update2.start();
        update1.join();

    }
}
