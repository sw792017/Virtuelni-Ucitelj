using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Frontend
{
    class PodTehnika
    {
        private String naziv;
        private double korak;
        private double savadao;


        public PodTehnika(String naziv, double korak, double savadao)
        {
            this.naziv = naziv;
            this.korak = korak;
            this.savadao = savadao;
        }


        public String toString()
        {
            return naziv;
        }


        public String getNaziv()
        {
            return naziv;
        }


        public void setNaziv(String naziv)
        {
            this.naziv = naziv;
        }


        public double getKorak()
        {
            return korak;
        }


        public void setKorak(double korak)
        {
            this.korak = korak;
        }


        public double getSavadao()
        {
            return savadao;
        }


        public void setSavadao(double savadao)
        {
            this.savadao = savadao;
        }
    }
}
