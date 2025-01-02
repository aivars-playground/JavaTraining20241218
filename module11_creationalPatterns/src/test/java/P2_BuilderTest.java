import org.junit.jupiter.api.Test;

public class P2_BuilderTest {

    @Test
    void builderExample() {

        StringBuffer sb = new StringBuffer();
        sb.append("<item>1</item>\n");

        StringBuilder builder = new StringBuilder();

        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        builder.append("<document>\n");
        builder.append(sb);
        builder.append("</document>\n");

        //this will not appear in builder!!!
        sb.append("<item>2</item>\n");

        String xml = builder.toString();
        System.out.println(xml);

    }

    @Test
    void builderExample2() {

        var builder = new Computer.ComputerBuilder();

        Computer computer = builder
                .withName("Alex")
                .withModel("mark1")
                .build();

        System.out.println(computer + " " + computer.getName());
    }
}

class Computer {

    private String name;
    private String model;

    public String getModel() {return model;}
    public String getName() {return name;}

    public Computer(ComputerBuilder builder) {
        this.name = builder.name;
        this.model = builder.model;
    }

    public static class ComputerBuilder {
        private String name;
        private String model;

        public ComputerBuilder withName(String name) {this.name=name;return this;}
        public ComputerBuilder withModel(String model) {this.model=model;return this;}

        public Computer build() {return new Computer(this);}
    }
}
