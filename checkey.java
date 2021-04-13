import java.awt. *;
import java.util. *;
import java.text. *;
import java.lang. *;
public class checkey
// class to make specify action upon commands
{
  private Graphics gg;
  private StringTokenizer parser;
  private String str2,
    str3;
  private double ylim = 415.0;
  hdisp h;
  void set_graph(Graphics g)
  {
    gg = g;
  }
  void check(int mode, char cmd, global g, disp d, String str)
  {
// mode 1=button or text 2=mouse entered 3=mouse excited
    String str4;
    int i;
    if (mode == 2)
    {
      g.set_click(0);
      if (cmd == 'c')
        g.set_key(0);
      if (cmd == 'a')
        g.set_key(1);
      if (cmd == 'H')
        g.set_key(2);
      if (cmd == 'S')
        g.set_key(3);
      if (cmd == 't')
        g.set_key(4);
      if (cmd == 'A')
        g.set_key(5);
      if (cmd == 'n')
        g.set_key(6);
      if (cmd == 'b')
        g.set_key(7);
      if (cmd == 'f')
        g.set_key(8);
      if (cmd == 'o')
        g.set_key(9);
      if (cmd == 'D')
        g.set_key(10);
      if (cmd == 'r')
        g.set_key(11);
      if (cmd == 'R')
        g.set_key(12);
      if (cmd == 'C')
        g.set_key(13);
      if (cmd == 'V')
        g.set_key(14);
      d.pclear(gg, 8.0, ylim + 50.0, 650.0, 100.0);
    }
    if (g.get_key() == 0)
        d.dtext(8.0, ylim + 60.0, gg, Color.black,
                "click to clear screen and spectral average");
    if (g.get_key() == 1 && g.get_digital() == 0)
    {
      d.dtext(8.0, ylim + 60.0, gg, Color.black,
              "click to change atten");
      d.dtext(8.0, ylim + 74.0, gg, Color.black,
          "use attenuation if the radiometer saturates on a strong signal");
      d.dtext(8.0, ylim + 88.0, gg, Color.black,
              "radiometer saturates when the power exceeds 3000 counts");
    }
    if (g.get_key() == 1 && g.get_digital() > 0)
    {
      d.dtext(8.0, ylim + 60.0, gg, Color.black,
              "attenuator not needed for digital receiver");
      d.dtext(8.0, ylim + 74.0, gg, Color.black,
              "digital receiver saturates at a count of 50000");
    }
    if (g.get_key() == 2)
    {
      if (mode == 1)
      {
        if (g.get_hlp() == 0)
          h = new hdisp("srtHelp", g);
        else
          h.focus(g);
      }
//          d.pclear(gg, 8.0, ylim + 50.0, 650.0, 100.0);
      if (mode == 2)
      {
        str4 = "";
        for (i = 0; i < 4; i++)
        {
          if (i == 0)
            str4 = "to go to a source click directly on displayed source";
          if (i == 1)
            str4 = "move mouse to each command button for information on commands";
          if (i == 2)
            str4 = "click on help button for additional information";
          if (i == 3)
            str4 = "displayed sources are from the catalog file: srt.cat";
          d.stext(8.0, ylim + 60.0 + i * 14.0, gg, Color.black, str4);
        }
      }
    }

    if (g.get_key() == 3)
      d.dtext(8.0, ylim + 60.0, gg, Color.black,
              "click to go to Stow");
    if (g.get_key() == 4)
    {
      d.stext(8.0, ylim + 60.0, gg, Color.black,
              "click to stop drives click again to track source");
      d.stext(8.0, ylim + 74.0, gg, Color.black,
              "pointing cursors are yellow while slewing to source " +
              "and turn red when antenna reaches commanded position");
      d.stext(8.0, ylim + 88.0, gg, Color.black,
              "if pointing offsets are non zero cursors " +
              "will be displaced from source");
    }
    if (g.get_key() == 5 && mode == 2)
    {
      if (g.get_mainten() == 0)
        d.dtext(8.0, ylim + 60.0, gg, Color.black,
                "click on button to set azimuth and elevation");
      else
        d.dtext(8.0, ylim + 60.0, gg, Color.red,
                "in maintenance mode use offsets to move     ");
    }
    if (g.get_key() == 5 && mode == 1)
      d.dtext(8.0, ylim + 102.0, gg, Color.blue,
              "now click on text area and enter az and el    ");
    if (g.get_key() == 6)
    {
      d.dtext(8.0, ylim + 60.0, gg, Color.black,
              "click to start one 25 point scan - click again to abort");
      d.dtext(8.0, ylim + 74.0, gg, Color.black,
              "source or position must be in catalog");
    }
    if (g.get_key() == 7)
    {
      d.dtext(8.0, ylim + 60.0, gg, Color.black,
              "click to start beam switch - click to stop");
      d.dtext(8.0, ylim + 74.0, gg, Color.black,
              "source or position must be in catalog");
    }
    if (g.get_key() == 8 && mode == 2 && g.get_digital() == 0)
    {
      d.stext(8.0, ylim + 60.0, gg, Color.black,
        "click to set frequency (MHz), num of freqs, [spacing - optional]");
      d.stext(8.0, ylim + 74.0, gg, Color.black,
              "the frequency is the L.O. frequency " +
            " - the bandpass center for each frequency is 0.04 MHz higher");
      d.stext(8.0, ylim + 88.0, gg, Color.black,
              "the 21cm hydrogen line has a rest frequency of 1420.4 MHz");
      d.stext(8.0, ylim + 102.0, gg, Color.black,
              "default frequency for continuum is 1420.0 MHz " +
              " and a zero frequency spacing can be used to increase integration time");
    }
    if (g.get_key() == 8 && mode == 2 && g.get_digital() != 0)
    {
      d.stext(8.0, ylim + 60.0, gg, Color.black,
              "click to set center frequency (MHz), [optional mode]");
      d.stext(8.0, ylim + 74.0, gg, Color.black,
              "mode 1 - default bandwidth = 500 kHz " +
              " mode 2 - bw = 250 kHz" +
              " mode 3 - bw = 125 kHz" + " mode 4 - 3x500 kHz" +
              " mode 5 - scanmode");
      d.stext(8.0, ylim + 88.0, gg, Color.black,
              "the 21cm hydrogen line has a rest frequency of 1420.4 MHz");
      d.stext(8.0, ylim + 102.0, gg, Color.black,
              "default frequency for continuum is 1420.0 MHz " +
              " continuum uses average power from all frequencies");
    }
    if (g.get_key() == 8 && mode == 1 && g.get_digital() == 0)
      d.dtext(8.0, ylim + 60.0, gg, Color.blue,
              "click on text area to enter center frequency, num of freqs," +
              "[spacing default=0.04 MHz]");
    if (g.get_key() == 8 && mode == 1 && g.get_digital() != 0)
      d.dtext(8.0, ylim + 60.0, gg, Color.blue,
              "click on text area to enter center frequency, mode ");
    if (g.get_key() == 9 && mode == 2)
    {
      d.stext(8.0, ylim + 60.0, gg, Color.black,
              "click to set az and el pointing offsets");
      d.stext(8.0, ylim + 74.0, gg, Color.black,
              "these pointing offsets will remain until changed");
    }
    if (g.get_key() == 9 && mode == 1)
      d.dtext(8.0, ylim + 102.0, gg, Color.blue,
              "now click on text area and enter az and el offsets");
    if (g.get_key() == 10)
      d.dtext(8.0, ylim + 60.0, gg, Color.black,
              "click to move antenna ahead to start Drift scan");
    if (g.get_key() == 11 && mode == 2)
    {
      g.set_nclick(0);
      d.stext(8.0, ylim + 60.0, gg, Color.black,
              "click to start/stop recording data");
      d.stext(8.0, ylim + 74.0, gg, Color.black,
              "default file: YYDDDHH.rad");
      if (g.get_digital() > 0)
      {
      d.stext(8.0, ylim + 88.0, gg, Color.black,
              "record format:time(YYYY:DDD:HH:MM:SS) az(deg) el(deg) " +
              "azoffset eloffset first_frequency(MHz) freq_spacing(MHZ) mode");
      d.stext(8.0, ylim + 102.0, gg, Color.black,
              "nmber_of_frequencies following by p[0] p[1] p[2] ...p[last] (K) " +
              " [optional VLSR (km/s)]");
      }
      else
      {
      d.stext(8.0, ylim + 88.0, gg, Color.black,
              "record format:time(YYYY:DDD:HH:MM:SS) az(deg) el(deg) " +
              "azoffset eloffset first_frequency(MHz) p[0] p[1] p[2] .. p[last] (K)");
      d.stext(8.0, ylim + 102.0, gg, Color.black,
              "first_frequency is receiver local oscillator frequency " +
              " which is the start of the bandpass");
      }
    }
    if (g.get_key() == 11 && mode == 1)
      d.dtext(8.0, ylim + 102.0, gg, Color.blue,
              "now click on text area and enter record file name" +
              " [recordmode] or click again for default");
    if (g.get_key() == 12)
    {
      if (g.get_cmdfile().length() > 2 && mode == 2)
      {
        str4 = "";
        for (i = 0; i < 4; i++)
        {
          if (i == 0)
            str4 = "click to start " + g.get_cmdfile() +
              " command file use help button for format details";
          if (i == 1)
            str4 = "rules: reads one line at a time skipping blank lines,"
              + " and lines which start with * and lines with past times";
          if (i == 2)
            str4 = "stops at and executes any line with current or future time"
              + " time format:line starts with yyyy:ddd:hh:mm:ss and is" +
              " followed by cmd";
          if (i == 3)
            str4 = "alternate format:   LST:hh:mm:ss   cmd"
              + " current time format:   :   cmd";
          d.stext(8.0, ylim + 60.0 + i * 14.0, gg, Color.black, str4);
        }
      }
      if (g.get_cmdfile().length() <= 2)
      {
        if (mode == 2)
        {
          d.ppclear(gg, 8.0, ylim + 60.0, 640.0);
          d.dtext(8.0, ylim + 60.0, gg, Color.black,
                  "click to enter command file name");
        }
        else if (mode == 1)
          d.dtext(8.0, ylim + 60.0, gg, Color.black,
                  "click on text area and enter command filename below");
      }
    }
    if (g.get_key() == 14)
    {
      if (g.get_mancal() != 0)
        d.dtext(8.0, ylim + 60.0, gg, Color.black,
                "click to start manual calibration");
      else
        d.dtext(8.0, ylim + 60.0, gg, Color.black,
                "click to calibrate using motor driven vane");
      d.dtext(8.0, ylim + 74.0, gg, Color.black,
              "assumes absorber load temperature Tload = "
              + d.dc(g.get_tload(), 3, 0) + " K");
      d.dtext(8.0, ylim + 88.0, gg, Color.black,
              "assumes antenna spillover temperature Tspill = "
              + d.dc(g.get_tspill(), 3, 0) + " K");
      d.dtext(8.0, ylim + 102.0, gg, Color.black,
              "Prec/Psky = (Trec + Tload)/(Trec + Tspill)");
    }
    if (g.get_key() == 13)
    {
      if (g.get_noisecal() > 0.0)
      {
        d.stext(8.0, ylim + 60.0, gg, Color.black,
                "click to calibrate using noisecal");
        d.stext(8.0, ylim + 74.0, gg, Color.black,
                "assumes noisecal = "
                + d.dc(g.get_noisecal(), 3, 0) + " K");
        d.stext(8.0, ylim + 88.0, gg, Color.black,
                "assumes antenna spillover temperature Tspill = "
                + d.dc(g.get_tspill(), 3, 0) + " K");
        d.stext(8.0, ylim + 102.0, gg, Color.black,
                "Pnoise_on/Psky = (Tsys + Tnoise)/(Tsys)");
        d.stext(8.0, ylim + 1116.0, gg, Color.black,
                "Trec = Tsys - Tspill");
      }
      else
      {
        d.dtext(8.0, ylim + 60.0, gg, Color.black,
                "noisecal not available - use Vane");
        d.dtext(8.0, ylim + 74.0, gg, Color.black,
                "if you have a diode noise calibrator add NOISECAL value to srt.cat");
      }
    }
    if (mode == 3 && g.get_click() == 0)
      d.pclear(gg, 8.0, ylim + 50.0, 650.0, 100.0);
    if (mode == 1)
    {
      if (g.get_key() == 5 || g.get_key() == 8 || g.get_key() == 9 ||
          (g.get_key() == 12 && g.get_cmdfile().length() <= 2))
        g.set_click(-1);
      else
        g.set_click(1);
      g.set_cmdstr(str);
    }
  }
  void checky(global g, disp d, Graphics gg, outfile out)
  {
    str3 = g.get_cmdstr();
// System.out.println("key "+g.get_key()+" str3 "+str3);
    if (g.get_key() == 11)
    {
      if (g.get_fstatus() == 1)
      {
        out.closeoutfile(g, d, gg);
        return;
      }
      if (g.get_fstatus() == 0)
      {
        if (str3.length() > 2)
        {
          parser = new StringTokenizer(str3);
          try
          {
            str2 = parser.nextToken();
          }
          catch(NoSuchElementException e)
          {
            return;
          }
          g.set_recfile(str2);
          g.set_recmode(0);
          try
          {
            str2 = parser.nextToken();
            try
            {
              g.set_recmode((int)Double.valueOf(str2).doubleValue());
            }
            catch(NumberFormatException e)
            {
              d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
              return;
            }
          }
          catch(NoSuchElementException e)
          {
          }
          str2 = "entered "
            + g.get_recfile();
          if (g.get_recmode() != 0)
            str2 += " " + g.get_recmode();
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, str2);
        }
        if (str3.length() > 2 || g.get_nclick() > 0)
        {
          out.openoutfile(g, d, gg);
          return;
        }
        g.set_nclick(1);
      }
    }
    if (g.get_key() == 12)
    {
      if (g.get_cmdf() == 0)
      {
        if (str3.length() > 2)
        {
          g.set_cmdfile(str3);
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "entered " + g.get_cmdfile());
        }
        if (g.get_cmdfile().length() > 2)
        {
          g.set_cmdf(1);
          g.set_cmdfline(0);
          d.set_bc(Color.green, 12);
          if (g.get_fstatus() == 1)
            out.stroutfile(g, "* cmdfile " +
                           g.get_cmdfile());
        }
      }
      else
      {
        g.set_cmdf(0);
        g.set_cmdfline(0);
        d.set_bc(Color.black, 12);
      }
    }
    if (g.get_key() == 4)
    {
      g.set_bsw(0);
      g.set_azoff(0.0);
      g.set_eloff(0.0);
      g.set_clr(1);
      g.set_stow(0);
      if (g.get_track() == -1)
        g.set_track(0);
      else
        g.set_track(-1);        // stop drives

    }
    if (g.get_key() == 1 && g.get_digital() == 0)
    {
      g.set_atten(g.get_atten() + 1);
      if (g.get_atten() >= 2)
        g.set_atten(0);
      g.set_clr(1);
    }
    if (g.get_key() == 6 && g.get_sourn() > 0)
    {
      if (g.get_scan() != 0)
      {
        g.set_scan(0);
        g.set_azoff(0.0);
        g.set_eloff(0.0);
      }
      else
      {
        if (g.get_stow() == 0 && g.get_track() != 0)
        {
          g.set_scan(g.get_scan() + 1);
          g.set_bsw(0);
          g.set_azoff(0.0);
          g.set_eloff(0.0);
          if (g.get_scan() >= 2)
            g.set_scan(0);
        }
      }
    }
    if (g.get_key() == 6 && g.get_sourn() == 0)
        d.dtext(450.0, ylim + 60.0, gg, Color.blue, "select source for beamsw ");       
    if (g.get_key() == 7 && g.get_sourn() > 0)
    {
      if (g.get_bsw() != 0)
      {
        g.set_bsw(0);
        g.set_azoff(0.0);
        g.set_eloff(0.0);
        g.set_track(0);
      }
      else
      {
        if (g.get_stow() == 0 && g.get_track() != 0)
        {
          g.set_azoff(0.0);
          g.set_eloff(0.0);
          if (g.get_bsw() == 0)
          {
            g.set_bsw(1);
            g.set_sig(-1);
            g.set_bswcycles(0);
            g.set_bswav(0.0);
            g.set_bswsq(0.0);
            g.set_bswlast(0.0);
            g.set_scan(0);
            g.set_clr(1);
          }
        }
      }
    }
    if (g.get_key() == 7 && g.get_sourn() == 0)
        d.dtext(450.0, ylim + 60.0, gg, Color.blue, "select source for beamsw ");       
    if (g.get_key() == 3)
    {
      g.set_clr(1);
      g.set_scan(0);
      g.set_bsw(0);
      g.set_track(0);
      g.set_sourn(0);
      g.set_azcmd(g.get_azlim1());
      g.set_elcmd(g.get_ellim1());
      g.set_stow(1);            // force stow

    }
    if (g.get_key() == 10)
      g.set_drift(1);
    if (g.get_key() == 0)
    {
      g.set_clr(-1);
      return;
    }
    if (g.get_key() == 14)
    {
      g.set_clr(1);
      g.set_docal(1);
      g.set_stopproc(0);        // restart if stopped

    }
    if (g.get_key() == 13)
    {
      g.set_clr(1);
      g.set_docal(2);
      g.set_stopproc(0);        // restart if stopped

    }
    if (g.get_key() == 5)
    {
      if (str3.length() > 2)
      {
        parser = new StringTokenizer(str3);
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          return;
        }
        try
        {
          g.set_azcmd(Double.valueOf(str2).doubleValue());
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "insufficient ");
          return;
        }
        try
        {
          g.set_elcmd(Double.valueOf(str2).doubleValue());
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        d.dtext(450.0, ylim + 60.0, gg, Color.blue, "entered az " + d.dc(g.get_azcmd(), 5, 1)
                + " el " + d.dc(g.get_elcmd(), 5, 1));
      }
      g.set_track(0);
      g.set_sourn(0);
      return;
    }
    if (g.get_key() == 9)
    {
      if (str3.length() > 2)
      {
        parser = new StringTokenizer(str3);
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          return;
        }
        try
        {
          g.set_pazoff(Double.valueOf(str2).doubleValue());
          if (g.get_mainten() == 1)
            g.set_azcmd(g.get_aznow() + g.get_pazoff());
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "insufficient ");
          return;
        }
        try
        {
          g.set_peloff(Double.valueOf(str2).doubleValue());
          if (g.get_mainten() == 1)
            g.set_elcmd(g.get_elnow() + g.get_peloff());
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        d.dtext(450.0, ylim + 60.0, gg, Color.blue, "entered azoff " + d.dc(g.get_pazoff(), 5, 1)
                + " eloff " + d.dc(g.get_peloff(), 5, 1));
        if (g.get_fstatus() == 1)
          out.stroutfile(g, "* entered azoff "
                         + d.dc(g.get_pazoff(), 5, 1) + " eloff " + d.dc(g.get_peloff(), 5, 1));
      }
    }
    if (g.get_key() == 8 && g.get_digital() == 0)
    {
      g.set_stopproc(1);
      if (str3.length() > 2)
      {
        parser = new StringTokenizer(str3);
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          return;
        }
        try
        {
          g.set_fcenter(Double.valueOf(str2).doubleValue());
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "insufficient ");
          return;
        }
        try
        {
          g.set_nfreq(Integer.valueOf(str2).intValue());
          if (g.get_nfreq() < 1)
            g.set_nfreq(1);
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        try
        {
          str2 = parser.nextToken();
          g.set_freqsep(Double.valueOf(str2).doubleValue());
        }
        catch(NoSuchElementException e)
        {
        }
        d.dtext(450.0, ylim + 60.0, gg, Color.blue, "entered freq " + d.dc(g.get_fcenter(), 7, 2)
                + " nfreq " + g.get_nfreq());
        if (g.get_nfreq() > 500)
          g.set_nfreq(500);
        if (g.get_fstatus() == 1)
          out.stroutfile(g, "* entered freq "
                 + d.dc(g.get_fcenter(), 7, 2) + " nfreq " + g.get_nfreq());
        g.set_clr(1);
        g.set_stopproc(0);
      }
    }
    if (g.get_key() == 8 && g.get_digital() != 0)
    {
      g.set_stopproc(1);
      if (str3.length() > 2)
      {
        parser = new StringTokenizer(str3);
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          return;
        }
        try
        {
          g.set_fcenter(Double.valueOf(str2).doubleValue());
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "entered freq "
                  + d.dc(g.get_fcenter(), 7, 2));
          return;
        }
        try
        {
          g.set_digital(Integer.valueOf(str2).intValue());
          if (g.get_digital() < 1)
            g.set_digital(1);
        }
        catch(NumberFormatException e)
        {
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
          return;
        }
        if (g.get_digital() == 5)
        {
          g.set_nfreq(40);      // default

          g.set_freqsep(1.0);   // default
          g.set_intg(0.52488);

          try
          {
            str2 = parser.nextToken();
          }
          catch(NoSuchElementException e)
          {
            d.dtext(450.0, ylim + 60.0, gg, Color.blue, "insufficient ");
            return;
          }
          try
          {
            g.set_nfreq(Integer.valueOf(str2).intValue());
            if (g.get_nfreq() < 1)
              g.set_nfreq(1);
          }
          catch(NumberFormatException e)
          {
            d.dtext(450.0, ylim + 60.0, gg, Color.blue, "format error ");
            return;
          }
          try
          {
            str2 = parser.nextToken();
            g.set_freqsep(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
          }
          d.dtext(430.0, ylim + 60.0, gg, Color.blue, "entered freq " + d.dc(g.get_fcenter(), 7, 2)
                  + " nfreq " + g.get_nfreq() + " spacing " + d.dc(g.get_freqsep(), 4, 2));
          if (g.get_nfreq() > 500)
            g.set_nfreq(500);
        }
        if (g.get_digital() < 5)
          d.dtext(450.0, ylim + 60.0, gg, Color.blue, "entered freq " + d.dc(g.get_fcenter(), 7, 2)
                  + " mode " + g.get_digital());
        if (g.get_digital() == 1)
        {
          g.set_freqsep(0.0078125);
          g.set_intg(0.52488);
          g.set_nfreq(64);
        }
        if (g.get_digital() == 2)
        {
          g.set_freqsep(0.00390625);
          g.set_intg(0.52488*2.0);
          g.set_nfreq(64);
        }
        if (g.get_digital() == 3)
        {
          g.set_freqsep(0.001953125);
          g.set_intg(0.52488*4.0);
          g.set_nfreq(64);
        }
        if (g.get_digital() == 4)
        {
          g.set_freqsep(0.0078125);
          g.set_intg(0.52488);
          g.set_nfreq(156);
        }
        if (g.get_fstatus() == 1)
          out.stroutfile(g, "* entered freq "
                + d.dc(g.get_fcenter(), 7, 2) + " mode " + g.get_digital());
        g.set_clr(1);
        g.set_stopproc(0);
      }
    }
    g.set_cmdstr("");           // set null string

  }
}
