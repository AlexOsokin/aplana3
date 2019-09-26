package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fabric.Company;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;


public class Main {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("test.json"));
        ArrayList<Company> compList = gson.fromJson(br, new TypeToken<ArrayList<Company>>() {
        }.getType());
        getNameAndDate(compList);
        oldAksionet(compList);
        askUser(compList);

    }
    private static void getNameAndDate(ArrayList<Company> compList){
        Function<String, LocalDate> comv = val -> LocalDate.parse(val);
        compList.stream().forEach(c -> System.out.println(c.getId() + " Краткое название: " + c.getName_short() + ". Дата основания: " + comv.apply(c.getEgrul_date()).format(DateTimeFormatter.ofPattern("dd/MM/yy"))));

    }
    private static void oldAksionet(ArrayList<Company> compList){
        System.out.println("Ценные бумаги, просроченные на сегодняшний день:");
        Function<String, LocalDate> comv = val -> LocalDate.parse(val);
        LocalDate date = LocalDate.now();
        long count = 0;
        for (int i = 0; i < compList.size(); i++){
            final int ii = i;
            compList.get(i).getSecurities().stream().filter(s->ChronoUnit.DAYS.between(date,comv.apply(s.getDate_to())) < 0).forEach(s->System.out.println("Code: " + s.getCode() + "; Дата истечения: " + comv.apply(s.getDate_to()).format(DateTimeFormatter.ofPattern("dd/MM/yy")) + "; полное название организации-владельца: " +  compList.get(ii).getName_full()));
            count += compList.get(i).getSecurities().stream().filter(s->ChronoUnit.DAYS.between(date,comv.apply(s.getDate_to()))<0).count();
        }
        System.out.println("Колличество просроченных бумаг: " + count);
    }
    private static void askUser(ArrayList<Company> compList){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите дату (в формате \"ДД.ММ.ГГГГ\", \"ДД.ММ.ГГ\", \"ДД/ММ/ГГГГ\" или \"ДД/ММ/ГГ\") или код валюты (например: EU, USD, RUB, CNY): ");
        String st = sc.nextLine();
        for(int i = 0; i < st.length(); i++){
            Character c  = st.charAt(i);
            if(c == '.'|| c == '/'){
                getForDate(st, compList);
                break;
            }
            else if(i == st.length() - 1){
                getForCode(st, compList);
            }
        }

    }

    private static void getForDate(String st, ArrayList<Company> compList) {
        System.out.println("Информация об организациях, основанных после введенной даты:");
        ArrayList<DateTimeFormatter> format = new ArrayList<>(Arrays.asList(DateTimeFormatter.ofPattern("dd.MM.yyyy"),  DateTimeFormatter.ofPattern("dd.MM.yy"),  DateTimeFormatter.ofPattern("dd/MM/yyyy"),  DateTimeFormatter.ofPattern("dd/MM/yy")));
        LocalDate date = null;
        for (int i = 0; i < format.size(); i++){
            try {
                date = LocalDate.parse(st, format.get(i));
            }
            catch (DateTimeParseException e){}
        }
        if (date == null){
            System.out.println("Неправельно введенная дата!");
            askUser(compList);
            return;
        }
        Function<String, LocalDate> comv = val -> LocalDate.parse(val);
        LocalDate finalDate = date;
        compList.stream().filter(c->ChronoUnit.DAYS.between(finalDate,comv.apply(c.getEgrul_date())) > 0).forEach(c -> System.out.println(c.getId() + " Краткое название: " + c.getName_short() + ". Дата основания: " + comv.apply(c.getEgrul_date()).format(DateTimeFormatter.ofPattern("dd/MM/yy"))));


    }

    private static void getForCode(String st, ArrayList<Company> compList) {
        System.out.println("Информация о ценных бумагах, использующих данную валюту:");
        st = st.toUpperCase();
        long count = 0;
        for (int i = 0; i < compList.size(); i++) {
            String finalSt = st;
            compList.get(i).getSecurities().stream().filter(c-> finalSt.equals(c.getCurrency().getCode())).forEach(c -> System.out.println("id: "+ c.getId() + "; code: " + c.getCode()));
            count += compList.get(i).getSecurities().stream().filter(c-> finalSt.equals(c.getCurrency().getCode())).count();
        }
        if (count == 0){
            System.out.println("Такой валюты нет, введите запрос еще раз");
            askUser(compList);
        }
    }

}
