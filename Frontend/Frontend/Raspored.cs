using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Frontend
{
    class Raspored
    {
        private int brTehnika;
        private List<string> zadaci;

        public Raspored(int brTehnika, List<string> zadaci)
        {
            this.brTehnika = brTehnika;
            this.zadaci = zadaci;
        }

        public Raspored(int brTehnika)
        {
            this.brTehnika = brTehnika;
            this.zadaci = new List<string>();
            for(int i = 0; i < 5; ++i)
            {
                zadaci.Add(" ");
            }
        }

        public Raspored(String str)
        {
            this.brTehnika = Convert.ToInt32(str.Split('@')[0]);
            this.zadaci = new List<string>();
            foreach(String ss in str.Split('@')[1].Split('|'))
            {
                this.zadaci.Add(ss);
            }
        }

        public String toString()
        {
            String s = brTehnika + "@";
            foreach (String str in this.zadaci)
            {
                s += str + "|";
            }
            return s.Remove(s.Length - 1);
        }

        public int getBrTehnika()
        {
            return brTehnika;
        }


        public void setBrTehnika(int brTehnika)
        {
            this.brTehnika = brTehnika;
        }


        public List<String> getZadaci()
        {
            return zadaci;
        }


        public void setZadaci(List<String> zadaci)
        {
            this.zadaci = zadaci;
        }

        public void addZadatak(int i, String s)
        {
            try
            {
                this.zadaci[i] = s;
            }
            catch (Exception e)
            {
                for (int j = 0; j < i; ++j)
                    try
                    {
                        if (this.getZadatak(j) == "")
                            this.addZadatak(j, "Zadatak" + (j + 1));
                    }
                    catch (Exception e2)
                    {
                        List<String> rr = this.getZadaci();
                        rr.Add("Zadatak" + (j + 1));
                        this.setZadaci(rr);
                    }
            }
        }

        public String getZadatak(int i)
        {
            try
            {
                return this.zadaci[i];
            }
            catch(Exception e)
            {
                return "";
            }
        }
    }
}
