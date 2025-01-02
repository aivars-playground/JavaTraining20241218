import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class P3_Prototype {

    @Test
    void shallowCopy() throws CloneNotSupportedException {
        var home = new Home(new Animal("dolly"));
        var other = home.clone();
        System.out.println(home.toString() + " -> " + other.toString()); //same animal
    }

    @Test
    void deepCopy() throws CloneNotSupportedException {
        var home = new HomeDeep(new Animal("dolly"));
        var other = home.clone();
        System.out.println(home.toString() + " -> " + other.toString()); //cloned animal
    }


    @Test
    void prototypeRegistry() throws CloneNotSupportedException {
        var reg = new OldShopTemplateRegistry();
        var m1 = reg.createVhsMovie("aa","bb");
        var m2 = reg.createVhsMovie("aa","bb");
        var m3 = reg.createVhsMovie("eeee","cccc");
        System.out.println(m1.toString() + "\n" + m2.toString() + "\n" + m3.toString());
    }

    @Test
    void builderWithPrototypeForImmutableClass() {

        ComputerImmutable computer = ComputerImmutable
                .builder()
                .withName("Alex")
                .withModel("mark1")
                .build();

        System.out.println(computer + " " + computer.getName());


        ComputerImmutable copy = computer
                .copyBuilder()
                .withName("hack")
                .build();

        System.out.println(copy + " " + copy.getName());
    }


}


class Animal implements Cloneable {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public Animal clone() throws CloneNotSupportedException {
        Animal cloned = (Animal) super.clone();
        return cloned;
    }
}

class Home implements Cloneable {
    private final Animal animal;

    Home(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return System.identityHashCode(this) + " " + System.identityHashCode(this.animal);
    }

    @Override
    public Home clone() throws CloneNotSupportedException {
        Home cloned = (Home) super.clone();
        return cloned;
    }
}



class HomeDeep implements Cloneable {
    private Animal animal;

    HomeDeep(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return System.identityHashCode(this) + " " + System.identityHashCode(this.animal);
    }

    @Override
    public HomeDeep clone() throws CloneNotSupportedException {
        HomeDeep cloned = (HomeDeep) super.clone();
        cloned.animal = cloned.animal.clone();
        return cloned;
    }
}

class ComputerImmutable {

    private final String name;
    private final String model;

    public String getModel() {return model;}
    public String getName() {return name;}

    private ComputerImmutable(ComputerBuilder builder) {
        this.name = builder.name;
        this.model = builder.model;
    }

    public static ComputerBuilder builder() {return new ComputerBuilder();}
    public ComputerBuilder copyBuilder() {
        return new ComputerBuilder(this);
    }

    public static class ComputerBuilder {
        private String name;
        private String model;

        public ComputerBuilder() {}
        public ComputerBuilder(ComputerImmutable computerImmutable) {
            this.name = computerImmutable.name;
            this.model = computerImmutable.model;
        }

        public ComputerBuilder withName(String name) {this.name=name;return this;}
        public ComputerBuilder withModel(String model) {this.model=model;return this;}

        public ComputerImmutable build() {return new ComputerImmutable(this);}
    }
}

class OldShopTemplateRegistry {

    public Movie createVhsMovie(String title, String imdbId) throws CloneNotSupportedException {
        Movie m = (Movie) createItem("vhs");
        m.title = title;
        m.imdbId = imdbId;
        return m;
    }

    private Item createItem(String type) throws CloneNotSupportedException {
        return (Item) itemMap.get(type).clone();
    }

    private Map<String,Item> itemMap = new HashMap<>();

    {{
        itemMap.put("vhs", new Movie("-","-", 18.7f, 10.2f, 2.5f));
        itemMap.put("fictionBook", new Book("-","-", 4.25f, 6.87f, 0));
        itemMap.put("textBook", new Book("-","-", 6f, 9.7f, 0));
    }}


    abstract class Item implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    class Movie extends Item {
        float dimension_x, dimension_y, dimension_z;
        String title, imdbId;
        public Movie(String title, String imdbId, float dimension_x, float dimension_y, float dimension_z) {
            this.title = title;
            this.imdbId = imdbId;
            this.dimension_x = dimension_x;
            this.dimension_y = dimension_y;
        }

        @Override
        public String toString() {
            return "Movie{" +
                    "dimension_x=" + dimension_x +
                    ", dimension_y=" + dimension_y +
                    ", dimension_z=" + dimension_z +
                    ", title='" + title + '\'' +
                    ", imdbId='" + imdbId + '\'' +
                    '}' + System.identityHashCode(this);
        }
    }

    class Book extends Item {
        float dimension_x, dimension_y;
        int pages;
        String title, isbn;
        public Book(String title, String isbn, float dimension_x, float dimension_y, int pages) {
            this.title = title;
            this.isbn = isbn;
            this.dimension_x = dimension_x;
            this.dimension_y = dimension_y;
            this.pages = pages;
        }
    }
}
