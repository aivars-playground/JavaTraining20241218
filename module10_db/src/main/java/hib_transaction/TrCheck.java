package hib_transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class TrCheck {
    @Id
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;
}
