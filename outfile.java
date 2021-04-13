import java.io. *;
import java.awt. *;
public class outfile
// writes the output file
{
  private BufferedWriter out;
  private String str,
    fname;
  private int i,
    year,
    day,
    hour,
    min,
    secc;
  private double sec;
  private time t;
  public outfile(time tt)
  {
    t = tt;
  }
  void openoutfile(global g, disp d, Graphics gg)
  {
    try
    {
      sec = (double)t.getTsec(g, d, gg);
      fname = t.getYdh() + ".rad";
      str = g.get_recfile();
      if (str.length() > 2)
      {
        fname = str;
        if (str.indexOf(".rad") == -1 && str.indexOf(".RAD") == -1)
          fname += ".rad";
      }
      g.set_recfile(fname);
        out = new BufferedWriter(new FileWriter(fname, true));
        g.set_fstatus(1);
        d.set_bc(Color.green, 11);
        str =
        "* STATION LAT=" + d.dc((g.get_lat() * 180.0 / Math.PI), 7, 2) +
        " DEG LONGW=" + d.dc((g.get_lon() * 180.0 / Math.PI), 7, 2) + "\r\n";
      if (g.get_recmode() != 1)
          out.write(str);
        d.dtext(665.0, 490.0, gg, Color.red, "recording: " + g.get_recfile());
      if (g.get_azaxis_tilt() != 0.0 || g.get_elaxis_tilt() != 0.0)
      {
        str =
          "* AZTILT=" + d.dc(g.get_azaxis_tilt(), 7, 2) +
          " TILTAZ=" + d.dc(g.get_tilt_az(), 7, 2) +
          " ELTILT=" + d.dc(g.get_elaxis_tilt(), 7, 2) + "\r\n";
        if (g.get_recmode() != 1)
          out.write(str);
      }
    }
    catch(IOException e)
    {
      System.out.println(e);
      g.set_fstatus(-1);
      g.set_recfile("");
    }
  }
  void writeoutfile(global g, disp d, Graphics gg)
  {
    String str4 = " ";
    if ((g.get_recform()&1) == 1)
      str4 = "\t";
    t.getTsec(g, d, gg);
    try
    {
      str = t.getYdhms() + str4 + d.dc(g.get_aznow(), 6, 1) + str4 +
        d.dc(g.get_elnow(), 5, 1) + str4 +
        d.dc(g.get_azoff(), 5, 1) + str4 + d.dc(g.get_eloff(), 5, 1) + str4 +
        d.dc(g.get_freq0(), 8, 2);
      out.write(str);
      if (g.get_digital() > 0)
      {
        str = str4 + d.dc(g.get_freqsep(), 10, 8) + str4 +
          d.dc((double)g.get_digital(), 3, 0) + str4 +
          d.dc((double)g.get_nfreq(), 3, 0);
        out.write(str);
      }
      for (i = 0; i < g.get_nfreq(); i++)
        out.write(str4 + d.dc(g.get_spec(i), 5, 1));
      if (g.get_recmode() == 2 || (g.get_recform()&2) == 2)
        out.write(str4 + "vlsr" + str4 + d.dc(g.get_vlsr(), 8, 2));
      out.write("\r\n");
      d.dtext(665.0, 490.0, gg, Color.red, "recording: " + g.get_recfile());
    }
    catch(IOException e)
    {
      System.out.println(e);
      g.set_fstatus(-1);
      g.set_recfile("");
    }
  }
  void stroutfile(global g, String str)
  {
    try
    {
      if (g.get_recmode() != 1)
        out.write(str + "\r\n");
    }
    catch(IOException e)
    {
      System.out.println(e);
      g.set_fstatus(-1);
      g.set_recfile("");
    }
  }
  void closeoutfile(global g, disp d, Graphics gg)
  {
    try
    {
      out.close();
      d.set_bc(Color.black, 11);
      g.set_fstatus(0);
      d.ppclear(gg, 665.0, 490.0, 140.0);
      g.set_click(0);
      g.set_recfile("");
    }
    catch(IOException e)
    {
      System.out.println(e);
      g.set_fstatus(-1);
      g.set_recfile("");
    }
  }
}
