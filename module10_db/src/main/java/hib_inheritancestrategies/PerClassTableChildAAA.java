package hib_inheritancestrategies;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STRATEGY_PERCLASS_AAA")
public class PerClassTableChildAAA extends JointTable {

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
