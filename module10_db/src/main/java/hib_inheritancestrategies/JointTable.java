package hib_inheritancestrategies;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STRATEGY_JOINED")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class JointTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String number;

}
