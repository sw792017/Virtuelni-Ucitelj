package com.javatechie.spring.drools.api;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private KieSession session;

    @PostMapping(value="/Presao", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Presao(@RequestBody String tehnikaSTR) {

        Integer min = Integer.parseInt(tehnikaSTR.split("\\|\\|")[0]);
        tehnikaSTR = tehnikaSTR.split("\\|\\|")[1];

        System.out.println("TEHNIKA STR--------------\n");
        System.out.println(tehnikaSTR);

        Tehnika tehnika = new Tehnika(tehnikaSTR);

        System.out.println("TEHNIKA STR--------------\n");
        System.out.println(tehnika.toString());

        System.out.println("MIN --------------\n");
        System.out.println(min.toString());


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

        System.out.println("TEHNIKE STR--------------\n");
        System.out.println(coreSTR);

        Core core = new Core(coreSTR);

        System.out.println("TEHNIKA STR--------------\n");
        System.out.println(core.toString());

        System.out.println("PRAVAC---------------\n");
        System.out.println(pravac);


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

        System.out.println("TEHNIKE STR--------------\n");
        System.out.println(coreSTR);

        Core core = new Core(coreSTR);

        System.out.println("TEHNIKA STR--------------\n");
        System.out.println(core.toString());

        System.out.println("ZVUK---------------\n");
        System.out.println(zvuk);

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

        System.out.println("CORE STR--------------\n");
        System.out.println(core.toString());

        System.out.println("RASPORED STR--------------\n");
        System.out.println(raspored.toString());

        FactHandle rpt = session.insert(raspored);
        FactHandle cot = session.insert(core);
        session.fireAllRules();

        session.delete(rpt);
        session.delete(cot);

        return raspored.toString();
    }
}
