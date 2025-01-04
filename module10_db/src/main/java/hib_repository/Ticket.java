package hib_repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TICKETS")
@NoArgsConstructor
public class Ticket {

    @Id
    @Column(name = "id")
    @Getter
    private int id;

    @Column(name = "number")
    @Getter
    @Setter
    private String number;

    @ManyToOne()
    @JoinColumn(name = "passenger_id")
    @Getter
    @Setter
    private Passenger passenger;

    public Ticket(int id, String number) {
        this.id = id;
        this.number = number;
    }
}
