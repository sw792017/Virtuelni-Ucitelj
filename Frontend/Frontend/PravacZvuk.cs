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
    public partial class PravacZvuk : Form
    {
        Color c = Color.FromArgb(255, 0, 0);
        public PravacZvuk()
        {
            InitializeComponent();
        }

        private void PravacZvuk_Load(object sender, EventArgs e)
        {
            comboBox1.SelectedIndex = 0;
            comboBox2.SelectedIndex = 0;
        }

        public string GetPravac()
        {
            return comboBox2.Text;
        }

        public string GetZvuk()
        {
            return comboBox1.Text;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (c.R == 255 && c.G < 255 && c.B < 255)
                c = Color.FromArgb(c.R, c.G, c.B + 5);
            else if (c.R == 0 && c.G < 255 && c.B == 255)
                c = Color.FromArgb(c.R, c.G + 5, c.B);
            else if (c.R <= 255 && c.R != 0 && c.G < 255 && c.B == 255)
                c = Color.FromArgb(c.R - 5, c.G, c.B);
            else if (c.R < 255 && c.G == 255 && c.B == 0)
                c = Color.FromArgb(c.R + 5, c.G, c.B);
            else if (c.R == 0 && c.G == 255 && c.B < 255 && c.B > 5)
                c = Color.FromArgb(c.R, c.G, c.B - 5);
            else
                c = Color.FromArgb(c.R, c.G - 5, c.B);

            label5.ForeColor = c;
        }
    }
}
