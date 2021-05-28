using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Frontend
{
    public partial class Form1 : Form
    {
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


            if (DateTime.Now.DayOfWeek == DayOfWeek.Monday)
            {
                ponedeljak.BackColor = Color.FromArgb(100, 100, 164);
                ponedeljak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Tuesday)
            {
                utorak.BackColor = Color.FromArgb(100, 100, 164);
                utorak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Wednesday)
            {
                sreda.BackColor = Color.FromArgb(100, 100, 164);
                sreda.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Thursday)
            {
                cetvrtak.BackColor = Color.FromArgb(100, 100, 164);
                cetvrtak.Paint += new System.Windows.Forms.PaintEventHandler(this.Nacrtaj);
            }
            else if (DateTime.Now.DayOfWeek == DayOfWeek.Friday)
            {
                petak.BackColor = Color.FromArgb(100, 100, 164);
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
    }
}
