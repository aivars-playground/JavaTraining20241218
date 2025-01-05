package hib_secondary_repo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SecondaryTables({
        @SecondaryTable(
                name = "NICENESS_DATA",
                pkJoinColumns = @PrimaryKeyJoinColumn(
                        name = "person_id",
                        referencedColumnName = "id")),
        @SecondaryTable(
                name = "NICENESS_NOTES",
                pkJoinColumns = @PrimaryKeyJoinColumn(
                        name = "person_id",
                        referencedColumnName = "id"))
})
@NoArgsConstructor
public class NicePerson extends Person {

    @Column(name = "niceness_level", table = "NICENESS_DATA")
    @Getter
    @Setter
    private int nicenessLevel;

    @Column(name = "notes", table = "NICENESS_NOTES")
    @Getter
    @Setter
    private String notes;

    public NicePerson(int id, String name, String address, int nicenessLevel, String notes) {
        super(id, name, address);
        this.nicenessLevel = nicenessLevel;
        this.notes = notes;
    }
}
