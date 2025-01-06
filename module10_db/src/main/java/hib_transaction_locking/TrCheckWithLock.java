package hib_transaction_locking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
public class TrCheckWithLock {
    @Id
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    @Version
    private long version;
}
