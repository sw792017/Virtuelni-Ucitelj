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
    public partial class minut : Form
    {
        List<Tuple<int, string>> zadaci;
        public minut(List<Tuple<int, string>> zadaci)
        {
            InitializeComponent();
            this.zadaci = zadaci;
        }

        private void minut_Load(object sender, EventArgs e)
        {
            int br = 2;
            foreach(Tuple<int, string> t in zadaci)
            {
                dodajNaGui(br, t.Item2);
                ++br;
            }
        }

        public void dodajNaGui(int br, string val)
        {
            try
            {
                Label lab = new Label();
                lab.Name = "l" + br;
                lab.Text = val.Remove(5, 1);
                lab.Location = new Point(100, 25 * br);
                Controls.Add(lab);

                NumericUpDown num = new NumericUpDown();
                num.Name = val;
                num.Value = 0;
                num.Width = 60;
                num.Location = new Point(20, (25 * br) - 2);
                num.Value = 50;
                Controls.Add(num);
            }
            catch (Exception e) { Console.Write("Ne bi trebao da ovde upadne " + e.Message); }
        }

        public List<Tuple<int, string>> GetData()
        {
            return zadaci;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            foreach (Object o in Controls)
            {
                if (o is NumericUpDown)
                {
                    for (int i = 0; i < zadaci.Count(); ++i)
                    {
                        if (zadaci[i].Item2 == (o as NumericUpDown).Name)
                        {
                            zadaci[i] = new Tuple<int, string>(Convert.ToInt32((o as NumericUpDown).Value), zadaci[i].Item2);
                        }
                    }
                }
            }

            this.Close();
        }
    }
}
