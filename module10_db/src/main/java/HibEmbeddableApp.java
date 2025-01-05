import hib_embed.ContainsEmbed;
import hib_embed.EmbedSimpleClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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

        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit_airport");
                EntityManager em = emf.createEntityManager()
        ) {
            em.getTransaction().begin();
            em.persist(containsEmbedd);
            em.getTransaction().commit();
        }
    }
}
