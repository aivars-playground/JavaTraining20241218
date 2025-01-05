package hib_cardinality;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
public class RightSide {

    @Id
    @GeneratedValue
    @Getter
    private int id;

    @Getter
    private String name;

    @ManyToMany(mappedBy = "rightSides")
    @Getter @Setter
    public List<LeftSide> leftSides;

    public RightSide(String name) {
        this.name = name;
    }
}
