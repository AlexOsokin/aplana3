package fabric;
import java.time.LocalDate;
import java.util.ArrayList;

public class Company {

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName_full() {
        return name_full;
    }

    public String getName_short() {
        return name_short;
    }

    public long getInn() {
        return inn;
    }

    public companyType getCompany_type() {
        return company_type;
    }


    public Country getCountry() {
        return country;
    }

    public String getFio_head() {
        return fio_head;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getWww() {
        return www;
    }

    public boolean isIs_resident() {
        return is_resident;
    }
    public long getOgrn() {
        return ogrn;
    }

    public ArrayList<Securities> getSecurities() {
        return securities;
    }
    public String getEgrul_date() {
        return egrul_date;
    }
    private int id;
    private String code;
    private String name_full;
    private String name_short;
    private long inn;
    private companyType company_type;
    private long ogrn;
    private String egrul_date;
    private Country country;
    private String fio_head;
    private String address;
    private String phone;
    private String e_mail;
    private String www;
    private boolean is_resident;
    private ArrayList<Securities> securities = new ArrayList<>();

}
