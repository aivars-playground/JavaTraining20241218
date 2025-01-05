package hib_inheritance;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class TrainTicketOneWay extends TrainTicket {

    @Getter
    @Setter
    private LocalDate departureDate;

}
