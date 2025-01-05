package hib_inheritance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "BUS_ONEWAY")
public class BusTicketOneWay extends BusTicketNotAnEntity {
    @Getter
    @Setter
    private LocalDate travelDate;
}
