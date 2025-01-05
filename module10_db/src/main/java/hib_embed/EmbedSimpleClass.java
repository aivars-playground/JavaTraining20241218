package hib_embed;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmbedSimpleClass {

    public String value;
    public String name;
}
