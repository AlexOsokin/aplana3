package fabric;

public class Country extends State{
    private String code;
    public Country(int id, String name, String code) {
        super(id, name);
        this.code = code;
    }

}
