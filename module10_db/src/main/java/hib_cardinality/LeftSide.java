package hib_cardinality;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
public class LeftSide {

    @Id
    @GeneratedValue
    @Getter
    private int id;

    private String name;

    @ManyToMany
    @Getter
    @Setter
    public List<RightSide> rightSides;

    public LeftSide(String name) {
        this.name = name;
    }
}
