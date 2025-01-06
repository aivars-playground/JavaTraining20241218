package hib_repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PASSENGERS")
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Passenger.findAll", query = "select p from Passenger p order by p.name"),
        @NamedQuery(name = "Passenger.findByName", query = "select p from Passenger p where p.name = :name")
})
public class Passenger {

    @Id
    @Column(name = "id")
    @Getter
    private int id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "airport_id")
    @Getter
    @Setter
    private Airport airport;

    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return List.copyOf(tickets);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}
