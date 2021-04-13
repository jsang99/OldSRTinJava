import java.io. *;
import java.util. *;
import java.text. *;
import java.lang. *;
import java.awt. *;
public class cat
// reads catalog file
{
  double dec;
  double ylim = 415.0;
  public void catfile(global global, disp d, Graphics gg)
  {
    try
    {
      BufferedReader in =
      new BufferedReader(new FileReader(new File("srt.cat")));
      double rah,
        ram,
        rass,
        decd,
        decm,
        decss,
        ep,
        glat,
        glon;
      int decsign,
        i;
      global.set_nsou(1);
      String str,
        str1,
        str2;
      StringTokenizer parser;
      while ((str1 = in.readLine()) != null)
      {
        if (!str1.startsWith("*"))
        {
          i = 0;
          str = "";
          while (i < str1.length() && str1.charAt(i) != '/')
          {
            str += str1.charAt(i);
            i++;
          }
          if (str.indexOf("STATION") != -1)
              try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_lat(Double.valueOf(str2).doubleValue() * Math.PI / 180.0);
              str2 = parser.nextToken();
            global.set_lon(Double.valueOf(str2).doubleValue() * Math.PI / 180.0);
              str2 = parser.nextToken();
            global.set_statnam(str2);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("AZLIMITS") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_azlim1(Double.valueOf(str2).doubleValue());
            str2 = parser.nextToken();
            global.set_azlim2(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("ELLIMITS") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_ellim1(Double.valueOf(str2).doubleValue());
            str2 = parser.nextToken();
            global.set_ellim2(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("RECORDFORM") != -1)
          {
            if (str.indexOf("TAB") != -1)
               global.set_recform(global.get_recform()|1);
            if (str.indexOf("VLSR") != -1)
               global.set_recform(global.get_recform()|2);
            if (str.indexOf("DAY") != -1)
               global.set_recform(global.get_recform()|4);
          }
          if (str.indexOf("DIGITAL") != -1)
          {
            global.set_digital(1);
            global.set_freqsep(0.0078125);
            global.set_intg(0.52488);
            global.set_nfreq(64);
          }
          if (str.indexOf("COMM") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_port((int)Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("CALCONS") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_calcons(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("ELBACKLASH") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_elback(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("TLOAD") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_tload(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("TSPILL") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_tspill(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("BEAMWIDTH") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_beamw(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("NOISECAL") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_noisecal(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("CURVATURE") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_curvcorr(Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("TOLERANCE") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_ptoler((int)Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("COUNTPERSTEP") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_countperstep((int)Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("MANCAL") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_mancal((int)Double.valueOf(str2).doubleValue());
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("AXISTILT") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_azaxis_tilt(Double.valueOf(str2).doubleValue());
            str2 = parser.nextToken();
            global.set_tilt_az(Double.valueOf(str2).doubleValue());
            try
            {
              str2 = parser.nextToken();
              global.set_elaxis_tilt(Double.valueOf(str2).doubleValue());
            }
            catch(NoSuchElementException e)
            {
            }
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("SSAT") != -1) // changes for syncSAT

            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            global.set_sounam(str2, global.get_nsou());
            global.set_soutype(4, global.get_nsou());
            str2 = parser.nextToken();
            // ras used to store longitude west
            global.set_ras(Double.valueOf(str2).doubleValue(), global.get_nsou());
            if (global.get_nsou() < 500)
              global.set_nsou(global.get_nsou() + 1);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("AZEL") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            global.set_soutype(1, global.get_nsou());
            str2 = parser.nextToken();
            // ras,dec used to store az and el
            global.set_ras(Double.valueOf(str2).doubleValue()
                           * Math.PI / 180.0, global.get_nsou());
            str2 = parser.nextToken();
            global.set_decs(Double.valueOf(str2).doubleValue()
                            * Math.PI / 180.0, global.get_nsou());
            str2 = parser.nextToken();
            global.set_sounam(str2, global.get_nsou());
            if (global.get_nsou() < 500)
              global.set_nsou(global.get_nsou() + 1);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("GALACTIC") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            global.set_soutype(0, global.get_nsou());
            str2 = parser.nextToken();
            glon = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            glat = Double.valueOf(str2).doubleValue();
            global.set_ras(get_ra(glat, glon), global.get_nsou());
            global.set_decs(get_dec(glat, glon), global.get_nsou());
            str2 = parser.nextToken();
            global.set_sounam(str2, global.get_nsou());
            global.set_epoc(2000.0, global.get_nsou());
            if (global.get_nsou() < 500)
              global.set_nsou(global.get_nsou() + 1);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("Sun") == 0 || str.indexOf("Moon") == 0)
            try
          {                     // supports keyword Sun or Moon starting in Col 1

            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            global.set_sounam(str2, global.get_nsou());
            global.set_ras(0.0, global.get_nsou());
            global.set_decs(0.0, global.get_nsou());
            global.set_epoc(0.0, global.get_nsou());
            global.set_soutype(0, global.get_nsou());
            if (global.get_nsou() < 100)
              global.set_nsou(global.get_nsou() + 1);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          if (str.indexOf("SOU") != -1)
            try
          {
            parser = new StringTokenizer(str);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            rah = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            ram = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            rass = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            decd = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            decm = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            decss = Double.valueOf(str2).doubleValue();
            str2 = parser.nextToken();
            global.set_sounam(str2, global.get_nsou());
//     System.out.println("sou"+str2);
            ep = 1950.0;        /* default */
            try
            {
              str2 = parser.nextToken();
              ep = Double.valueOf(str2).doubleValue();
            }
            catch(NoSuchElementException e)
            {
            }
            if (str.indexOf("-") != -1)
              decsign = -1;
            else
              decsign = 1;
            global.set_ras((rah + ram / 60.0 + rass / 3600.0) * Math.PI / 12.0, global.get_nsou());
            global.set_decs(decsign * (Math.abs(decd) + decm / 60.0 + decss / 3600.0) *
                            Math.PI / 180.0, global.get_nsou());
            global.set_epoc(ep, global.get_nsou());
            global.set_soutype(0, global.get_nsou());
            if (global.get_nsou() < 100)
              global.set_nsou(global.get_nsou() + 1);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog err " + str);
            global.set_fstatus(-99);
          }
        }
      }
      in.close();
    }
    catch(NumberFormatException e)
    {
      System.out.println(e);
    }
    catch(FileNotFoundException e)
    {
      System.out.println(e);
      d.dtext(440.0, ylim + 40.0, gg, Color.red, "catalog file srt.cat not found");
      global.set_fstatus(-99);
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
    global.set_nsoucat(global.get_nsou());
  }
  public double cmdfile(global g, disp d, Graphics gg, time t, outfile out)
  // reads command file
  /* drives the schedule with stop time yyyy:ddd:hh:mm:ss
     or LST:hh:mm:ss  or just : for immediate scheduling
     or :n for scheduling for n secs
     followed by keywords:
     sourcename (any name in catalog)
     azel az_deg el_deg
     radec ra_hh:mm:ss dec_dd:mm:ss [epoch]
     galactic glat_deg glon_deg
     stow
     calibrate
     noisecal
     record (turns on data file if not already on) [filename] [recmode]
     roff (turns off data file)
     freq fcenter_MHz number_of_frequencies [spacing]
     mode n for 25 point b for beamswitch
   */
  {
    double secs = 0.0;
      try
    {
      BufferedReader in =
      new BufferedReader(new FileReader(new File(g.get_cmdfile())));
      double rah,
        ram,
        rass,
        decd,
        decm,
        decss,
        ep,
        secnow,
        glon,
        glat,
        lst;
      int decsign,
        i,
        j,
        line,
        yr,
        day,
        hr,
        min,
        se;
      String str,
        str2,
        str3;
      StringTokenizer parser;
        secs = secnow = (double)t.getTsec(g, d, gg);
        i = 1;
        line = 0;
      while ((str = in.readLine()) != null && i == 1)
      {
        line++;
        if (!str.startsWith("*") && !str.startsWith(" ")
            && str.length() > 2 && line > g.get_cmdfline())
        {
          if (!str.startsWith("L") && !str.startsWith(":"))
            try
          {                     // yyyy:ddd:hh:mm:ss

            str3 = "";
            for (j = 0; j < str.length(); j++)
            {
              if (str.charAt(j) == ':')
                str3 += " ";
              else
                str3 += str.charAt(j);
            }
            parser = new StringTokenizer(str3);
            str2 = parser.nextToken();
            yr = Integer.valueOf(str2).intValue();
            str2 = parser.nextToken();
            day = Integer.valueOf(str2).intValue();
            str2 = parser.nextToken();
            hr = Integer.valueOf(str2).intValue();
            str2 = parser.nextToken();
            min = Integer.valueOf(str2).intValue();
            str2 = parser.nextToken();
            se = Integer.valueOf(str2).intValue();
            secs = t.tosec(yr, day, hr, min, se);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
          }
          if (str.startsWith("L"))
            try
          {                     //    "LST:%2d:%2d:%2d",&hr,&min,&sec

            str3 = "";
            for (j = 0; j < str.length(); j++)
            {
              if (str.charAt(j) == ':')
                str3 += " ";
              else
                str3 += str.charAt(j);
            }
            parser = new StringTokenizer(str3);
            str2 = parser.nextToken();
            str2 = parser.nextToken();
            hr = Integer.valueOf(str2).intValue();
            str2 = parser.nextToken();
            min = Integer.valueOf(str2).intValue();
            str2 = parser.nextToken();
            se = Integer.valueOf(str2).intValue();
            lst = t.getGst() - g.get_lon();
            if (lst > Math.PI * 2.0)
              lst -= Math.PI * 2.0;
            if (lst < 0.0)
              lst += Math.PI * 2.0;
            secs = secnow + hr * 3600.0 + min * 60.0 + se - lst * 86400.0 / (Math.PI * 2.0);
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
          }
          if (str.startsWith(":") && str.charAt(1) != ' ')
            try
          {
            str3 = "";
            for (j = 0; j < str.length(); j++)
            {
              if (str.charAt(j) != ':')
                str3 += str.charAt(j);
            }
            parser = new StringTokenizer(str3);
            str2 = parser.nextToken();
            secs += (double)Integer.valueOf(str2).intValue();
          }
          catch(NoSuchElementException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
          }
          catch(NumberFormatException e)
          {
            d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
          }
          if (secs >= secnow)
          {
// System.out.println("secs "+secs+" secnow "+secnow);
            try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              for (j = 1; j < g.get_nsou(); j++)
              {
                if (str2.indexOf(g.get_sounam(j)) != -1)
                {
                  g.set_sourn(j);
                  g.set_bsw(0);
                  g.set_azoff(0.0);
                  g.set_eloff(0.0);
                  g.set_clr(1);
                  // check for mode
                  parser = new StringTokenizer(str);
                  str2 = parser.nextToken();
                  str2 = parser.nextToken();
                  try
                  {
                    str2 = parser.nextToken();
                    if (str2.indexOf("n") != -1)
                      g.set_scan(1);
                    if (str2.indexOf("b") != -1)
                    {
                      g.set_bsw(1);
                      g.set_sig(-1);
                      g.set_bswcycles(0);
                      g.set_bswav(0.0);
                      g.set_bswsq(0.0);
                      g.set_bswlast(0.0);
                    }
                  }
                  catch(NoSuchElementException e)
                  {
                  }
                }
              }
            }
            catch(NoSuchElementException e)
            {
            }
            if (str.indexOf("azel") != -1)
              try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              g.set_azcmd(Double.valueOf(str2).doubleValue());
              str2 = parser.nextToken();
              g.set_elcmd(Double.valueOf(str2).doubleValue());
              g.set_track(0);
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            catch(NumberFormatException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            if (str.indexOf("mode") != -1)
              try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              if (str2.indexOf("n") != -1)
                g.set_scan(1);
              if (str2.indexOf("b") != -1)
              {
                g.set_clr(1);
                g.set_bsw(1);
                g.set_sig(-1);
                g.set_bswcycles(0);
                g.set_bswav(0.0);
                g.set_bswsq(0.0);
                g.set_bswlast(0.0);
              }
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            if (str.indexOf("offset") != -1)
              try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              g.set_azoff(Double.valueOf(str2).doubleValue());
              str2 = parser.nextToken();
              g.set_eloff(Double.valueOf(str2).doubleValue());
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            catch(NumberFormatException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            if (str.indexOf("radec") != -1)
              try
            {
              str3 = "";
              for (j = str.indexOf("radec"); j < str.length(); j++)
              {
                if (str.charAt(j) == ':')
                  str3 += " ";
                else
                  str3 += str.charAt(j);
              }
              parser = new StringTokenizer(str3);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              rah = Double.valueOf(str2).doubleValue();
              str2 = parser.nextToken();
              ram = Double.valueOf(str2).doubleValue();
              str2 = parser.nextToken();
              rass = Double.valueOf(str2).doubleValue();
              str2 = parser.nextToken();
              decd = Double.valueOf(str2).doubleValue();
              str2 = parser.nextToken();
              decm = Double.valueOf(str2).doubleValue();
              str2 = parser.nextToken();
              decss = Double.valueOf(str2).doubleValue();
              if (g.get_nsou() > g.get_nsoucat())
                g.set_nsou(g.get_nsoucat());
              g.set_sounam("radec", g.get_nsou());
              ep = 1950.0;      /* default */
              try
              {
                str2 = parser.nextToken();
                if (str2.indexOf("n") == -1 && str2.indexOf("b") == -1)
                   ep = Double.valueOf(str2).doubleValue();
              }
              catch(NoSuchElementException e)
              {
              }
              if (str.indexOf("-") != -1)
                decsign = -1;
              else
                decsign = 1;
              g.set_ras((rah + ram / 60.0 + rass / 3600.0) * Math.PI / 12.0, g.get_nsou());
              g.set_decs(decsign * (Math.abs(decd) + decm / 60.0 + decss / 3600.0) *
                         Math.PI / 180.0, g.get_nsou());
              g.set_epoc(ep, g.get_nsou());
              g.set_sourn(g.get_nsou());
              if (g.get_nsou() < 100)
                g.set_nsou(g.get_nsou() + 1);
              g.set_clr(1);
              //  check for mode
              if (str.indexOf("n") > str.indexOf("radec"))
                g.set_scan(1);
              if (str.indexOf("b") > str.indexOf("radec"))
              {
                g.set_bsw(1);
                g.set_sig(-1);
                g.set_bswcycles(0);
                g.set_bswav(0.0);
                g.set_bswsq(0.0);
                g.set_bswlast(0.0);
              }
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            catch(NumberFormatException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            if (str.indexOf("galactic") != -1)
              try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              glon = Double.valueOf(str2).doubleValue();
              str2 = parser.nextToken();
              glat = Double.valueOf(str2).doubleValue();
              if (g.get_nsou() > g.get_nsoucat())
                g.set_nsou(g.get_nsoucat());
              g.set_ras(get_ra(glat, glon), g.get_nsou());
              g.set_decs(get_dec(glat, glon), g.get_nsou());
              g.set_sounam("galactic", g.get_nsou());
              g.set_epoc(2000.0, g.get_nsou());
              g.set_sourn(g.get_nsou());
              if (g.get_nsou() < 100)
                g.set_nsou(g.get_nsou() + 1);
              g.set_clr(1);
              if (str.indexOf("n") > str.indexOf("galactic"))
                g.set_scan(1);
              if (str.indexOf("b") > str.indexOf("galactic"))
              {
                g.set_bsw(1);
                g.set_sig(-1);
                g.set_bswcycles(0);
                g.set_bswav(0.0);
                g.set_bswsq(0.0);
                g.set_bswlast(0.0);
              }
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            catch(NumberFormatException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            if (str.indexOf("stow") != -1)
            {
              g.set_sourn(0);
              g.set_track(0);
              g.set_azcmd(g.get_azlim1());
              g.set_elcmd(g.get_ellim1());
              g.set_stow(1);
            }
            if (str.indexOf("calibrate") != -1 && g.get_mancal() == 0)
            {
              g.set_docal(1);
              g.set_stopproc(0);
            }
            if (str.indexOf("noisecal") != -1)
            {
              g.set_docal(2);
              g.set_stopproc(0);
            }
            if (str.indexOf("record") != -1)
              try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              try
              {
                str2 = parser.nextToken();
                g.set_recfile(str2);
              }
              catch(NoSuchElementException e)
              {
              }
              g.set_recmode(0);
              try
              {
                str2 = parser.nextToken();
                g.set_recmode((int)Double.valueOf(str2).doubleValue());
              }
              catch(NoSuchElementException e)
              {
              }
              if (g.get_fstatus() == 1)
                out.closeoutfile(g, d, gg);
              if (g.get_fstatus() == 0)
                out.openoutfile(g, d, gg);
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            catch(NumberFormatException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            if (str.indexOf("roff") != -1)
              if (g.get_fstatus() == 1)
                out.closeoutfile(g, d, gg);
            if (str.indexOf("freq") != -1)
              try
            {
              parser = new StringTokenizer(str);
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              str2 = parser.nextToken();
              g.set_fcenter(Double.valueOf(str2).doubleValue());
              str2 = parser.nextToken();
              if (g.get_digital() == 0)
              {
                g.set_nfreq(Integer.valueOf(str2).intValue());
                if (g.get_nfreq() > 500)
                  g.set_nfreq(500);
                g.set_freqsep(0.04); // default
                g.set_intg(0.1);

              }
              if (g.get_digital() >= 1)
              {
                g.set_digital(Integer.valueOf(str2).intValue());
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

              }

              try
              {
                str2 = parser.nextToken();
                g.set_freqsep(Double.valueOf(str2).doubleValue());
              }
              catch(NoSuchElementException e)
              {
              }
              g.set_clr(1);
            }
            catch(NoSuchElementException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            catch(NumberFormatException e)
            {
              d.dtext(440.0, ylim + 40.0, gg, Color.red, "cmd err " + str);
            }
            d.ppclear(gg, 8.0, ylim + 60.0, 600.0);
            d.dtext(8.0, ylim + 60.0, gg, Color.green,
                    g.get_cmdfile() + ": line " + line + " " + str);
            if (g.get_fstatus() == 1)
              out.stroutfile(g, "* " + g.get_cmdfile() +
                             ": line " + line + " " + str);
          }
          i = 0;
        }
      }
      g.set_cmdfline(line);
      in.close();
      if (str == null)
      {
        d.ppclear(gg, 8.0, ylim + 60.0, 600.0);
        d.dtext(8.0, ylim + 60.0, gg, Color.green, g.get_cmdfile() + ": end_of_file");
//        g.set_cmdfile("");
        g.set_cmdf(0);
        g.set_cmdfline(0);
        d.set_bc(Color.black, 12);
      }
    }
    catch(NumberFormatException e)
    {
      System.out.println(e);
    }
    catch(FileNotFoundException e)
    {
      d.dtext(400.0, ylim + 60.0, gg, Color.red, g.get_cmdfile() + " file not found");
      g.set_cmdf(0);
      g.set_cmdfline(0);
      d.set_bc(Color.black, 12);
      g.set_cmdfile("");
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
    return secs;
  }
  public double get_ra(double glat, double glon)
  /* galactic to radec  2000 epoch pole at 12h51.4 27.1 */
  {
    double a,
      xg,
      yg,
      zg,
      xr,
      yr,
      zr,
      d0,
      dp,
      r0,
      rp,
      ra;
      d0 = -(28.0 + 56.0 / 60.0) * Math.PI / 180.0;
      r0 = (17.0 + 45.5 / 60.0) * Math.PI / 12.0;
      dp = 27.1 * Math.PI / 180.0;
      rp = (12.0 + 51.4 / 60.0) * Math.PI / 12.0;
      zr = Math.sin(d0);
      xr = Math.cos(r0 - rp) * Math.cos(d0);
      yr = Math.sin(r0 - rp) * Math.cos(d0);
      xg = xr * Math.sin(dp) - zr * Math.cos(dp);
      yg = yr;
      a = Math.atan2(yg, xg);
      xg = Math.cos((glon * Math.PI / 180.0) + a) * Math.cos(glat * Math.PI / 180.0);
      yg = Math.sin((glon * Math.PI / 180.0) + a) * Math.cos(glat * Math.PI / 180.0);
      zg = Math.sin(glat * Math.PI / 180.0);
      xr = xg * Math.sin(dp) + zg * Math.cos(dp);
      yr = yg;
      zr = zg * Math.sin(dp) - xg * Math.cos(dp);
      dec = Math.atan2(zr, Math.sqrt(xr * xr + yr * yr));
      ra = Math.atan2(yr, xr) + rp;
      return ra;
  }
  public double get_dec(double glat, double glon)
  {
    return dec;
  }

}
