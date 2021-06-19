package com.javatechie.spring.drools.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DroolsTest extends UnitTest{

    @Autowired
    private Controller controller;

    private static final String tehnike  = "Test Tehnika1|60|03/06/2021|pt1_1@50@100%pt2_1@50@100%Menjanje akorda E5, A5, C5@50@26%pt4_1@50@0%pt5_1@50@0@@@Test Tehnika2|55|03/06/2021|pt1_2@50@100%pt2_2@50@100%pt3_2@50@100%pt4_2@50@100%Sweep picking@50@80@@@Test Tehnika3|15|03/06/2021|pt1_3@50@100%pt2_3@50@100%pt3_3@50@100%Hammeron-pulloff-over@50@0%pt5_3@50@0@@@Test Tehnika4|65|03/06/2021|pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%Menjanje akorda Am, A, Dm, D@50@0%pt5_4@50@0@@@Test Tehnika5|90|03/06/2021|pt1_5@50@100%pt2_5@50@100%Tapkanje na 2 zice@50@10%pt4_5@50@0%pt5_5@50@0@@@Savladana Test Tehnika|0|01/06/2021|pt1_5@50@100%pt2_5@50@100";
    private static final String tehnikeNula  = "Akordi|0|Sat Jun 19 06:57:51 CEST 2021|Formiranje A akorda@50@100%Formiranje Am akorda@50@100%Menjanje akorda A, AmFormiranje D akorda@50@100%Formiranje Dm akorda@50@100%Menjanje akorda D, Dm@100@100%Formiranje C akorda@50@100%Formiranje Cm akorda@50@100%Menjanje akorda A, D, C@100@100%Formiranje G akorda@50@100%Formiranje F akorda@50@100%Formiranje D akorda@50@100%Menjanje akorda G, D, Em, C@100@100@@@Strumming|0|Sat Jun 19 06:57:51 CEST 2021|Strumming D U D U D U D U@50@100%Strumming D D U D U D U@50@100%Strumming D D U D D U@50@100%Strumming D D U D D@50@100%Strumming D D D U D U@50@100%Strumming D D U U D U@50@100@@@Prigusivanje|0|Sat Jun 19 06:57:51 CEST 2021|Standardno prigusivanje@50@0%Progresivno prigusivanje levom rukom@50@0%Progresivno prigusivanje desnom rukom zaica iznad dlana@50@0%Progresivno prigusivanje desnom rukom zaica ispod prstiju@50@0%”Duh” note@50@0%Prigusene note@50@0@@@Sweep picking|0|Sat Jun 19 06:57:51 CEST 2021|Na 2 zice@50@0%Na 3 zice@50@0%Na 4 zice@50@0%Na 2 zice sa preskakanjem@50@0%Na 3 zice sa preskakanjem…@50@0@@@Harminike|0|Sat Jun 19 06:57:51 CEST 2021|Prirodne harmonike@50@0%Vestacke harmovnike@50@0%”Pinch”  harmonike@50@0%”Tapped” harmonike@50@0";
    @Before
    public void setUp(){

    }

    @Test
    public void PresaoTest(){
        String ulaz = "15||Test Tehnika|60|03/06/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0";
        String[] ocekivano = {"61.0", "75.0"};

        //Test Tehnika|60.0|03/06/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@50.0%pt4@50.0@0.0%pt5@50.0@0.0
        //Test Tehnika|61.0|03/06/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@75.0%pt4@50.0@0.0%pt5@50.0@0.0

        String izlaz = controller.Presao(ulaz);
        System.out.println(izlaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("\\|")[1]);//Proverimo da li se prioritet povecao
        Assert.assertEquals(ocekivano[1], izlaz.split("%")[2].split("@")[2]);//Proverimo da li se savladanost podtehnike povecala
    }

    @Test
    public void PresaoTest_TehnikeSaNula(){
        String ulaz = "5||Test Tehnika|65|03/06/2021|pt1@50@100%pt2@50@100%pt3@50@100%pt4@50@0%pt5@50@0";
        String[] ocekivano = {"66.0", "25.0"};

        //Test Tehnika|65.0|03/06/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@100.0%pt4@50.0@0.0%pt5@50.0@0.0
        //Test Tehnika|66.0|03/06/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@100.0%pt4@50.0@25.0%pt5@50.0@0.0

        String izlaz = controller.Presao(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("\\|")[1]);//Proverimo da li se prioritet povecao
        Assert.assertEquals(ocekivano[1], izlaz.split("%")[3].split("@")[2]);//Proverimo da li se savladanost podtehnike povecala
    }

    @Test
    public void PresaoTest_PodtehnikaSavladaoViseOd100(){
        String ulaz = "120||Test Tehnika|65|03/06/2021|pt1@50@100%pt2@50@100%pt3@50@100%pt4@50@75%pt5@50@0";
        String ocekivano = "100.0";

        String izlaz = controller.Presao(ulaz);

        Assert.assertEquals(ocekivano, izlaz.split("%")[3].split("@")[2]);//Proverimo da li se savladanost podtehnike povecala
    }

    @Test
    public void PresaoTest_TehnikePreko100(){
        String ulaz = "120||Test Tehnika|99|03/06/2021|pt1@50@100%pt2@50@100%pt3@50@100%pt4@50@0%pt5@50@0";
        String ocekivano = "100.0";

        String izlaz = controller.Presao(ulaz);

        Assert.assertEquals(ocekivano, izlaz.split("\\|")[1]);//Proverimo da li se prioritet povecao
    }

    @Test
    public void PresaoTest_TehnikeIspod0(){
        String ulaz = "0||Test Tehnika|1|03/06/2021|pt1@50@100%pt2@50@100%pt3@50@100%pt4@50@0%pt5@50@0";
        String ocekivano = "0.0";

        String izlaz = controller.Presao(ulaz);

        Assert.assertEquals(ocekivano, izlaz.split("\\|")[1]);//Proverimo da li se prioritet povecao
    }

    @Test
    public void ZvukTest_Rock(){
        String ulaz = "Rock||" + tehnikeNula;
        String[] ocekivano = {"0.0", "0.0", "60.0", "80.0", "50.0"};

        String izlaz = controller.OdaberiZvuk(ulaz);
        for(int i = 0 ; i < 5 ; ++i){
            System.out.println(izlaz.split("@@@")[i].split("\\|")[1]);
        }
        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void ZvukTest_Narodna(){
        String ulaz = "Narodna||" + tehnikeNula;
        String[] ocekivano = {"0.0", "0.0", "10.0", "0.0", "20.0"};

        String izlaz = controller.OdaberiZvuk(ulaz);for(int i = 0 ; i < 5 ; ++i){
            System.out.println(izlaz.split("@@@")[i].split("\\|")[1]);
        }

        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void PravacTest_Glavna(){
        String ulaz = "Glavna||" + tehnikeNula;
        String[] ocekivano = {"0.0", "0.0", "50.0", "70.0", "65.0"};

        String izlaz = controller.OdaberiPravac(ulaz);

        for(int i = 0 ; i < 5 ; ++i){
            System.out.println(izlaz.split("@@@")[i].split("\\|")[1]);
        }

        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void PravacTest_Bas(){
        String ulaz = "Bas||" + tehnikeNula;
        String[] ocekivano = {"0.0", "0.0", "90.0", "0.0", "0.0"};

        String izlaz = controller.OdaberiPravac(ulaz);
        for(int i = 0 ; i < 5 ; ++i){
            System.out.println(izlaz.split("@@@")[i].split("\\|")[1]);
        }
        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void RasporedTest(){
        String ulaz = "3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike;
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 1h| | ";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedTest_DvaTestaSaIstimPrioritetom(){
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike).replace("Test Tehnika3|15|", "Test Tehnika3|65|");
        String ocekivano1 = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Hammeron-pulloff-over 30min|Vezbaj Hammeron-pulloff-over 30min| | ";
        String ocekivano2 = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Hammeron-pulloff-over 30min|Vezbaj Menjanje akorda Am, A, Dm, D 30min| | ";

        String izlaz = controller.NapraviRaspored(ulaz);
        System.out.println("Expected: " + ocekivano1);
        System.out.println("Expected: " + ocekivano2);
        System.out.println("Actual  : " + izlaz);
        Assert.assertTrue(ocekivano1.equals(izlaz) || ocekivano2.equals(izlaz));
    }

    @Test
    public void RasporedTest_DvaTestaSaIstimPrioritetomNaIvici(){
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike).replace("Test Tehnika2|55|", "Test Tehnika2|65|");
        String ocekivano1 = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Sweep picking 2h|Vezbaj Menjanje akorda Am, A, Dm, D 30min| | ";
        String ocekivano2 = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Sweep picking 2h| | ";

        System.out.println(tehnike.replace("Test Tehnika2|55|", "Test Tehnika2|65|"));

        String izlaz = controller.NapraviRaspored(ulaz);

        System.out.println("Expected: " + ocekivano1);
        System.out.println("Expected: " + ocekivano2);
        System.out.println("Actual  : " + izlaz);
        Assert.assertTrue(ocekivano1.equals(izlaz) || ocekivano2.equals(izlaz));
    }

    @Test
    public void RasporedTest_CustomZadatak(){// da li id custom tehnike pocinje od 0 ili od 1 ????????   ovfe je vratio da vraca sa 1
        String ulaz = "2" + "[_]" + "3@Vezbaj Tapkanje na 2 zice 1h|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 30min" + "[_]" + tehnike;
        String ocekivano = "Test Tehnika1|60.0|Thu Jun 03 00:00:00 CEST 2021|pt1_1@50.0@100.0%pt2_1@50.0@100.0%Menjanje akorda E5, A5, C5@50.0@26.0%pt4_1@50.0@0.0%pt5_1@50.0@0.0@@@Test Tehnika2|55.0|Thu Jun 03 00:00:00 CEST 2021|pt1_2@50.0@100.0%pt2_2@50.0@100.0%pt3_2@50.0@100.0%pt4_2@50.0@100.0%Sweep picking@50.0@80.0@@@Test Tehnika3|38.5875|Thu Jun 03 00:00:00 CEST 2021|pt1_3@50.0@100.0%pt2_3@50.0@100.0%pt3_3@50.0@100.0%Hammeron-pulloff-over@50.0@0.0%pt5_3@50.0@0.0@@@Test Tehnika4|65.0|Thu Jun 03 00:00:00 CEST 2021|pt1_4@50.0@100.0%pt2_4@50.0@100.0%pt3_4@50.0@100.0%Menjanje akorda Am, A, Dm, D@50.0@0.0%pt5_4@50.0@0.0@@@Test Tehnika5|90.0|Thu Jun 03 00:00:00 CEST 2021|pt1_5@50.0@100.0%pt2_5@50.0@100.0%Tapkanje na 2 zice@50.0@10.0%pt4_5@50.0@0.0%pt5_5@50.0@0.0@@@Savladana Test Tehnika|0.0|Tue Jun 01 00:00:00 CEST 2021|pt1_5@50.0@100.0%pt2_5@50.0@100.0";

        String izlaz = controller.CustomTehnika(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedTest_CustomZadatakIRasporedZaZadatke1i3(){//
        String ulaz = "2" + "[_]" + "3@|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Zadatak3" + "[_]" + tehnike;
        String ocekivano = "Test Tehnika1|60.0|Thu Jun 03 00:00:00 CEST 2021|pt1_1@50.0@100.0%pt2_1@50.0@100.0%Menjanje akorda E5, A5, C5@50.0@26.0%pt4_1@50.0@0.0%pt5_1@50.0@0.0@@@Test Tehnika2|55.0|Thu Jun 03 00:00:00 CEST 2021|pt1_2@50.0@100.0%pt2_2@50.0@100.0%pt3_2@50.0@100.0%pt4_2@50.0@100.0%Sweep picking@50.0@80.0@@@Test Tehnika3|38.5875|Thu Jun 03 00:00:00 CEST 2021|pt1_3@50.0@100.0%pt2_3@50.0@100.0%pt3_3@50.0@100.0%Hammeron-pulloff-over@50.0@0.0%pt5_3@50.0@0.0@@@Test Tehnika4|65.0|Thu Jun 03 00:00:00 CEST 2021|pt1_4@50.0@100.0%pt2_4@50.0@100.0%pt3_4@50.0@100.0%Menjanje akorda Am, A, Dm, D@50.0@0.0%pt5_4@50.0@0.0@@@Test Tehnika5|90.0|Thu Jun 03 00:00:00 CEST 2021|pt1_5@50.0@100.0%pt2_5@50.0@100.0%Tapkanje na 2 zice@50.0@10.0%pt4_5@50.0@0.0%pt5_5@50.0@0.0@@@Savladana Test Tehnika|0.0|Tue Jun 01 00:00:00 CEST 2021|pt1_5@50.0@100.0%pt2_5@50.0@100.0";

        String izlaz = controller.CustomTehnika(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void LegacyZadatak() {
        String ulaz = ("3@Zadatak1|Zadatak2| | |Ne ovo" + "[_]" + tehnike).replace("|01/06/2021|", "|01/04/2021|");
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 1h| |Ponovi Savladana Test Tehnika";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void LegacyZadatak2(){
        String ulaz = ("3@Zadatak1|Zadatak2|Za| | |" + "[_]" + tehnike).replace("|01/06/2021|", "|01/05/2021|");
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 1h| |Ponovi Savladana Test Tehnika";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void Ne_LegacyZadatak() {
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike).replace("|01/06/2021|pt1_5@50@100%", "|01/05/2021|pt1_5@50@10%");
        System.out.println(ulaz);
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 1h| | ";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedTest_LegacyZadatakICustomZadatakSaZadacima1i3() {
        String ulaz = ("4" + "[_]" + "3@|Vezbaj Jazz Akorde 1h|Vezbaj Menjanje akorda Am, A, Dm, D 30min||Vezbaj Formiranje Barre akorda 30min" + "[_]" + tehnike).replace("|01/06/2021|", "|01/05/2021|");
        String ocekivano = "Test Tehnika1|60.0|Thu Jun 03 00:00:00 CEST 2021|pt1_1@50.0@100.0%pt2_1@50.0@100.0%Menjanje akorda E5, A5, C5@50.0@26.0%pt4_1@50.0@0.0%pt5_1@50.0@0.0@@@Test Tehnika2|55.0|Thu Jun 03 00:00:00 CEST 2021|pt1_2@50.0@100.0%pt2_2@50.0@100.0%pt3_2@50.0@100.0%pt4_2@50.0@100.0%Sweep picking@50.0@80.0@@@Test Tehnika3|15.0|Thu Jun 03 00:00:00 CEST 2021|pt1_3@50.0@100.0%pt2_3@50.0@100.0%pt3_3@50.0@100.0%Hammeron-pulloff-over@50.0@0.0%pt5_3@50.0@0.0@@@Test Tehnika4|65.0|Thu Jun 03 00:00:00 CEST 2021|pt1_4@50.0@100.0%pt2_4@50.0@100.0%pt3_4@50.0@100.0%Menjanje akorda Am, A, Dm, D@50.0@0.0%pt5_4@50.0@0.0@@@Test Tehnika5|91.5|Thu Jun 03 00:00:00 CEST 2021|pt1_5@50.0@100.0%pt2_5@50.0@100.0%Tapkanje na 2 zice@50.0@10.0%pt4_5@50.0@0.0%pt5_5@50.0@0.0@@@Savladana Test Tehnika|0.0|Sat May 01 00:00:00 CEST 2021|pt1_5@50.0@100.0%pt2_5@50.0@100.0";

        String izlaz = controller.CustomTehnika(ulaz);
        System.out.println(izlaz);


        Core c = new Core(tehnike.replace("0|01/06/2021|", "99.9|01/04/2021|"));

        System.out.println(c.getStara());
        System.out.println(c.getTehnikaId(c.getStara()));
        System.out.println(c.getTehnikaId(c.getStara()).getSavladansot());

        Assert.assertEquals(ocekivano, izlaz);
    }


    @Test
    public void GetZadatak0() throws Exception {

        String ocekivano = "Tapkanje na 2 zice";
        Core c = new Core(tehnike);

        String izlaz = c.getZadatak(0);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatak1() throws Exception {

        String ocekivano = "Menjanje akorda Am, A, Dm, D";
        Core c = new Core(tehnike);

        String izlaz = c.getZadatak(1);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatak2() throws Exception {

        String ocekivano = "Menjanje akorda E5, A5, C5";
        Core c = new Core(tehnike);

        String izlaz = c.getZadatak(2);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatak3() throws Exception {

        String ocekivano = "Sweep picking";
        Core c = new Core(tehnike);

        String izlaz = c.getZadatak(3);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatak4() throws Exception {

        String ocekivano = "Hammeron-pulloff-over";
        Core c = new Core(tehnike);

        String izlaz = c.getZadatak(4);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatak5() throws Exception {

        String ocekivano = "";
        Core c = new Core(tehnike);

        String izlaz = c.getZadatak(5);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatak_prazna() throws Exception {
        String ocekivano = "";
        String teh = tehnike.replace("Tehnika1|60", "Tehnika1|0");
        teh = teh.replace("Tehnika2|55", "Tehnika2|0");
        teh = teh.replace("Tehnika3|15", "Tehnika3|0");
        teh = teh.replace("Tehnika4|65", "Tehnika4|0");
        teh = teh.replace("Tehnika5|90", "Tehnika5|0");
        Core c = new Core(teh);

        String izlaz = c.getZadatak(0);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatakSavladana() throws Exception {

        String ocekivano = "";
        Core c = new Core(tehnike.replace("Tehnika|0|", "Tehnika|10|"));


        for(int i = 0 ; i < 10 ; ++i)
            System.out.println(c.getZadatak(i));

        String izlaz = c.getZadatak(5);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void GetZadatakNeSavladana() throws Exception {

        String ocekivano = "pt2_5";
        Core c = new Core(tehnike.replace("Test Tehnika|0|01/06/2021|pt1_5@50@100%pt2_5@50@100","Test Tehnika|10|01/06/2021|pt1_5@50@100%pt2_5@50@50"));


        for(int i = 0 ; i < 10 ; ++i)
            System.out.println("Zadatak "+i+")  " + c.getZadatak(i));

        String izlaz = c.getZadatak(5);
        System.out.println(izlaz);
        Assert.assertEquals(ocekivano, izlaz);
    }


    @Test
    public void GetSavladanost() throws Exception {
        Core c = new Core(tehnike.replace("Test Tehnika|0|01/06/2021|pt1_5@50@100%pt2_5@50@100","Test Tehnika|10|01/06/2021|pt1_5@50@100%pt2_5@50@50"));

        for(int i = 0 ; i < 6 ; ++i){
            System.out.println("Zadatak "+i +")  " + c.getZadatak(i) + " sa " + c.getTehnika(i).getSavladansot() + "%" + "  c.getTehnika(i).getSavladansot()");
            System.out.println("Zadatak "+i +")  " + c.getZadatak(i) + " sa " + c.getSavladanost(i) + "%" + "  c.getSavladanost(i)");
        }

        Assert.assertEquals(1, 1);
    }


    @Test
    public void RasporedPreskociUradjenu(){
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike).replace("pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%Menjanje akorda Am, A, Dm, D@50@0%pt5_4@50@0", "pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%");
        System.out.println(ulaz);
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 30min|Vezbaj Menjanje akorda E5, A5, C5 1h|Vezbaj Sweep picking 2h| | ";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedPoslednjiZadatak(){
        String tehnike2 = tehnike.replace("|60|", "|0|").replace("|15|", "|0|").replace("|90|", "|0|");
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike2).replace("pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%Menjanje akorda Am, A, Dm, D@50@0%pt5_4@50@0", "pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%");
        System.out.println(ulaz);
        String ocekivano = "3@Vezbaj Sweep picking 2h| | | | ";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedPoslednjiZadatak2Testa(){
        String tehnike2 = tehnike.replace("|60|", "|0|").replace("|15|", "|0|").replace("|90|", "|0|");
        String ulaz = ("2@Zadatak1|Zadatak2|" + "[_]" + tehnike2).replace("pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%Menjanje akorda Am, A, Dm, D@50@0%pt5_4@50@0", "pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%");
        System.out.println(ulaz);
        String ocekivano = "2@Vezbaj Sweep picking 2h| | | | ";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedBezZadataka(){
        String tehnike2 = tehnike.replace("|60|", "|0|").replace("|15|", "|0|").replace("|90|", "|0|").replace("|55|", "|0|");
        String ulaz = ("3@Zadatak1|Zadatak2|" + "[_]" + tehnike2).replace("pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%Menjanje akorda Am, A, Dm, D@50@0%pt5_4@50@0", "pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%");
        System.out.println(ulaz);
        String ocekivano = "3@ | | | | ";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedZavrsenPrioritetViseOdNulaTest(){
        String ulaz = tehnike.replace("|0|", "|66|").replace("Test Tehnika5|90|03/06/2021|pt1_5@50@100%pt2_5@50@100%Tapkanje na 2 zice@50@10%pt4_5@50@0%pt5_5@50@0","Test Tehnika5|90|03/06/2021|pt1_5@50@100%pt2_5@50@100%Tapkanje na 2 zice@50@100%pt4_5@50@100%pt5_5@50@100");
        String ocekivano1 = "Test Tehnika5|0.0|";
        String ocekivano2 = "Savladana Test Tehnika|0.0|";

        String izlaz = controller.Normalisi(ulaz);
        System.out.println(izlaz);

        Assert.assertTrue(izlaz.contains(ocekivano1) && izlaz.contains(ocekivano2));
    }

    @Test
    public void OdaberiPravacNoprmalizacija(){
        String ulaz = "Bas||" + tehnikeNula.replace("Akordi|0|", "Akordi|90|");
        String izlaz = controller.OdaberiPravac(ulaz);

        Assert.assertEquals("90.0", izlaz.split("@@@")[2].split("\\|")[1]);
    }

    @Test
    public void OdaberiPravacOdbijen(){
        String ulaz = "Bas||" + tehnikeNula.replace("Formiranje A akorda@50@100%", "Formiranje A akorda@50@10%").replace("Akordi|0|", "Akordi|80|");
        String izlaz = controller.OdaberiPravac(ulaz);

        Assert.assertEquals("0.0", izlaz.split("@@@")[2].split("\\|")[1]);
    }
}
