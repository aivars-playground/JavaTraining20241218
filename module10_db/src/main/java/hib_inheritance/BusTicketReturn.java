package hib_inheritance;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "BUS_RETURN")
@AssociationOverride(name = "busPassenger", joinColumns = @JoinColumn(name = "differently_named_passenger_id"))
public class BusTicketReturn extends BusTicketNotAnEntity {
    @Getter
    @Setter
    private LocalDate returnDate;
}
