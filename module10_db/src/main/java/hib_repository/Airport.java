package hib_repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AIRPORTS")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Airport {

    @Id
    @Column(name = "id")
    @Getter
    private int id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    public Airport(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "airport")
    private List<Passenger> passengers = new ArrayList<>();

    public List<Passenger> getPassengers() {
        return List.copyOf(passengers);
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }
}
