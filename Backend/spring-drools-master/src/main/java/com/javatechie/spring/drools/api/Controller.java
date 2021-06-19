package com.javatechie.spring.drools.api;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    @PostMapping(value = "/getCC/{id}")
    public String getCCTehnika(@PathVariable("id") String id, @RequestBody String str)
    {
        try {
            Core cc = new Core(str);
            int idd = -1;
            for (int i = 0 ; i < cc.getTehnike().size() ; ++i){
                if(cc.getTehnikaId(i).getNaziv().equals(id))
                    idd = i;
            }

            return cc.getTehnikaId(idd).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @PostMapping(value="/Presao", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Presao(@RequestBody String tehnikaSTR) {

        Integer min = Integer.parseInt(tehnikaSTR.split("\\|\\|")[0]);
        tehnikaSTR = tehnikaSTR.split("\\|\\|")[1];

        Tehnika tehnika;
        try {
            tehnika = new Tehnika(tehnikaSTR);
        }catch (Exception e) {
            return "";
        }

        FactHandle th = session.insert(tehnika);
        FactHandle mint = session.insert(min);
        session.fireAllRules();

        session.delete(th);
        session.delete(mint);

        //////////////
        try {
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            FileInputStream fis = new FileInputStream("src/main/resources/offer.drl");
            kfs.write("src/main/resources/offer.drl",
                    kieServices.getResources().newInputStreamResource(fis));
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                //System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }
            KieContainer kieContainer =
                    kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            KieBase kieBase = kieContainer.getKieBase();
            KieSession kieSession = kieBase.newKieSession();


            FactHandle th2 = kieSession.insert(tehnika);
            FactHandle mint2 = kieSession.insert(min);
            kieSession.fireAllRules();

            kieSession.delete(th2);
            kieSession.delete(mint2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return tehnika.toString();
    }

    @PostMapping("/OdaberiPravac")
    public String OdaberiPravac(@RequestBody String coreSTR) {
        //System.out.println(coreSTR);

        String pravac = coreSTR.split("\\|\\|")[0];
        coreSTR = coreSTR.split("\\|\\|")[1];

        coreSTR = Normalisi(coreSTR);
        Core core = new Core(coreSTR);

        try {//nece puci, ali FileInputStream trazi da budde u try-u
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            FileInputStream fis = new FileInputStream("src/main/resources/pravac.drl");
            kfs.write("src/main/resources/pravac.drl",
                    kieServices.getResources().newInputStreamResource(fis));
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                //System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }
            KieContainer kieContainer =
                    kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            KieBase kieBase = kieContainer.getKieBase();
            KieSession kieSession = kieBase.newKieSession();


            FactHandle th = kieSession.insert(core);
            FactHandle pit = kieSession.insert(pravac);
            kieSession.fireAllRules();

            kieSession.delete(th);
            kieSession.delete(pit);
        }catch (Exception ignored){}

        //System.out.println("Vraca: " + core.toString());

        return core.toString();
    }

    @PostMapping("/OdaberiZvuk")
    public String OdaberiZvuk(@RequestBody String coreSTR) {
        String zvuk = coreSTR.split("\\|\\|")[0];
        coreSTR = coreSTR.split("\\|\\|")[1];

        coreSTR = Normalisi(coreSTR);
        Core core = new Core(coreSTR);

        try {//nece puci, ali FileInputStream trazi da budde u try-u
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            FileInputStream fis = new FileInputStream("src/main/resources/pravac.drl");
            kfs.write("src/main/resources/pravac.drl",
                    kieServices.getResources().newInputStreamResource(fis));
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                //System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }
            KieContainer kieContainer =
                    kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            KieBase kieBase = kieContainer.getKieBase();
            KieSession kieSession = kieBase.newKieSession();


            FactHandle th = kieSession.insert(core);
            FactHandle pit = kieSession.insert(zvuk);
            kieSession.fireAllRules();

            kieSession.delete(th);
            kieSession.delete(pit);
        }catch (Exception ignored){}

        /*FactHandle th = session.insert(core);
        FactHandle pit = session.insert(zvuk);
        session.fireAllRules();

        session.delete(th);
        session.delete(pit);*/

        return core.toString();
    }

    @PostMapping("/Raspored")
    public String NapraviRaspored(@RequestBody String coreIrasporedSTR) {
        Raspored raspored = new Raspored(coreIrasporedSTR.split("\\[_]")[0]);
        Core core = new Core(coreIrasporedSTR.split("\\[_]")[1]);

        raspored = new Raspored(raspored.getBrTehnika());

        FactHandle rpt = session.insert(raspored);
        FactHandle cot = session.insert(core);
        FactHandle sta = session.insert("Raspored");
        session.fireAllRules();

        session.delete(rpt);
        session.delete(cot);
        session.delete(sta);


        try {
            if(core.getStara() != -1) {
                KieServices kieServices = KieServices.Factory.get();
                KieFileSystem kfs = kieServices.newKieFileSystem();
                FileInputStream fis = new FileInputStream("src/main/resources/legacy.drl");
                kfs.write("src/main/resources/legacy.drl",
                        kieServices.getResources().newInputStreamResource(fis));
                KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
                Results results = kieBuilder.getResults();
                if (results.hasMessages(Message.Level.ERROR)) {
                    //System.out.println(results.getMessages());
                    throw new IllegalStateException("### errors ###");
                }
                KieContainer kieContainer =
                        kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
                KieBase kieBase = kieContainer.getKieBase();
                KieSession kieSession = kieBase.newKieSession();


                FactHandle th2 = kieSession.insert(core);
                FactHandle mint2 = kieSession.insert(raspored);
                FactHandle tt = kieSession.insert(core.getTehnikaId(core.getStara()));
                kieSession.fireAllRules();

                kieSession.delete(th2);
                kieSession.delete(mint2);
                kieSession.delete(tt);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        raspored.addZadatak(0, raspored.getZadatak(0).replace("Zadatak1", " "));
        raspored.addZadatak(1, raspored.getZadatak(1).replace("Zadatak2", " "));
        raspored.addZadatak(2, raspored.getZadatak(2).replace("Zadatak3", " "));

        return raspored.toString();
    }

    @PostMapping(value="/Normalisi", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Normalisi(@RequestBody String coreSTR){
        Core core = new Core(coreSTR);

        try {
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            FileInputStream fis = new FileInputStream("src/main/resources/raspored.drl");
            kfs.write("src/main/resources/raspored.drl",
                    kieServices.getResources().newInputStreamResource(fis));
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                throw new IllegalStateException("### errors ###");
            }
            KieContainer kieContainer =
                    kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            KieBase kieBase = kieContainer.getKieBase();
            KieSession kieSession = kieBase.newKieSession();


            FactHandle th2 = kieSession.insert(core);
            kieSession.fireAllRules();

            kieSession.delete(th2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return core.toString();
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

        try {
            if(core.getStara() != -1) {
                KieServices kieServices = KieServices.Factory.get();
                KieFileSystem kfs = kieServices.newKieFileSystem();
                FileInputStream fis = new FileInputStream("src/main/resources/legacy.drl");
                kfs.write("src/main/resources/legacy.drl",
                        kieServices.getResources().newInputStreamResource(fis));
                KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
                Results results = kieBuilder.getResults();
                if (results.hasMessages(Message.Level.ERROR)) {
                    //System.out.println(results.getMessages());
                    throw new IllegalStateException("### errors ###");
                }
                KieContainer kieContainer =
                        kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
                KieBase kieBase = kieContainer.getKieBase();
                KieSession kieSession = kieBase.newKieSession();


                FactHandle th2 = kieSession.insert(core);
                FactHandle mint2 = kieSession.insert(raspored);
                FactHandle tt = kieSession.insert(core.getTehnikaId(core.getStara()));
                kieSession.fireAllRules();

                kieSession.delete(th2);
                kieSession.delete(mint2);
                kieSession.delete(tt);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return core.toString();
    }
}
