package hib_inheritancestrategies;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("BBB")
public class OneTableChildBBB extends OneTable {
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
