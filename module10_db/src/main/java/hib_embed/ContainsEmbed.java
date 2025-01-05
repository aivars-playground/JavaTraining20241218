package hib_embed;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
public class ContainsEmbed {

    @Id
    @GeneratedValue
    private Long id;

    public String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "em1_name")),
            @AttributeOverride(name = "value", column = @Column(name = "em1_value"))
    })
    public EmbedSimpleClass embedSimpleClass;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "em2_name")),
            @AttributeOverride(name = "value", column = @Column(name = "em2_value"))
    })
    public EmbedSimpleClass embedSimpleClassAgain;

    @ElementCollection
    public List<EmbedSimpleClass> embedList1 = new ArrayList<>();

    @ElementCollection
    public List<EmbedSimpleClass> embedList2 = new ArrayList<>();

    @ElementCollection
    @MapKeyColumn(name = "ATTR_KEY")
    @Column(name = "ATTR_VAL")
    public Map<String,String> embedded_attrs = new HashMap<>();

}
