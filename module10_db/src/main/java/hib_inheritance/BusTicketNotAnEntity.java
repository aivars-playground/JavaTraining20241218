package hib_inheritance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class BusTicketNotAnEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    private String number;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private BusPassenger busPassenger;

}
