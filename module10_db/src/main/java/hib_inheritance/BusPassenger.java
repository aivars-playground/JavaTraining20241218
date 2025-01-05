package hib_inheritance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class BusPassenger {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    private String name;
}
