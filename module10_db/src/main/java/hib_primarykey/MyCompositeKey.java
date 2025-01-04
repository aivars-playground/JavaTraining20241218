package hib_primarykey;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
public class MyCompositeKey implements Serializable {

    @Getter @Setter
    private String series;

    @Getter @Setter
    private String number;
}
