import java.awt. *;
public class procs
// class of procedures
{
  private plots p = new plots();
  private outfile o;
  private map m = new map();
  private geom geom = new geom();
  private time t = new time();
  private sport s;
  private double ylim = 415.0;
  public procs(sport sp, outfile out)
  {
    o = out;
    s = sp;
  }
  void spectra(global g, disp d, Graphics gg)
  // take a spectrum
  {
    int i,
      k,
      j,
      n,
      nk,
      nk2;
    double freqf,
      secc,
      avp,
      pwr,
      pp,
      lst,
      slope;
    if (g.get_map() == 2)
    {
        m.map(g, d, gg, o);
        g.set_map(1);
        g.set_azoff(0.0);
        g.set_eloff(0.0);
    }
    if (g.get_track() != 0)
    {
      if (g.get_scan() >= 26)
      {
        g.set_scan(0);
        g.set_azoff(0);
        g.set_eloff(0);
        m.map(g, d, gg, o);
        g.set_map(1);
      }
      if (g.get_scan() != 0)
      {
        g.set_azoff(g.get_beamw() * 0.5 *
                    (double)((g.get_scan() - 1) % 5 - 2) /
                    Math.cos(g.get_elcmd() * Math.PI / 180.0));
        g.set_eloff(g.get_beamw() * 0.5 * (double)((g.get_scan() - 1) / 5 - 2));
        g.set_pwr(0.0, g.get_scan());
        g.set_scan(g.get_scan() + 1);
      }
      if (g.get_bsw() != 0)
      {
        g.set_sig(-g.get_sig());
        if (g.get_sig() == -1)
        {
          g.set_azoff(g.get_beamw() * g.get_bsw() /
                      Math.cos(g.get_elcmd() * Math.PI / 180.0));
          g.set_bsw(-g.get_bsw());
        }
        else
          g.set_azoff(0.0);
      }
      else
        g.set_sig(1);
    }
    p.plotbox(g, d, gg);
    geom.setsounam(g, d, gg, t);
    g.set_elcor(geom.antiltel(g.get_azcmd(), g));
    g.set_azcor(geom.antiltaz(g.get_azcmd(), g.get_elcmd(), g));
    if(g.get_calon() == 0 && g.get_docal() == 0)
       s.azel(g.get_azcmd() + g.get_azcor(),
           g.get_elcmd() + g.get_elcor(), g, d, gg, o); // command antenna

    if (g.get_track() == 0 && g.get_stow() != -1)
    {
      return;
    }
    if (g.get_clr() != 0)
    {
      for (i = 0; i < g.get_nfreq(); i++)
      {
        g.set_avspec(0.0, i);
        g.set_avspecc(0.0, i);
      }
      g.set_av(0.0);
      g.set_avc(0.0);
      g.set_bswav(0.0);
      g.set_bswsq(0.0);
      g.set_bswlast(0.0);
      g.set_bswcycles(0);
      if (g.get_clr() == -1)
        d.pclear(gg, 0.0, 0.0, 640.0, 440.0);
      g.set_clr(0);
    }
    if (g.get_atten() == 1)
      d.set_bc(Color.red, 1);
    else
      d.set_bc(Color.black, 1);
    if (g.get_track() == 1 && g.get_stow() == 0)
    {
      d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status: tracking  ");
      d.set_bc(Color.green, 4);
    }
    if (g.get_track() == -1)
    {
      d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status: stopped   ");
      d.set_bc(Color.red, 4);
    }
    if (g.get_track() == 0)
      d.set_bc(Color.black, 4);
    if (g.get_scan() != 0)
    {
      d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status: scanning");
      d.set_bc(Color.green, 6);
    }
    else
      d.set_bc(Color.black, 6);
    if (g.get_bsw() != 0)
    {
      d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status: on/offs   ");
      d.set_bc(Color.green, 7);
    }
    else
      d.set_bc(Color.black, 7);
    if (g.get_comerr() > 0 || g.get_comerad() > 0)
      d.dtext(440.0, ylim + 40.0, gg, Color.blue, "ERRORS: " +
              "gnd " + g.get_comerr() + " radio " + g.get_comerad());
    avp = 0.0;
    if (g.get_digital() == 0 || g.get_digital() == 5)
    {
      for (i = 0; i < g.get_nfreq(); i++)
      {
        if (g.get_mclick() == 1 || g.get_click() != 0)
        {
          g.set_mclick(0);
          return;
        }
        if (g.get_digital() == 0)
        {
          freqf = g.get_fcenter() + (double)(i - g.get_nfreq() / 2) * g.get_freqsep();
          g.set_spec(s.radio(freqf, g, d, gg, o), i); // get power from radio

        }
        else
        {
          freqf = g.get_fcenter() + (double)(i - g.get_nfreq() / 2) * g.get_freqsep() + 0.8;
          g.set_spec(s.radiodg(freqf, g, d, gg, o), i); // get power from radio

        }
        if (i == 0)
          g.set_freq0(g.get_freqa());
      }
      for (i = 0; i < g.get_nfreq(); i++)
      {
        pwr = g.get_spec(i);
        if (pwr > 0.0)
        {
          avp += pwr;
          if (g.get_sig() == 1)
            g.set_avspec(g.get_avspec(i) + pwr, i);
          else
            g.set_avspecc(g.get_avspecc(i) + pwr, i);
          if (g.get_scan() != 0)
            g.set_pwr(g.get_pwr(g.get_scan() - 1) + pwr, g.get_scan() - 1);
        }
      }
    }
    else
    {                           // digital receiver

      if (g.get_digital() == 4)
        nk = 3;
      else
        nk = 1;
      for (k = 0; k < nk; k++)
      {
        if (g.get_mclick() == 1 || g.get_click() != 0)
        {
          g.set_mclick(0);
          return;
        }
        nk2 = nk / 2;
        freqf = g.get_fcenter() + (k - nk2) * 0.360 + 0.8; // 0.8 MHz I.F.

        s.radiodg(freqf, g, d, gg, o); // get power from radio

        if (k == nk2)
          g.set_fcenter(g.get_freqa()); // correct to actual setting

        if (k == 0)
        {
          if (g.get_digital() <= 3)
            g.set_freq0(g.get_fcenter() - 32.0 * g.get_freqsep());
          else
            g.set_freq0(g.get_fcenter() - 78.0 * g.get_freqsep());
        }
        for (i = 0; i < 64; i++)
        {
          pwr = g.get_specd(i);
          g.set_spec(pwr, i + k * 64);
        }
      }
      if (g.get_digital() == 4)
      {
        pwr = 0.0;
        for (k = 0; k < 3; k++)
        {
          i = 54 + k;
          j = 64 + 8 + k;
          pwr += g.get_spec(i) - g.get_spec(j);
          i = 54 + 64 + k;
          j = 128 + 8 + k;
          pwr += g.get_spec(i) - g.get_spec(j);
// System.out.println("k "+k+" "+g.get_spec(i)+" "+g.get_spec(j));
        }
        slope = pwr / 276.0;    // 23x2x6

        for (k = 0; k < 3; k++)
        {
          for (i = 0; i < 64; i++)
          {
            j = i + k * 64;
            pwr = g.get_spec(j) - (i - 32) * slope;
            g.set_spec(pwr, j);
          }
        }
      }
      for (k = 0; k < nk; k++)
      {
        for (i = 0; i < 64; i++)
        {
          j = 1;
          n = 46 * k + i;
          if (k == 0)
          {
            if (i > 55)
              j = 0;
          }
          if (k == 1)
          {
            if (i < 10 || i > 55)
              j = 0;
          }
          if (k == 2)
          {
            if (i < 10)
              j = 0;
          }
          if (j == 1)
          {
            pwr = g.get_spec(i + k * 64);
            g.set_spec(pwr, n);
            if (pwr > 0.0)
            {
              if (n >= 10 && n < g.get_nfreq() - 10)
              {
                avp += pwr;
                if (g.get_scan() != 0)
                  g.set_pwr(g.get_pwr(g.get_scan() - 1) + pwr, g.get_scan() - 1);
              }
              if (g.get_sig() == 1)
                g.set_avspec(g.get_avspec(n) + pwr, n);
              else
                g.set_avspecc(g.get_avspecc(n) + pwr, n);
            }
          }
        }
      }
    }
    if (g.get_digital() > 0)
      avp = avp / ((double)g.get_nfreq() - 20);
    else
      avp = avp / (double)g.get_nfreq();
    if (g.get_bswlast() > 0.0)
    {
      if (g.get_sig() == 1)
        pp = avp - g.get_bswlast();
      else
        pp = g.get_bswlast() - avp;
      g.set_bswav(g.get_bswav() + pp);
      g.set_bswsq(g.get_bswsq() + pp * pp);
      g.set_bswcycles(g.get_bswcycles() + 1);
    }
    g.set_bswlast(avp);
    if (g.get_sig() == 1)
      g.set_av(g.get_av() + 1);
    else
      g.set_avc(g.get_avc() + 1);
    if (g.get_clr() != 0)
    {
      for (i = 0; i < g.get_nfreq(); i++)
      {
        g.set_avspec(0.0, i);
        g.set_avspecc(0.0, i);
      }
      g.set_av(0.0);
      g.set_avc(0.0);
      g.set_clr(0);
    }
    p.plotspec(0, g, d, gg, o);
    p.plotspec(1, g, d, gg, o);
    secc = (double)t.getTsec(g, d, gg);
    lst = (t.getGst() - g.get_lon()) * 12.0 / Math.PI;
    if (lst < 0.0)
      lst += 24.0;
    if (lst > 24.0)
      lst -= 24.0;
    d.dtext(670.0, 167.0, gg, Color.black, "LST " + d.dc(lst, 4, 1) + " hrs");
    d.dtext(710.0, 136.0, gg, Color.black, "UTdate " + t.getMonthday() + " ");
    if (g.get_tsys() > 0.0)
      d.dtext(665.0, 328.0, gg, Color.black, "tsys: "
              + d.dc(g.get_tsys(), 5, 0) + " K             ");
    else
      d.dtext(665.0, 328.0, gg, Color.black, "tsys: not yet calibrated ");
    if (g.get_scan() == 0 && g.get_stow() == 0 && g.get_track() == 0)
      d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status:                ");
    if (g.get_track() == 0)
      d.set_bc(Color.black, 4);
    p.plotp(secc, g, d, gg);
    if (g.get_fstatus() == 1 && g.get_recmode() != 4)
      o.writeoutfile(g, d, gg);
  }
  void calibrat(global g, disp d, Graphics gg, outfile o)
  // calibration procedure
  {
    int i,
      istart,
      istop;
    double pwr0,
      pwr1,
      trecvr;
      g.set_atten(0);           /* turn-off atten for calibration */
      d.set_bc(Color.green, 14);
      pwr0 = pwr1 = 0.0;
    if (g.get_mancal() == 0)
        s.cal(1, g, d, gg, o);
    else
    {
      d.dtext(400.0, ylim + 60.0, gg, Color.red, "place load on feed");
      g.set_stopproc(1);
      while (g.get_stopproc() == 1)
      {
        if (g.get_click() == 1)
        {
          g.set_stopproc(0);
          g.set_click(0);
        }
      }
      d.dtext(400.0, ylim + 60.0, gg, Color.white, "place load on feed");
    }
    g.set_calon(1);
    spectra(g, d, gg);
    istart = 0;
    istop = g.get_nfreq();
    if (g.get_digital() > 0)
    {
      istart = 10;
      istop = g.get_nfreq() - 10;
    }
    for (i = istart; i < istop; i++)
      pwr1 += g.get_spec(i);
    pwr1 = pwr1 / (double)(istop - istart);
    if (g.get_mancal() == 0)
      s.cal(0, g, d, gg, o);
    else
    {
      d.dtext(400.0, ylim + 60.0, gg, Color.red, "remove load");
      g.set_stopproc(1);
      while (g.get_stopproc() == 1)
      {
        if (g.get_click() == 1)
        {
          g.set_stopproc(0);
          g.set_click(0);
        }
      }
      d.dtext(400.0, ylim + 60.0, gg, Color.white, "remove load");
    }
    g.set_calon(0);
    spectra(g, d, gg);
    for (i = istart; i < istop; i++)
      pwr0 += g.get_spec(i);
    pwr0 = pwr0 / (double)(istop - istart);
    if (pwr1 > pwr0 && pwr0 > 0.0)
    {
      trecvr = (g.get_tload() - (pwr1 / pwr0) * g.get_tspill()) / (pwr1 / pwr0 - 1.0);
      g.set_tsys(trecvr + g.get_tspill());
      g.set_calcons((trecvr + g.get_tspill()) * g.get_calcons() / pwr0);
      d.dtext(665.0, 328.0, gg, Color.black, "tsys: "
              + d.dc(g.get_tsys(), 5, 0) + " K             ");
      d.dtext(665.0, 344.0, gg, Color.black, "calcons: "
              + d.dc(g.get_calcons(), 5, 2));
      d.dtext(665.0, 360.0, gg, Color.black, "trec: "
              + d.dc(trecvr, 5, 0) + " K");
      if (g.get_fstatus() == 1)
        o.stroutfile(g,
                     "* tsys " + d.dc(g.get_tsys(), 5, 0) + " calcons" + d.dc(g.get_calcons(), 5, 2) +
                     " trecvr" + d.dc(trecvr, 5, 0) + " tload" + d.dc(g.get_tload(), 5, 0) +
                     " tspill" + d.dc(g.get_tspill(), 5, 0));
      g.set_docal(0);
      d.set_bc(Color.black, 14);
    }
    else
    {
      d.dtext(670.0, 296.0, gg, Color.black, "tsys: error");
      g.set_docal(0);
      d.set_bc(Color.black, 14);
    }
  }
  void noisecal(global g, disp d, Graphics gg, outfile o)
  // calibration procedure
  {
    int i,
      istart,
      istop;
    double pwr0,
      pwr1,
      trecvr;
      d.set_bc(Color.green, 13);
      pwr0 = pwr1 = 0.0;
      g.set_atten(0);
      s.cal(3, g, d, gg, o);
      g.set_calon(2);
      spectra(g, d, gg);
      istart = 0;
      istop = g.get_nfreq();
    if (g.get_digital() > 0)
    {
      istart = 10;
      istop = g.get_nfreq() - 10;
    }
    for (i = istart; i < istop; i++)
      pwr1 += g.get_spec(i);
    pwr1 = pwr1 / (double)(istop - istart);
    s.cal(2, g, d, gg, o);
    g.set_calon(0);
    g.set_atten(0);             /* turn-off atten for calibration */
    spectra(g, d, gg);
    for (i = istart; i < istop; i++)
      pwr0 += g.get_spec(i);
    pwr0 = pwr0 / (double)(istop - istart);
    if (pwr1 > pwr0 && pwr0 > 0.0 && g.get_noisecal() > 0.0)
    {
      trecvr = ((g.get_noisecal()) / (pwr1 / pwr0 - 1.0)) - g.get_tspill();
      g.set_tsys(trecvr + g.get_tspill());
      g.set_calcons((trecvr + g.get_tspill()) * g.get_calcons() / pwr0);
      d.dtext(665.0, 328.0, gg, Color.black, "tsys: "
              + d.dc(g.get_tsys(), 5, 0) + " K             ");
      d.dtext(665.0, 344.0, gg, Color.black, "calcons: "
              + d.dc(g.get_calcons(), 5, 2));
      d.dtext(665.0, 360.0, gg, Color.black, "trec: "
              + d.dc(trecvr, 5, 0) + " K");
      if (g.get_fstatus() == 1)
        o.stroutfile(g,
                     "* tsys " + d.dc(g.get_tsys(), 5, 0) + " calcons" + d.dc(g.get_calcons(), 5, 2) +
                     " trecvr" + d.dc(trecvr, 5, 0) + " tload" + d.dc(g.get_noisecal(), 5, 0) +
                     " tspill" + d.dc(g.get_tspill(), 5, 0));
      g.set_docal(0);
      d.set_bc(Color.black, 13);
    }
    else
    {
      d.dtext(670.0, 296.0, gg, Color.black, "tsys: error");
      g.set_docal(0);
      d.set_bc(Color.black, 13);
    }
  }

  void grayfilt(global g)
  {
    double cf[] =
    {
      1.000000, 1.006274, 1.022177, 1.040125, 1.051102, 1.048860, 1.033074, 1.009606,
      0.987706, 0.975767, 0.977749, 0.991560, 1.009823, 1.022974, 1.023796, 1.011319,
      0.991736, 0.975578, 0.972605, 0.986673, 1.012158, 1.032996, 1.025913, 0.968784,
      0.851774, 0.684969, 0.496453, 0.320612, 0.183547, 0.094424, 0.046729, 0.026470,
      0.021300
    };
    int i;
    for (i = 0; i <= 32; i++)
    {
      if (i < 32)
        g.set_graycorr(cf[i], i + 32);
      if (i < 33)
        g.set_graycorr(cf[i], 32 - i);
    }
  }
}
