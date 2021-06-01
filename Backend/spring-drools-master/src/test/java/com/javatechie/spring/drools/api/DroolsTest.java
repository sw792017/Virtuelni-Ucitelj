package com.javatechie.spring.drools.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DroolsTest extends UnitTest{

    @Autowired
    private Controller controller;

    private static final String tehnike  = "Test Tehnika1|60|01/01/2021|pt1_1@50@100%pt2_1@50@100%Menjanje akorda E5, A5, C5@50@26%pt4_1@50@0%pt5_1@50@0@@@Test Tehnika2|55|01/01/2021|pt1_2@50@100%pt2_2@50@100%pt3_2@50@100%pt4_2@50@100%Sweep picking@50@80@@@Test Tehnika3|15|01/01/2021|pt1_3@50@100%pt2_3@50@100%pt3_3@50@100%Hammeron-pulloff-over@50@0%pt5_3@50@0@@@Test Tehnika4|65|01/01/2021|pt1_4@50@100%pt2_4@50@100%pt3_4@50@100%Menjanje akorda Am, A, Dm, D@50@0%pt5_4@50@0@@@Test Tehnika5|90|01/01/2021|pt1_5@50@100%pt2_5@50@100%Tapkanje na 2 zice@50@10%pt4_5@50@0%pt5_5@50@0@@@Test";;

    @Before
    public void setUp(){

    }

    @Test
    public void PresaoTest(){
        String ulaz = "15||Test Tehnika|60|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0";
        String[] ocekivano = {"61.0", "75.0"};

        //Test Tehnika|60.0|01/01/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@50.0%pt4@50.0@0.0%pt5@50.0@0.0
        //Test Tehnika|61.0|01/01/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@75.0%pt4@50.0@0.0%pt5@50.0@0.0

        String izlaz = controller.Presao(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("\\|")[1]);//Proverimo da li se prioritet povecao
        Assert.assertEquals(ocekivano[1], izlaz.split("%")[2].split("@")[2]);//Proverimo da li se savladanost podtehnike povecala
    }

    @Test
    public void PresaoTest_TehnikeSaNula(){
        String ulaz = "5||Test Tehnika|65|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@100%pt4@50@0%pt5@50@0";
        String[] ocekivano = {"66.0", "25.0"};

        //Test Tehnika|65.0|01/01/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@100.0%pt4@50.0@0.0%pt5@50.0@0.0
        //Test Tehnika|66.0|01/01/2021|pt1@50.0@100.0%pt2@50.0@100.0%pt3@50.0@100.0%pt4@50.0@25.0%pt5@50.0@0.0

        String izlaz = controller.Presao(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("\\|")[1]);//Proverimo da li se prioritet povecao
        Assert.assertEquals(ocekivano[1], izlaz.split("%")[3].split("@")[2]);//Proverimo da li se savladanost podtehnike povecala
    }

    @Test
    public void ZvukTest_Rock(){
        String ulaz = "Rock||" + tehnike;
        String[] ocekivano = {"68.0", "64.0", "49.0", "65.0", "90.0"};

        String izlaz = controller.OdaberiZvuk(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void ZvukTest_Narodna(){
        String ulaz = "Narodna||" + tehnike;
        String[] ocekivano = {"82.0", "64.0", "74.5", "65.0", "90.0"};

        String izlaz = controller.OdaberiZvuk(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void PravacTest_Glavna(){
        String ulaz = "Glavna||" + tehnike;
        String[] ocekivano = {"70.0", "73.0", "49.0", "82.5", "96.0"};

        String izlaz = controller.OdaberiPravac(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void PravacTest_Bas(){
        String ulaz = "Bas||" + tehnike;
        String[] ocekivano = {"98.0", "91.0", "49.0", "68.5", "98.0"};

        String izlaz = controller.OdaberiPravac(ulaz);

        Assert.assertEquals(ocekivano[0], izlaz.split("@@@")[0].split("\\|")[1]);//Proverimo prioritete svake tehnike
        Assert.assertEquals(ocekivano[1], izlaz.split("@@@")[1].split("\\|")[1]);
        Assert.assertEquals(ocekivano[2], izlaz.split("@@@")[2].split("\\|")[1]);
        Assert.assertEquals(ocekivano[3], izlaz.split("@@@")[3].split("\\|")[1]);
        Assert.assertEquals(ocekivano[4], izlaz.split("@@@")[4].split("\\|")[1]);
    }

    @Test
    public void RasporedTest(){
        String ulaz = "3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike;
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 1h|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 30min";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedTest_DvaTestaSaIstimPrioritetom(){
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike).replace("|15|", "|65|");
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 1h|Vezbaj Hammeron-pulloff-over 30min|Vezbaj Hammeron-pulloff-over 30min";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }

    @Test
    public void RasporedTest_DvaTestaSaIstimPrioritetomNaIvici(){
        String ulaz = ("3@Zadatak1|Zadatak2|Za" + "[_]" + tehnike).replace("|55|", "|60|");
        String ocekivano = "3@Vezbaj Tapkanje na 2 zice 1h|Vezbaj Menjanje akorda Am, A, Dm, D 30min|Vezbaj Menjanje akorda E5, A5, C5 30min";

        String izlaz = controller.NapraviRaspored(ulaz);

        Assert.assertEquals(ocekivano, izlaz);
    }
}
