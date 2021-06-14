package com.javatechie.spring.drools.api;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private KieSession session;

    @PostMapping(value = "/get/{id}")
    public String getTehnika(@PathVariable("id") int id, @RequestBody String str)
    {

        try {
            Core cc = new Core(str);
            return cc.getTehnika(id).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @PostMapping(value="/Presao", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Presao(@RequestBody String tehnikaSTR) {

        Integer min = Integer.parseInt(tehnikaSTR.split("\\|\\|")[0]);
        tehnikaSTR = tehnikaSTR.split("\\|\\|")[1];

        //System.out.println("TEHNIKA STR--------------\n");
        //System.out.println(tehnikaSTR);
        Tehnika tehnika;
        try {
            tehnika = new Tehnika(tehnikaSTR);
        }catch (Exception e){
            System.out.println("Nije uspeo da pronadje tehniku za " + tehnikaSTR);
            return "";
        }

        //System.out.println("TEHNIKA STR--------------\n");
        //System.out.println(tehnika.toString());

        //System.out.println("MIN --------------\n");
        //System.out.println(min.toString());


        FactHandle th = session.insert(tehnika);
        FactHandle mint = session.insert(min);
        session.fireAllRules();

        session.delete(th);
        session.delete(mint);

        return tehnika.toString();
    }

    @PostMapping("/OdaberiPravac")
    public String OdaberiPravac(@RequestBody String coreSTR) {
        String pravac = coreSTR.split("\\|\\|")[0];
        coreSTR = coreSTR.split("\\|\\|")[1];

        //System.out.println("TEHNIKE STR--------------\n");
        //System.out.println(coreSTR);

        Core core = new Core(coreSTR);

        //System.out.println("TEHNIKA STR--------------\n");
        //System.out.println(core.toString());

        //System.out.println("PRAVAC---------------\n");
        //System.out.println(pravac);


        List<String> pravci = new ArrayList<>();
        pravci.add("Glavna");
        pravci.add("Ritam");
        pravci.add("Bas");

        FactHandle th = session.insert(core);
        FactHandle pit = session.insert(pravac);
        FactHandle pt = session.insert(new Lista(pravci));
        session.fireAllRules();

        session.delete(th);
        session.delete(pit);
        session.delete(pt);

        return core.toString();
    }

    @PostMapping("/OdaberiZvuk")
    public String OdaberiZvuk(@RequestBody String coreSTR) {
        String zvuk = coreSTR.split("\\|\\|")[0];
        coreSTR = coreSTR.split("\\|\\|")[1];

        //System.out.println("TEHNIKE STR--------------\n");
        //System.out.println(coreSTR);

        Core core = new Core(coreSTR);

        //System.out.println("TEHNIKA STR--------------\n");
        //System.out.println(core.toString());

        //System.out.println("ZVUK---------------\n");
        //System.out.println(zvuk);

        List<String> zvukovi = new ArrayList<>();
        zvukovi.add("Rock");
        zvukovi.add("Dzez");
        zvukovi.add("Blues");
        zvukovi.add("Narodna");
        zvukovi.add("Country");

        FactHandle th = session.insert(core);
        FactHandle pit = session.insert(zvuk);
        FactHandle zvt = session.insert(new Lista(zvukovi));
        session.fireAllRules();

        session.delete(th);
        session.delete(pit);
        session.delete(zvt);

        return core.toString();
    }

    @PostMapping("/Raspored")
    public String NapraviRaspored(@RequestBody String coreIrasporedSTR) {
        Raspored raspored = new Raspored(coreIrasporedSTR.split("\\[_]")[0]);
        Core core = new Core(coreIrasporedSTR.split("\\[_]")[1]);

        if (raspored.getBrTehnika() > 3){
            raspored.setBrTehnika(3);
        }

        if (raspored.getBrTehnika() < 2){
            raspored.setBrTehnika(2);
        }

        for (int i = 0 ; i < raspored.getBrTehnika() ; ++i)
            raspored.addZadatak(i, "Zadatak" + (i+1));

        //System.out.println("CORE STR--------------\n");
        //System.out.println(core.toString());

        //System.out.println("RASPORED STR--------------\n");
        //System.out.println(raspored.toString());

        FactHandle rpt = session.insert(raspored);
        FactHandle cot = session.insert(core);
        FactHandle sta = session.insert("Raspored");
        session.fireAllRules();

        session.delete(rpt);
        session.delete(cot);
        session.delete(sta);

        return raspored.toString();
    }

    @PostMapping(value="/CustomTehnika", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String CustomTehnika(@RequestBody String tehnikaSTR) {
        String tehnika = tehnikaSTR.split("\\[_]")[0];
        Raspored raspored = new Raspored(tehnikaSTR.split("\\[_]")[1]);
        Core core = new Core(tehnikaSTR.split("\\[_]")[2]);


        for (int i = 0 ; i < raspored.getBrTehnika()+1 ; ++i)
            try {
                if (raspored.getZadatak(i).equals("") || raspored.getZadatak(i).equals(" "))
                    if(!(i == 2 && raspored.getBrTehnika() == 2))
                        raspored.addZadatak(i, "Zadatak" + (i + 1));
            }catch (java.lang.IndexOutOfBoundsException e){
                List<String> rr = raspored.getZadaci();
                rr.add("Zadatak" + (i + 1));
                raspored.setZadaci(rr);
            }

        //System.out.println("ODABRANA TEHNIKA STR--------------\n");
        //System.out.println(tehnikaSTR);

        //System.out.println("CORE STR--------------\n");
        //System.out.println(core.toString());

        //System.out.println("RASPORED STR--------------\n");
        //System.out.println(raspored.toString());

        String tip = "Custom";
        int id = -1;

        try{
            id = Integer.parseInt(tehnika);
        }catch (Exception e) {
            return null;
        }

        FactHandle mint = session.insert(id);
        FactHandle f = session.insert(tip);
        FactHandle rpt = session.insert(raspored);
        FactHandle cot = session.insert(core);

        session.fireAllRules();

        session.delete(mint);
        session.delete(f);
        session.delete(rpt);
        session.delete(cot);

        return raspored.toString();
    }
}
