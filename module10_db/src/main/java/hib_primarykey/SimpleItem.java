package hib_primarykey;

import jakarta.persistence.*;

@Entity
@Table(name = "SIMPLEITEM")
public class SimpleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;



}
