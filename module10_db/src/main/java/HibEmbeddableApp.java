import hib_embed.ContainsEmbed;
import hib_embed.EmbedSimpleClass;
import hib_embed.WithDataTypes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class HibEmbeddableApp {


    public static void main(String[] args) {

        ContainsEmbed containsEmbedd = new ContainsEmbed();
        containsEmbedd.name = "this class contains embeddables";

        EmbedSimpleClass embedSimpleClass = new EmbedSimpleClass();
        embedSimpleClass.name = "embName";
        embedSimpleClass.value = "embValue";
        containsEmbedd.embedSimpleClass = embedSimpleClass;

        EmbedSimpleClass embedSimpleClass2 = new EmbedSimpleClass();
        embedSimpleClass2.name = "embName2";
        embedSimpleClass2.value = "embValue2";
        containsEmbedd.embedSimpleClassAgain = embedSimpleClass;


        List<EmbedSimpleClass> embeddedList1 = List.of(embedSimpleClass);
        containsEmbedd.embedList1.addAll(embeddedList1);

        List<EmbedSimpleClass> embeddedList2 = List.of(embedSimpleClass2);
        containsEmbedd.embedList2.addAll(embeddedList2);

        var attrs = containsEmbedd.embedded_attrs;
        attrs.put("key1", "value1");
        attrs.put("key2", "value2");

        var withDataTypes = new WithDataTypes();
        withDataTypes.name = "withDataTypes";
        withDataTypes.createdAt = Instant.now();
        withDataTypes.modifiedAt = LocalDateTime.now();
        withDataTypes.isSomething = true;
        withDataTypes.isSomethingElse = false;

        Instant createdAtStart;
        Instant createdAtSent;
        Instant createdAtPostCommit;
        Instant createdAtRead;

        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            em.getTransaction().begin();
            em.persist(containsEmbedd);

            createdAtStart = withDataTypes.createdAt;
            em.persist(withDataTypes);
            createdAtSent = withDataTypes.createdAt;

            em.getTransaction().commit();

            createdAtPostCommit = withDataTypes.createdAt;
            createdAtRead = em.find(WithDataTypes.class, withDataTypes.getId()).createdAt;


        }

        System.out.println("===============================================");
        System.out.println("===============================================");
        System.out.println("created at: " + createdAtStart
        + ", created at sent: " + createdAtSent
        + ", created at post: " + createdAtPostCommit
        + ", created at read: " + createdAtRead);
    }
}
