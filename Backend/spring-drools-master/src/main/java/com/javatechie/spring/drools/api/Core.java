package com.javatechie.spring.drools.api;

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
                //System.out.println(str);
                this.tehnike.add(new Tehnika(str));
            }catch (Exception e){
                //System.out.println(e.getMessage());
            }
        }
    }

    public List<Tehnika> getTehnike() {
        return tehnike;
    }

    public void setTehnike(List<Tehnika> tehnike) {
        this.tehnike = tehnike;
    }

    /*public Tehnika getTehnika(int br){
        List<Tehnika> lista = this.getTehnike();
        Tehnika temp;
        for(int i = 0; i<lista.size(); i++ ) {
            for (int j = i + 1; j < lista.size(); j++) {
                if(lista.get(i).getPrioritet()>lista.get(j).getPrioritet()){
                    Collections.swap(lista, i, j);
                }

            }
        }
        return lista.get(br);
    }*/

    public Tehnika getTehnika(int br) throws Exception {
        Tehnika tehnika = tehnike.get(0);
        List<Tehnika> lista = this.getTehnike();
        List<String> drugi = new ArrayList<>();
        int tren;
        for(int i = 0 ; i < br ; ++i) {
            tren = 0;
            for (Tehnika t : lista) {
                tehnika = new Tehnika("placeholder", new ArrayList<PodTehnika>(), -1, new Date());
                if (tehnika.getPrioritet() < t.getPrioritet() && !drugi.contains(t.getNaziv())) {
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
            throw new Exception("vraca placeholder");
        }

        /*for(int i = 0; i<lista.size(); i++ ) {
            for (int j = i + 1; j < lista.size(); j++) {
                if(lista.get(i).getPrioritet()>lista.get(j).getPrioritet()){
                    Collections.swap(lista, i, j);
                }

            }
        }
        return lista.get(br);*/
    }

    public double getSavladanost(int br){
        //System.out.println(getTehnika(br).getPodtehnika().getSavadao());
        try {
            return getTehnika(br).getPodtehnika().getSavadao();
        }catch (Exception e){
            return -1;
        }
    }

    public String getZadatak(int br){
        List<Tehnika> lista = this.getTehnike();
        Tehnika temp;
        for(int i = 0; i<lista.size(); i++ ) {
            for (int j = i + 1; j < lista.size(); j++) {
                if(lista.get(i).getPrioritet()>lista.get(j).getPrioritet()){
                    Collections.swap(lista, i, j);
                }

            }
        }

        Collections.reverse(lista);

        return lista.get(br).getPodtehnika().getNaziv();
    }

    public void setPravac(String pravacStr) {
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
                myWriter.write("25, 40, 40, 50, 60, 55, 70, 50, 40\n" +
                                   "60, 65, 20, 40, 50, 80, 60, 20, 50\n" +
                                   "95, 80, 40, 10, 80, 90, 90, 40, 90\n");
                myWriter.close();
                setPravac(pravacStr);
            } catch (Exception ee) {
                //System.out.println("Nemate dozvolu da napravite fajl");
            }
        }catch (Exception e){
            //System.out.println(e.getMessage());
        }
    }

    public void setZvuk(String zvukStr) {
        int zvuk = 0;
        double ucitano=0;

        switch (zvukStr) {
            case "Rock":
                zvuk = 1;
                break;
            case "Dzez":
                zvuk = 2;
                break;
            case "Blues":
                zvuk = 3;
                break;
            case "Narodna":
                zvuk = 4;
                break;
            case "Country":
                zvuk = 5;
                break;
            default:
                //System.out.println("Zvuk ne postoji");
                return;
        }
        try {
            File myObj = new File("Zvuci.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 1;

            while (myReader.hasNextLine()) {
                if(i == zvuk) {
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
                File myObj = new File("Zvuci.txt");
                myObj.createNewFile();
                FileWriter myWriter = new FileWriter(myObj);
                myWriter.write("20, 20, 40, 0, 0, 0, 0, 0, 40\n" +
                        "60, 65, 0, 0, 50, 80, 0, 20, 10\n" +
                        "35, 80, 0, 10, 0, 0, 0, 0, 10\n" +
                        "55, 20, 70, 0, 0, 60, 0, 85, 5\n" +
                        "50, 40, 40, 0, 0, 0, 20, 0, 30\n");
                myWriter.close();
                setPravac(zvukStr);
            } catch (Exception ee) {
                //System.out.println("Nemate dozvolu da napravite fajl");
            }
        }catch (Exception e){
            //System.out.println(e.getMessage());
        }
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
                return i;
            }
        }
        return -1;
    }
}
