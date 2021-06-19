using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Frontend
{
    class Tehnika
    {
        private String naziv;
        private List<PodTehnika> podtehnike;
        private double prioritet;
        private string presao;


        public String toString()
        {
            //Test Tehnika|60|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0
            String str = naziv + "|" + prioritet + "|" + presao + "|";
            foreach(PodTehnika pt in podtehnike)
            {
                str += pt.getNaziv() + "@" + pt.getKorak() + "@" + pt.getSavadao() + "%";
            }
            return str.Remove(str.Length - 1);
        }

        public int comparedTo(Tehnika o1)
        {
            return this.getPodtehnika().getSavadao() > o1.getPodtehnika().getSavadao() ? -1 : 1;
        }

        public Tehnika(String naziv, List<PodTehnika> podtehnike, double prioritet, string presao)
        {
            this.naziv = naziv;
            this.podtehnike = podtehnike;
            this.prioritet = prioritet;
            this.presao = presao;
        }

        public Tehnika(String json)
        {
            String[] niz = json.Split('|');

            this.naziv = niz[0];
            this.prioritet = Convert.ToDouble(niz[1]);

            this.presao = niz[2];

            this.podtehnike = new List<PodTehnika>();
            //Test Tehnika1|60|01/01/2021|pt1_1@50@100%pt2_1@50@100%Menjanje akorda E5, A5, C5@50@26%pt4_1@50@0%pt5_1@50@0
            //pt1@50@100%pt2@50@100%pt3@50@100%pt4@50@75%pt5@50@0
            foreach (String linija in niz[3].Split('%'))
            {
                String[] pt_str = linija.Split('@');
                PodTehnika pt = new PodTehnika(pt_str[0], Convert.ToDouble(pt_str[1]), Convert.ToDouble(pt_str[2]));
                this.podtehnike.Add(pt);
            }
            /*
                Test Tehnika|60|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0
            */
        }


        public static string TehnikeSTR(List<Tehnika> tehnike)
        {
            string sss = "";

            foreach(Tehnika t in tehnike)
            {
                sss += t.toString() + "@@@";
            }

            return sss.Remove(sss.Length - 3);
        }

        public static List<Tehnika> IzFajla()
        {
            string str = "";

            try{
                str = File.ReadAllText(@"tehnike.txt");
            }catch(Exception e)
            {
                using (FileStream fs = File.Create(@"tehnike.txt"))
                {
                    byte[] info = new UTF8Encoding(true).GetBytes(
                        "Akordi|15|01/01/2021|" +
                            "Formiranje A akorda@50@0%" +
                            "Formiranje Am akorda@50@0%" +
                            "Menjanje akorda A, Am" +
                            "Formiranje D akorda@50@0%" +
                            "Formiranje Dm akorda@50@0%" +
                            "Menjanje akorda D, Dm@100@0%" +
                            "Formiranje C akorda@50@0%" +
                            "Formiranje Cm akorda@50@0%" +
                            "Menjanje akorda A, D, C@100@0%" +
                            "Formiranje G akorda@50@0%" +
                            "Formiranje F akorda@50@0%" +
                            "Formiranje D akorda@50@0%" +
                            "Menjanje akorda G, D, Em, C@100@0" +// 1 5 6 4 :)
                        "@@@" +
                        "Strumming|10|01/01/2021|" +
                            "Strumming D U D U D U D U@50@0%" +
                            "Strumming D D U D U D U@50@0%" +
                            "Strumming D D U D D U@50@0%" +
                            "Strumming D D U D D@50@0%" +
                            "Strumming D D D U D U@50@0%" +
                            "Strumming D D U U D U@50@0" +
                        "@@@" +
                        "Prigusivanje|0|01/01/2021|" +
                            "Standardno prigusivanje@50@0%" +
                            "Progresivno prigusivanje levom rukom@50@0%" +
                            "Progresivno prigusivanje desnom rukom zaica iznad dlana@50@0%" +
                            "Progresivno prigusivanje desnom rukom zaica ispod prstiju@50@0%" +
                            "”Duh” note@50@0%" +
                            "Prigusene note@50@0" +
                        "@@@" +
                        "Sweep picking|0|01/01/2021|" +
                            "Sweep picking na 2 zice@50@0%" +
                            "Sweep picking na 3 zice@50@0%" +
                            "Sweep picking na 4 zice@50@0%" +
                            "Sweep picking na 2 zice sa preskakanjem@50@0%" +
                            "Sweep picking na 3 zice sa preskakanjem…@50@0" +
                        "@@@" +
                        "Harminike|0|01/01/2021|" +
                            "Prirodne harmonike@50@0%" +
                            "Vestacke harmovnike@50@0%" +
                            "”Pinch”  harmonike@50@0%" +
                            "”Tapped” harmonike@50@0"
                    );
                    fs.Write(info, 0, info.Length);
                    fs.Close();

                    str = File.ReadAllText(@"tehnike.txt");
                }
            }

            return TehnikeIzSTR(str);
        }

        public static List<Tehnika> TehnikeIzSTR(string tehnikeSTR)
        {
            List<Tehnika> tehnike = new List<Tehnika>();

            foreach (String s in tehnikeSTR.Split(new String[] { "@@@" }, StringSplitOptions.None))
            {
                tehnike.Add(new Tehnika(s));
            }

            return tehnike;
        }


        public double getProcenat()
        {
            foreach (PodTehnika pt in this.getPodtehnike())
            {
                if (pt.getSavadao() < 100)
                    return pt.getSavadao();
            }
            return 100;
        }

        public PodTehnika getPodtehnika()
        {
            foreach (PodTehnika pt in this.getPodtehnike())
            {
                if (pt.getSavadao() < 100)
                    return pt;
            }
            return null;
        }

        public String getNaziv()
        {
            return naziv;
        }


        public void setNaziv(String naziv)
        {
            this.naziv = naziv;
        }


        public List<PodTehnika> getPodtehnike()
        {
            return podtehnike;
        }


        public void setPodtehnike(List<PodTehnika> podtehnike)
        {
            this.podtehnike = podtehnike;
        }


        public double getPrioritet()
        {
            return prioritet;
        }


        public void setPrioritet(double prioritet)
        {
            this.prioritet = prioritet;
        }


        public String getPresao()
        {
            return presao;
        }


        public void setPresao(String presao)
        {
            this.presao = presao;
        }
    }
}
