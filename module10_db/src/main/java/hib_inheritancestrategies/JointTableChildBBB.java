package hib_inheritancestrategies;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STRATEGY_JOINED_BBB")
public class JointTableChildBBB extends JointTable {

    @Getter
    @Setter
    private String valueBBB;

    @Getter
    @Setter
    private String sameTypeOverlap;

    @Getter
    @Setter
    private int differentTypeOverlap;

}
