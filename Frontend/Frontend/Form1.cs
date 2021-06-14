using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Net;

namespace Frontend
{
    public partial class Form1 : Form
    {
        Raspored raspored;
        List<Tehnika> tehnike;
        string url = "http://localhost:8080";

        System.Media.SoundPlayer tick = new System.Media.SoundPlayer("clock.wav");
        int stanje = 5;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Ofarbaj();
            Datum();
            timer1.Start();
            ////
            raspored = new Raspored("2@ | | ");
            tehnike = Tehnika.IzFajla();
            NoviRaspored();
            NapuniDropDown();
            RefreshGUIRaspored();
        }

        private void Ofarbaj()
        {
            ponedeljak.BackColor = Color.FromArgb(234, 234, 246);
            utorak.BackColor = Color.FromArgb(234, 234, 246);
            sreda.BackColor = Color.FromArgb(234, 234, 246);
            cetvrtak.BackColor = Color.FromArgb(234, 234, 246);
            petak.BackColor = Color.FromArgb(234, 234, 246);
            subota.BackColor = Color.FromArgb(234, 234, 246);
            nedelja.BackColor = Color.FromArgb(234, 234, 246);

            dodajVezbu.BackColor = Color.FromArgb(100, 100, 164);
            button1.BackColor = Color.FromArgb(100, 100, 164);
            meni.BackColor = Color.FromArgb(100, 100, 164);

            LAB_PON.ForeColor = Color.FromArgb(100, 100, 164);
            LAB_SRE.ForeColor = Color.FromArgb(100, 100, 164);
            LAB_PET.ForeColor = Color.FromArgb(100, 100, 164);

            if (DateTime.Now.DayOfWeek == DayOfWeek.Monday)
            {
                ponedeljak.BackColor = Color.FromArgb(100, 100, 164);
                LAB_PON.ForeColor = Color.White;
                ponedeljak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Tuesday)
            {
                utorak.BackColor = Color.FromArgb(100, 100, 164);
                LAB_UTO.ForeColor = Color.White;
                utorak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Wednesday)
            {
                sreda.BackColor = Color.FromArgb(100, 100, 164);
                LAB_SRE.ForeColor = Color.White;
                sreda.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Thursday)
            {
                cetvrtak.BackColor = Color.FromArgb(100, 100, 164);
                LAB_CET.ForeColor = Color.White;
                cetvrtak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Friday)
            {
                petak.BackColor = Color.FromArgb(100, 100, 164);
                LAB_PET.ForeColor = Color.White;
                petak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Saturday)
            {
                subota.BackColor = Color.FromArgb(100, 100, 164);
                subota.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Sunday)
            {
                nedelja.BackColor = Color.FromArgb(100, 100, 164);
                nedelja.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
        }

        private void Nacrtaj(object sender, PaintEventArgs e)
        {
            int sada = DateTime.Now.Hour * 60 + DateTime.Now.Minute - 360;//dan pocinje od 6 AM (ne racunam vreme za spavanje)
            if (sada > 1320)
                return;

            base.OnPaint(e);
            using (Graphics g = e.Graphics)
            {
                Pen olovka = new Pen(Color.Red, 2);

                PointF A = new PointF(2, 200 * sada / 960);
                PointF B = new PointF(1000, 200 * sada / 960);

                g.DrawLine(olovka, A, B);
                g.DrawEllipse(olovka, A.X - 1, A.Y-3, 5, 5);

                //Random rng = new Random();
                //g.DrawLine(olovka, 0, rng.Next(0, 200),1000 , rng.Next(0, 200));
            }
        }

        private void Datum()
        {
            if (DateTime.Now.DayOfWeek == DayOfWeek.Monday)
            {
                var today = DateTime.Today;
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Tuesday)
            {
                var today = DateTime.Today.AddDays(-1);
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Wednesday)
            {
                var today = DateTime.Today.AddDays(-2);
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Thursday)
            {
                var today = DateTime.Today.AddDays(-3);
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Friday)
            {
                var today = DateTime.Today.AddDays(-4);
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Saturday)
            {
                var today = DateTime.Today.AddDays(-5);
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Sunday)
            {
                var today = DateTime.Today.AddDays(-6);
                ponedeljakL.Text = today.AddDays(0).Day.ToString();
                utorakL.Text = today.AddDays(1).Day.ToString();
                sredaL.Text = today.AddDays(2).Day.ToString();
                cetvrtakL.Text = today.AddDays(3).Day.ToString();
                petakL.Text = today.AddDays(4).Day.ToString();
                subotaL.Text = today.AddDays(5).Day.ToString();
                nedeljaL.Text = today.AddDays(-1).Day.ToString();
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            this.Refresh();
        }

        private void metronom_Tick(object sender, EventArgs e)
        {
            if (stanje == 18 || stanje == 9)
            {
                tick.Play();
                stanje += 1;
            }
            else if (stanje == 19)
            {
                stanje = 0;
            }
            else
                stanje += 1;
            this.Refresh();
            
        }

        private void metronomTick_Tick(object sender, EventArgs e)
        {
            
        }

        private void levimeni_Paint(object sender, PaintEventArgs e)
        {
            base.OnPaint(e);
            using (Graphics g = e.Graphics)
            {
                Pen olovka = new Pen(Color.Purple, 3);
                Pen hemijska = new Pen(Color.Red, 4);

                g.DrawLine(olovka, 40, 140, 80, 140);
                g.DrawLine(olovka, 40, 140, 25, 250);
                g.DrawLine(olovka, 95, 250, 80, 140);
                g.DrawLine(olovka, 95, 250, 25, 250);
                g.DrawLine(olovka, 90, 220, 30, 220);

                if (stanje == 0)
                    g.DrawLine(hemijska, 60, 220, 35, 170);
                else if (stanje == 1)
                    g.DrawLine(hemijska, 60, 220, 40, 168);
                else if (stanje == 2)
                    g.DrawLine(hemijska, 60, 220, 45, 166);
                else if (stanje == 3)
                    g.DrawLine(hemijska, 60, 220, 50, 164);
                else if (stanje == 4)
                    g.DrawLine(hemijska, 60, 220, 55, 162);
                else if (stanje == 5)
                    g.DrawLine(hemijska, 60, 220, 60, 160);
                else if (stanje == 6)
                    g.DrawLine(hemijska, 60, 220, 65, 162);
                else if (stanje == 7)
                    g.DrawLine(hemijska, 60, 220, 70, 164);
                else if (stanje == 8)
                    g.DrawLine(hemijska, 60, 220, 75, 166);
                else if (stanje == 9)
                    g.DrawLine(hemijska, 60, 220, 80, 168);
                else if (stanje == 10)
                    g.DrawLine(hemijska, 60, 220, 85, 170);
                else if (stanje == 11)
                    g.DrawLine(hemijska, 60, 220, 80, 168);
                else if (stanje == 12)
                    g.DrawLine(hemijska, 60, 220, 75, 166);
                else if (stanje == 13)
                    g.DrawLine(hemijska, 60, 220, 70, 164);
                else if (stanje == 14)
                    g.DrawLine(hemijska, 60, 220, 65, 162);
                else if (stanje == 15)
                    g.DrawLine(hemijska, 60, 220, 60, 160);
                else if (stanje == 16)
                    g.DrawLine(hemijska, 60, 220, 55, 162);
                else if (stanje == 17)
                    g.DrawLine(hemijska, 60, 220, 50, 164);
                else if (stanje == 18)
                    g.DrawLine(hemijska, 60, 220, 45, 166);
                else if (stanje == 19)
                    g.DrawLine(hemijska, 60, 220, 40, 168);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (button1.Text == "Pokreni")
            {
                button1.Text = "Zaustavi";
                if (stanje == 5)
                    stanje = 0;
                try
                {
                    metronom.Interval = 1000 * 60 / Convert.ToInt32(textBox1.Text);
                }
                catch {
                    metronom.Interval = 120;
                }


                metronom.Start();
            }
            else
            {
                button1.Text = "Pokreni";
                metronom.Stop();
                stanje = 5;
                this.Refresh();
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        public string POST(string link, string content)
        {
            var req = WebRequest.Create(url + link);
            req.Method = "POST";

            byte[] inp = Encoding.UTF8.GetBytes(content);

            req.ContentType = "application/json";
            req.ContentLength = inp.Length;

            Stream reqS = req.GetRequestStream();
            reqS.Write(inp, 0, inp.Length);

            WebResponse res = req.GetResponse();
            Console.WriteLine(((HttpWebResponse)res).StatusDescription);

            Stream respS = res.GetResponseStream();

            StreamReader rdr = new StreamReader(respS);
            return rdr.ReadToEnd();
        }

        public void NoviRaspored()
        {
            try
            {
                string ulaz = raspored.toString() + "[_]" + Tehnika.TehnikeSTR(tehnike);
                string data = POST("/Raspored", ulaz);
                raspored = new Raspored(data);
                Console.WriteLine(data);
            }
            catch (Exception e) { MessageBox.Show("Konekcija sa serverom nije uspela"); }
        }

        public void RefreshGUIRaspored()
        {
            if(raspored.getBrTehnika() == 3){
                LAB_PON.Text = raspored.getZadatak(0);
                LAB_SRE.Text = raspored.getZadatak(1);
                LAB_PET.Text = raspored.getZadatak(2);
                LAB_UTO.Text = raspored.getZadatak(3);
                LAB_CET.Text = raspored.getZadatak(4);
            }
            else {
                LAB_PON.Text = raspored.getZadatak(0);
                LAB_PET.Text = raspored.getZadatak(1);
                LAB_SRE.Text = raspored.getZadatak(035);//Uvek prazan dan (0 3 6 5)
                LAB_UTO.Text = raspored.getZadatak(3);
                LAB_CET.Text = raspored.getZadatak(4);
            }

            string s = "";
            foreach(Tehnika t in tehnike)
                s += t.toString().Replace("%", "\n\t       ") + "\n\n\n";
            debug.Text = s;

        }

        private void NapuniDropDown()
        {
            int i = 0;
            foreach (Tehnika t in tehnike){
                comboBox1.Items.Insert(i++, t.getNaziv());
            }
        }

        private void dodajVezbu_Click(object sender, EventArgs e)
        {
            comboBox1.Enabled = comboBox1.Enabled ? false : true;
            comboBox1.Visible = comboBox1.Visible ? false : true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            List<Tuple<int, string>> lista = new List<Tuple<int, string>>();

            for (int i = 0; i < 5; ++i)
                if(raspored.getZadatak(i)!="" && raspored.getZadatak(i) != " " && raspored.getZadatak(i).Contains("Vezbaj"))
                    try
                    {
                        lista.Add(new Tuple<int, string> (0, raspored.getZadatak(i)));
                    }catch(Exception exception) { Console.Write(exception.Message); }

            minut min = new minut(lista);
            min.ShowDialog();
            lista = min.GetData();


            //15||Test Tehnika|60|03/06/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0
            //br min   ||   tehnika.STR
            try
            {
                List<Tehnika> tt2 = new List<Tehnika>();
                

                for(int i = 0; i < raspored.getBrTehnika(); ++i){
                    string tehnika = POST("/get/" + i.ToString(), Tehnika.TehnikeSTR(tehnike));
                    if (tehnika == "" || tehnika == " ")
                    {
                        MessageBox.Show(Tehnika.TehnikeSTR(tehnike), url + "/get/" + i.ToString());
                        break;
                    }
                    string noviRaspored = POST("/Presao", (lista[i]).Item1 + "||" + tehnika);
                    update(tehnike, new Tehnika(noviRaspored));
                }

                ////// za kasnije

                if (comboBox1.Enabled == true)
                {
                    NoviRaspored();
                    string ulaz = (comboBox1.SelectedIndex) + "[_]" + (new Raspored(raspored.getBrTehnika())).toString() + "[_]" + Tehnika.TehnikeSTR(tehnike);
                    if (!(ulaz == "" || ulaz == " "))
                        raspored = new Raspored(POST("/CustomTehnika", ulaz));
                    MessageBox.Show(raspored.toString());
                }
                else {
                    NoviRaspored();
                }

                RefreshGUIRaspored();
            }
            catch (Exception exception) { MessageBox.Show("Konekcija sa serverom nije uspela"); Console.Write(exception.Message); }
        }

        private void update(List<Tehnika> tehnike, Tehnika tehnika)
        {
            if (tehnika.getNaziv() == "placeholder")
                return;

            for(int i = 0; i < tehnike.Count; ++i)
            {
                if(tehnike[i].getNaziv() == tehnika.getNaziv())
                {
                    tehnike[i] = tehnika;
                }
            }
        }
    }
}
