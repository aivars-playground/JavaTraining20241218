package hib_inheritance;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class TrainTicketReturn extends TrainTicketOneWay {

    @Getter
    @Setter
    private LocalDate returnDate;

}
