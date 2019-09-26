package fabric;

public class companyType {
    public int getId() {
        return id;
    }

    private int id;
    private String name_short;
    private String name_full;

    public companyType(int id, String name_short, String name_full) {
        this.id = id;
        this.name_short = name_short;
        this.name_full = name_full;
    }
}
