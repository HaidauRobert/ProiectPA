package models;

public class Street {
    public Street(int id, String name, int idNodeStart, int idNodeEnd) {
        this.id = id;
        this.name = name;
        this.idNodeStart = idNodeStart;
        this.idNodeEnd = idNodeEnd;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIdNodeStart() {
        return idNodeStart;
    }

    public int getIdNodeEnd() {
        return idNodeEnd;
    }

    int id;
    String name;
    int idNodeStart;
    int idNodeEnd;
    int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
