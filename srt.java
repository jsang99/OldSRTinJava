import java.awt. *;
public class srt
// main class that starts main thread loop
// this code evolved from a C program and is not modular
// an ongoing task is to provided a more modular version
// allowing for easier addition of modules
// since this code is not modular we recommend that find or
// grep always be used when making changes to check for all
// occurances of globals
{
  public static void main(String args[])
  {
    time t;
    disp d;
    String ydhms;
    long sec,
      secp;
    double gst,
      secstop;
    global g;
    int i;
    outfile out;
    sport s;
    procs proc;
    checkey che = new checkey();
    cat cat = new cat();
    Graphics gg;
    if (args.length < 1)
    {
      System.out.println("srt radiosim azelsim   version 2.1 24 Jul 2002");
      System.out.println("for example: srt 0 to run with receiver + antenna");
      System.out.println("             srt 0 1 to simulate antenna");
      System.out.println("             srt 1  to simulate radio");
      System.out.println("             srt 1 1 to simulate both");
      System.out.println("             srt 1 10 to simulate and speed-up time by x 10");
      System.out.println("             srt 1 -1 to simulate with 1 hour advance");
      System.out.println("             srt 1 0 1 for maintenance");
      System.out.println("radiometer simulation: assumes 200K tsys + Gaussian noise");
      System.out.println("                       for 40kHz BW 0.1s integration per freq");
      System.out.println("                       1K for Moon, 100K for Sun, 2.6K for Cass");
      System.out.println("Catalog Keywords: STATION,AZLIMITS,ELLIMITS,COMM,CALCONS,TOLERANCE");
      System.out.println("                  TLOAD,TSPILL,BEAMWIDTH,MANCAL,AXISTILT,NOISECAL");
      System.out.println("                  SSAT,AZEL,GALACTIC,SOU,Sun,Moon,CURVATURE,ELBACKLASH");
      System.out.println("                  COUNTPERSTEP");
      System.exit(1);
    }
    g = new global();
    g.set_noisecal(0.0);
    g.set_curvcorr(0.0);
    g.set_ptoler(1);
    g.set_countperstep(10000); // default large number for no stepping 
    t = new time();
    out = new outfile(t);
    d = new disp("srt", g, out, che);
    gg = d.getGraphics();
    che.set_graph(gg);
    if (args.length >= 1)
        g.set_radiosim(Integer.valueOf(args[0]).intValue());
    else
        g.set_radiosim(0);
    if (args.length >= 2)
        g.set_azelsim(Integer.valueOf(args[1]).intValue());
    else
        g.set_azelsim(0);
    if (args.length >= 3)
        g.set_mainten(Integer.valueOf(args[2]).intValue());
    else
        g.set_mainten(0);
    for (i = 0; i < 100; i++)
    {
      g.set_xlast(1, i);
      g.set_ylast(1, i);
    }
    for (i = 0; i < 360; i++)
    {
      g.set_gxlast(1, i);
      g.set_gylast(1, i);
    }
    g.set_nfreq(1);
    g.set_calcons(1.0);
    g.set_tload(300.0);
    g.set_tspill(20.0);
    g.set_beamw(5.0);
    g.set_pscale(400.0);
    g.set_comerr(0);
    g.set_comerad(0);
    g.set_fcenter(1420.0);      /* default for continuum */
    g.set_freqa(1420.0);
    g.set_restfreq(1420.406);   /* H-line restfreq */
    if (g.get_radiosim() != 0)
    {
      g.set_fcenter(1420.4);    /* default for H-line */
      g.set_nfreq(40);
    }
    g.set_ppos(0);
    g.set_azoff(0.0);
    g.set_eloff(0.0);
    g.set_pazoff(0.0);
    g.set_peloff(0.0);
    g.set_elback(0.0);
    g.set_drift(0);
    secstop = 0.0;
    secp = 0;
    g.set_tstart(0);
    g.set_azaxis_tilt(0.0);
    g.set_tilt_az(0.0);
    g.set_elaxis_tilt(0.0);
    g.set_tsys(0.0);
    g.set_stopproc(0);
    g.set_click(0);
    g.set_mclick(0);
    g.set_atten(0);
    g.set_calon(0);
    g.set_docal(0);
    g.set_sourn(0);
    g.set_track(0);
    g.set_scan(0);
    g.set_bsw(0);
    g.set_mancal(0);
    g.set_clr(1);
    g.set_port(1);              /* comm1 default */
    g.set_freqsep(0.04);
    g.set_intg(0.1);
    g.set_fstatus(0);
    g.set_cmdf(0);
    g.set_key(0);
    g.set_noisecal(0.0);
    g.set_recform(0);
    g.set_sig(1);
    cat.catfile(g, d, gg);
    d.set_font(gg);
//    System.out.println("finished cat");
    if (g.get_azlim2() > g.get_azlim1() && g.get_azlim2() < 360.0)
      g.set_south(1);           /* normally South */
    else
    {
      g.set_south(0);           /* North */
      if (g.get_azlim2() < 360.0)
        g.set_azlim2(g.get_azlim2() + 360.0);
    }
    s = new sport(g);
    proc = new procs(s, out);
    if (g.get_digital() != 0)
      proc.grayfilt(g);
    if (g.get_mainten() == 0)
    {
      g.set_azcmd(g.get_azlim1());
      g.set_elcmd(g.get_ellim1());
      g.set_azcount(0);
      g.set_elcount(0);
      g.set_stow(1);
    }
    else
    {
      if (g.get_south() == 1)
      {
        g.set_azcmd(180.0);
        g.set_aznow(180.0);
      }
      else
      {
        g.set_azcmd(360.0);
        g.set_aznow(360.0);
      }
      g.set_elcmd(45.0);
      g.set_elnow(45.0);
      g.set_azcount((int)((g.get_aznow() - g.get_azlim1()) * 27.0 * 52.0 / 120.0));
      g.set_elcount((int)((g.get_elnow() - g.get_ellim1()) * 27.0 * 52.0 / 120.0));
      g.set_stow(0);
    }
    while (true)
    {
      if (g.get_click() != 0)
      {
        if (g.get_cmdstr().length() > 1)
          g.set_click(0);
        che.checky(g, d, gg, out);
        if (g.get_click() == 1)
          g.set_click(0);
      }
      if (g.get_docal() == 1)
        proc.calibrat(g, d, gg, out);
      if (g.get_docal() == 2)
        proc.noisecal(g, d, gg, out);
      proc.spectra(g, d, gg);
      sec = t.getTsec(g, d, gg);
      if (g.get_cmdf() == 1)
      {
        if (sec > secstop && g.get_slew() == 0 && g.get_scan() == 0)
          secstop = cat.cmdfile(g, d, gg, t, out);
      }
      else
        secstop = 0.0;
      if (((g.get_recform() & 4) == 4) && sec/86400 > secp/86400
         && g.get_fstatus() == 1)
      {
       out.closeoutfile(g, d, gg);
       if (g.get_fstatus() == 0)
          out.openoutfile(g, d, gg);
       secp = sec;
      }
    }
  }
}
