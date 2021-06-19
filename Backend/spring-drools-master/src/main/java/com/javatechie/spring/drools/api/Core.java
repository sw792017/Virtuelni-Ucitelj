package com.javatechie.spring.drools.api;

import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Core {
    List<Tehnika> tehnike;

    public Core(List<Tehnika> tehnike) {
        this.tehnike = tehnike;
    }

    public Core(String tehnike) {
        this.tehnike = new ArrayList<Tehnika>();

        for(String str : tehnike.split("@@@")){
            try {
                this.tehnike.add(new Tehnika(str));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Tehnika> getTehnike() {
        return tehnike;
    }

    public void setTehnike(List<Tehnika> tehnike) {
        this.tehnike = tehnike;
    }

    public Tehnika getTehnika(int br) throws Exception {
        ++br;
        Tehnika tehnika = tehnike.get(0);
        List<Tehnika> lista = this.getTehnike();
        List<String> drugi = new ArrayList<>();
        int tren;
        for(int i = 0 ; i < br ; ++i) {
            tren = 0;
            tehnika = new Tehnika("placeholder", new ArrayList<PodTehnika>(), -1, new Date());
            for (Tehnika t : lista) {
                if (tehnika.getPrioritet() < t.getPrioritet() && !drugi.contains(t.getNaziv()) && t.getSavladansot() < 100 && t.getPrioritet() > 0) {
                    tehnika = t;
                }
                ++tren;
            }
            drugi.add(tehnika.getNaziv());
        }
        //System.out.println(tehnika.toString());
        if(!tehnika.getNaziv().equals("placeholder"))
            return tehnika;
        else {
            System.out.println("get tehnika-------------------------------------------------");
            System.out.println(drugi.toString());
            System.out.println("Broj " + br + " u ---" + lista.toString());
            System.out.println("-----------------------------------------------------------");
            throw new Exception("vraca placeholder");
        }
    }

    public double getSavladanost(int br){
        //System.out.println(getTehnika(br).getPodtehnika().getSavadao());
        try {
            return getTehnika(br).getPodtehnika().getSavadao();
        }catch (Exception e){
            return -1;
        }
    }

    public double getSavladanostId(int br){
        //System.out.println(getTehnika(br).getPodtehnika().getSavadao());
        try {
            return getTehnikaId(br).getPodtehnika().getSavadao();
        }catch (Exception e){
            return -1;
        }
    }

    public String getZadatak(int br) throws Exception {
        try {
            return getTehnika(br).getPodtehnika().getNaziv();
        }catch (Exception e){
            return "";
        }
    }

    public void setPravacZvuk(String pravacStr) {
        String[] pravac = pravacStr.split(", ");
        for (int i = 0 ; i < tehnike.size() ; ++i) {
            if(tehnike.get(i).getSavladansot() < 100) {
                double snaga = Double.parseDouble(pravac[i]);
                this.tehnike.get(i).setPrioritet(snaga + (100 - snaga) * (this.tehnike.get(i).getPrioritet() / 100));
            }
        }
    }

    /*public void setPravac(String pravacStr) {
        int pravac = 0;
        double ucitano=0;

        switch (pravacStr) {
            case "Glavna":
                pravac = 1;
                break;
            case "Ritam":
                pravac = 2;
                break;
            case "Bas":
                pravac = 3;
                break;
            default:
                //System.out.println("Pravac ne prstoji");
                return;
        }
        try {
            File myObj = new File("Pravci.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 1;

            while (myReader.hasNextLine()) {
                if(i == pravac) {
                    String data = myReader.nextLine();
                    //System.out.println(data);

                    int iterator = 0;

                    for (String ss : data.split(", ")) {
                        ucitano = Double.parseDouble(ss);
                        this.tehnike.get(iterator).setPrioritet(ucitano + (100 - ucitano) * (this.tehnike.get(iterator).getPrioritet() / 100));
                        ++iterator;
                    }
                    break;

                }
                else {
                    ++i;
                    myReader.nextLine();
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            try {
                //System.out.println("Fajl je ostecen....\nPravljenje novog");
                File myObj = new File("Pravci.txt");
                myObj.createNewFile();
                FileWriter myWriter = new FileWriter(myObj);
                myWriter.write("0, 0, 50, 70, 65\n" +
                        "0, 0, 70, 50, 20\n" +
                        "0, 0, 90, 0, 0\n");
                myWriter.close();
                setPravac(pravacStr);
            } catch (Exception ee) {
                //System.out.println("Nemate dozvolu da napravite fajl");
            }
        }catch (Exception e){
            //System.out.println(e.getMessage());
        }
    }*/

    public void setZvuk(String zvukStr) {


    }

    @Override
    public String toString() {
        String str = "";
        for (Tehnika t : tehnike) {
            str += t.toString() + "@@@";
        }
        return str.substring(0, str.length() - 3);
    }

    public int getStara(){
        for (int i = 0 ; i < this.getTehnike().size() ; ++i){
            //System.out.println("UNIX " + ((new Date()).getTime() - this.getTehnike().get(i).getPresao().getTime()));
            if(((new Date()).getTime() - this.getTehnike().get(i).getPresao().getTime()) >= (2678400000L)){
                System.out.println("getStara()  Vraca " + i + " sa razlikom od " + ((new Date()).getTime() - this.getTehnike().get(i).getPresao().getTime()));
                return i;
            }
        }
        return -1;
    }

    public Tehnika getTehnikaId(int id){
        return this.getTehnike().get(id);
    }

    public String getZadatakId(int br) {
        return this.getTehnike().get(br).getPodtehnika().getNaziv();
    }

    public void zavrsi(Tehnika tt) throws Exception {
        for(int i = 0 ; i < this.tehnike.size() ; ++i){
            System.out.println(tehnike.get(i).getNaziv() + " =?= " + tt.getNaziv());
            if (this.tehnike.get(i).getNaziv().equals(tt.getNaziv())){
                System.out.println("#######$" + tehnike.get(i).getNaziv() + " =?= " + tt.getNaziv());
                tt.setPrioritet(0);
                this.tehnike.set(i, new Tehnika(tt.toString()));
                System.out.println(this.toString());
                break;
            }
        }
    }
}
