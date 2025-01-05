package hib_inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@SecondaryTable(name = "TRAIN_TICKET_EXTRAS")
public class TrainTicketReturnWithExtras extends TrainTicketReturn {

    @Getter
    @Setter
    @Column(table = "TRAIN_TICKET_EXTRAS")
    private String extras;
}
