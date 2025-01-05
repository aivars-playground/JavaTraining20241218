package hib_embed;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class WithDataTypes {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    public String name;

    public Instant createdAt;
    public LocalDateTime modifiedAt;

    public Boolean isSomething;

    @Convert(converter = Boolean2YesNo.class)
    public Boolean isSomethingElse;
}

class Boolean2YesNo implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return ("Y".equals(dbData)) ? true : false;
    }
}
