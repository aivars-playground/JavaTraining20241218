package hib_secondary_repo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PERSONS")
@SecondaryTable(
        name = "ADDRESSES",
        pkJoinColumns = @PrimaryKeyJoinColumn(
                name = "person_id",
                referencedColumnName = "id"))
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @Getter
    private int id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "person_address", table = "ADDRESSES")
    @Getter
    @Setter
    private String address;

    public Person(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
