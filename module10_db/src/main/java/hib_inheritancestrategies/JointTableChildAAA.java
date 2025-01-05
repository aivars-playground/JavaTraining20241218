package hib_inheritancestrategies;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STRATEGY_JOINED_AAA")
public class JointTableChildAAA extends JointTable {

    @Getter
    @Setter
    private String valueAAA;

    @Getter
    @Setter
    private String sameTypeOverlap;

    @Getter
    @Setter
    private String differentTypeOverlap;

}
