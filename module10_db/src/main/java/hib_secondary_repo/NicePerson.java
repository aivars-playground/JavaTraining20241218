package hib_secondary_repo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PERSONS")
@SecondaryTable(
        name = "NICENESS_DATA",
        pkJoinColumns = @PrimaryKeyJoinColumn(
                name = "person_id",
                referencedColumnName = "id"))
@NoArgsConstructor
public class NicePerson extends Person {

    @Column(name = "niceness_level", table = "NICENESS_DATA")
    @Getter
    @Setter
    private int nicenessLevel;

    public NicePerson(int id, String name, String address, int nicenessLevel) {
        super(id, name, address);
        this.nicenessLevel = nicenessLevel;
    }
}
