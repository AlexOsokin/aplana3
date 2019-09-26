package fabric;

public class Сurrency extends companyType{
    public String getCode() {
        return code;
    }

    private String code;
    public Сurrency(int id, String code, String name_short, String name_full) {
        super(id, name_short, name_full);
        this.code = code;
    }
}
