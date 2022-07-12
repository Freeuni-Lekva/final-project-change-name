package bandfinder.models;

public class Band {
    private int id;
    private String name;

    public Band(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Band(String name){
        this(-1,name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        Band other = (Band) obj;
        return other.getId() == this.getId();
    }
}
