package hib_primarykey;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ITEM_WITH_COMPOSITE")
public class ItemWithCompKey {

    @EmbeddedId
    @Getter @Setter
    private MyCompositeKey id;

    @Getter @Setter
    private String value;

    @Getter @Setter
    private transient String valueNotPersisted;

    @Getter @Setter
    private static int staticValueNotPersisted = 1;

}
