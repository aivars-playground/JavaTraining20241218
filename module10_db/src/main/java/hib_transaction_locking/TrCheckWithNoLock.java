package hib_transaction_locking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class TrCheckWithNoLock {
    @Id
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;
}
