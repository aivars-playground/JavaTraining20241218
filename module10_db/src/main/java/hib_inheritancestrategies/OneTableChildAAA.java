package hib_inheritancestrategies;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("AAA")
public class OneTableChildAAA extends OneTable {

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
