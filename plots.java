import java.awt. *;
public class plots
// class for plotting data
{
  double xlim = 640.0;
  double ylim = 415.0;
  double ylim0 = 200.0;
  void plotbox(global g, disp d, Graphics gg)
  {
    int x,
      y,
      yy;
    double x1,
      y1,
      azz1,
      azz2;
    String str4;
    for (x = 1; x < 36; x++)
    {
      x1 = (double)(x * xlim / 36.0);
      if (x % 2 == 0)
      {
        if (g.get_south() == 1)
          d.stext(x1 - 8, ylim + 12.0, gg, Color.black,
                  d.dc(x * 10.0, 3, 0));
        else
        {
          y = x + 18;
          if (y >= 36)
            y = x - 18;
          d.stext(x1 - 8, ylim + 12.0, gg, Color.black,
                  d.dc(y * 10.0, 3, 0));
        }
      }
      if (x == 17)
          d.stext(x1 - 8, ylim + 22.0, gg, Color.black, "azimuth (deg)");
    }
    for (y = 0; y < 2; y++)
      d.lpaint(gg, Color.black, 0.0, (double)(y * ylim),
               xlim, (double)(y * ylim));
    for (x = 0; x < 2; x++)
      d.lpaint(gg, Color.black, (double)(x * xlim), 0.0,
               (double)(x * xlim), ylim);
    for (x = 1; x < 36; x++)
    {
      x1 = (double)(x * xlim / 36.0);
      d.lpaint(gg, Color.black, x1, ylim, x1, ylim - 9.0);
    }
    for (y = 0; y <= 9; y++)
    {
      y1 = (double)(ylim - y * (ylim - ylim0) / 9.0);
      d.lpaint(gg, Color.black, 0.0, y1, 9.0, y1);
      d.lpaint(gg, Color.black, xlim, y1, xlim - 9.0, y1);
      d.stext(xlim + 1.0, y1 + 4.0, gg, Color.black, d.dc(y * 10.0, 3, 0));
    }
    for (y = 0; y < 9; y++)
    {
      y1 = (double)(ylim - 0.75 * (ylim - ylim0)) + y * 10.0;
      str4 = "E";
      if (y == 1)
        str4 = "l";
      if (y == 2)
        str4 = "e";
      if (y == 3)
        str4 = "v";
      if (y == 4)
        str4 = "a";
      if (y == 5)
        str4 = "t";
      if (y == 6)
        str4 = "i";
      if (y == 7)
        str4 = "o";
      if (y == 8)
        str4 = "n";
      d.stext(20.0, y1, gg, Color.black, str4);
    }
    if (g.get_south() == 1)
    {
      d.stext(xlim * 0.5 - 15.0, ylim - 11.0, gg, Color.black, "south");
      azz1 = g.get_azlim1();
      azz2 = g.get_azlim2();
    }
    else
    {
      d.stext(xlim * 0.5 - 15.0, ylim - 11.0, gg, Color.black, "north");
      azz1 = g.get_azlim1() - 180.0;
      azz2 = g.get_azlim2() - 180.0;
    }
    y = (int)(ylim - g.get_ellim1() * (ylim - ylim0) / 90.0);
    for (x = (int)(azz1 * xlim / 360.0);
         x <= (int)(azz2 * xlim / 360.0); x++)
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)x % 2),
              (double)x, (double)y);
    y = (int)(ylim - (180.0 - g.get_ellim2()) * (ylim - ylim0) / 90.0);
    for (x = 40; x <= (int)((azz2 - 180.0) * xlim / 360.0); x++)
    {
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)x % 2),
              (double)x, (double)y);
    }
    for (x = (int)((azz1 + 180.0) * xlim / 360.0); x <= (int)xlim; x++)
    {
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)x % 2),
              (double)x, (double)y);
    }
    x = (int)(azz1 * xlim / 360.0);
    y = (int)(ylim - g.get_ellim1() * (ylim - ylim0) / 90.0);
    for (yy = (int)ylim0; yy < y; yy++)
    {
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)yy % 2),
              (double)x, (double)yy);
    }
    x = (int)((azz1 + 180.0) * xlim / 360.0);
    y = (int)(ylim - (180.0 - g.get_ellim2()) * (ylim - ylim0) / 90.0);
    for (yy = (int)ylim0; yy < y; yy++)
    {
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)yy % 2),
              (double)x, (double)yy);
    }
    x = (int)(azz2 * xlim / 360.0);
    y = (int)(ylim - g.get_ellim1() * (ylim - ylim0) / 90.0);
    for (yy = (int)ylim0; yy < y; yy++)
    {
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)yy % 2),
              (double)x, (double)yy);
    }
    x = (int)((azz2 - 180.0) * xlim / 360.0);
    y = (int)(ylim - (180.0 - g.get_ellim2()) * (ylim - ylim0) / 90.0);
    for (yy = (int)ylim0; yy < y; yy++)
    {
      d.dpaint(gg, Color.getHSBColor((float)0.0, (float)0.0, (float)yy % 2),
              (double)x, (double)yy);
    }
    d.stext(10.0, (double)y + 4.0, gg, Color.black, "limits");
    d.lpaint(gg, Color.black, 660.0, 0.0, 660.0, ylim + 88.0);
    d.lpaint(gg, Color.black, 660.0, ylim + 88.0, 800.0, ylim + 88.0);
    d.lpaint(gg, Color.black, 660.0, 0.0, 800.0, 0.0);
    d.lpaint(gg, Color.black, 0.0, ylim + 25.0, 640.0, ylim + 25.0);
    d.lpaint(gg, Color.black, 320.0, ylim + 25.0, 320.0, ylim + 46.0);
    d.lpaint(gg, Color.black, 640.0, ylim + 25.0, 640.0, ylim + 46.0);
    d.lpaint(gg, Color.black, 0.0, ylim + 46.0, 640.0, ylim + 46.0);
    d.stext(665.0, 10.0, gg, Color.black, "Antenna coordinates:");
    d.lpaint(gg, Color.black, 660.0, 124.0, 800.0, 124.0);
    d.stext(665.0, 136.0, gg, Color.black, "Time:");
    d.lpaint(gg, Color.black, 660.0, 172.0, 800.0, 172.0);
    d.stext(665.0, 184.0, gg, Color.black, "Source:");
    d.stext(665.0, 248.0, gg, Color.black, "Center frequency:");
    d.dtext(670.0, 264.0, gg, Color.black, d.dc(g.get_fcenter(), 8, 3) + " MHz");
    d.dtext(665.0, 280.0, gg, Color.black, "spacing: " + d.dc(g.get_freqsep() * 1000.0, 8, 2) + " kHz");
    d.dtext(665.0, 296.0, gg, Color.black, "number bins: " + g.get_nfreq());
    d.dtext(665.0, 312.0, gg, Color.black, "integ. period: "
            + d.dc(g.get_intg(),4,2)+ " sec");

  }

  void plotspec(int mode, global g, disp d, Graphics gg, outfile o)
  // plot the spectrum
  {
    String txt,
      txt2;
    int x,
      y,
      yp,
      j,
      jmax,
      kk,
      k,
      np,
      j1,
      j2,
      i,
      offset,
      xp;
    double xx,
      dmax,
      dmin,
      slope,
      dd,
      totpp,
      scale,
      spav,
      sigma,
      fmax,
      vmax,
      intg,
      pp[];
      pp = new double[500];
      dmax = -1.0e99;
      dmin = 1.0e99;
      dd = spav = sigma = 0.0;
    if (mode == 0 || g.get_digital() == 0 || g.get_digital() == 5)
    {
      np = g.get_nfreq();
      j1 = 0;
      j2 = np;
    }
    else
    {
      np = g.get_nfreq();
      j1 = 9;
      j2 = g.get_nfreq() - 9;
    }
    if (g.get_digital() == 0)
      intg = g.get_intg() * g.get_nfreq();
    else
      intg = g.get_intg();
    jmax = 0;
    for (j = j1; j < j2; j++)
    {
      if (mode == 0)
        dd = g.get_spec(j);
      if (mode != 0)
      {
        if (g.get_bsw() == 0)
          dd = g.get_avspec(j) / (g.get_av() + 1e-6);
        else
          dd = g.get_avspec(j) / (g.get_av() + 1e-6)
            - g.get_avspecc(j) / (g.get_avc() + 1e-6);
      }
      if (g.get_bswcycles() > 1)
      {
        spav = g.get_bswav() / (double)g.get_bswcycles();
        sigma = (g.get_bswsq()
                 - spav * g.get_bswav()) / ((double)g.get_bswcycles() - 1.0);
        if (sigma > 0.0)
          sigma = Math.sqrt(sigma / (double)g.get_bswcycles());
      }
      pp[j] = dd;
    }
    slope = pp[j2 - 1] - pp[j1];
    for (j = j1; j < j2; j++)
    {
      if (np > 1 && mode != 0)
        pp[j] -= slope * (double)(j - j1) / ((double)(j2 - j1) - 1.0);
      dd = pp[j];
      if (dd > dmax)
      {
        dmax = dd;
        jmax = j;
      }
      if (dd < dmin)
        dmin = dd;
    }
    if (mode == 0)
      offset = (int)(xlim * 3.0 / 4.0);
    else
      offset = (int)(xlim / 2.0);
    if (g.get_bsw() != 0 && mode != 0)
    {
      if (g.get_bswcycles() > 1)
        txt = "av " + d.dc(spav, 7, 2) + " +/-" + d.dc(sigma, 7, 2)
          + " scale" + d.dc(dmax - dmin, 5, 1);
      else
        txt = "av " + d.dc(spav, 7, 2)
          + " scale" + d.dc(dmax - dmin, 5, 1);
    }
    else
    {
      if (mode == 0)
        txt = " max-min " + d.dc(dmax - dmin, 5, 1) + " K   freq ->";
      else
        txt = " max-min " + d.dc(dmax - dmin, 5, 1) + " K slope " +
          d.dc(slope, 5, 1) + " K ";
    }
    if (dmax > dmin)
      scale = 1.2 * (dmax - dmin);
    else
      scale = 1.0;
    d.pclear(gg, (double)offset, 0.0, 160.0, 99.0);
    if (g.get_freqsep() > 0.0)
      xx = Math.floor(g.get_fcenter() / g.get_freqsep()) * g.get_freqsep();
    else
      xx = g.get_fcenter();
    if (mode == 0)
    {
      txt2 = d.dc(xx, 7, 2) + " span"
        + d.dc(g.get_nfreq() * g.get_freqsep(), 7, 2) + " MHz";
      d.dtext(480.0, 10.0, gg, Color.black, txt2);
      d.dtext((double)offset, 95.0, gg, Color.black, txt);
    }
    else if (g.get_nfreq() > 1)
    {                           // center of bandpass 0.04 above L.O.

      txt2 = "peak";
      if (g.get_digital() == 0)
        fmax = g.get_fcenter() +
          (double)(jmax - g.get_nfreq() / 2) * g.get_freqsep() + 0.04;
      else
        fmax = g.get_fcenter() +
          (double)(jmax - g.get_nfreq() / 2) * g.get_freqsep();
      vmax = -g.get_vlsr() - (fmax - g.get_restfreq()) * 299790.0 / g.get_restfreq();
      d.dtext(665.0, 458.0, gg, Color.black, "V" + txt2 + "   " + d.dc(vmax, 6, 1) + " km/s");
      d.dtext(665.0, 474.0, gg, Color.black, "F" + txt2 + "  " + d.dc(fmax, 8, 3) + " MHz");
      if (g.get_fstatus() == 1 && g.get_recmode() == 3)
        o.stroutfile(g, "* V   " + d.dc(vmax, 6, 1) + " km/s" +
                     " F  " + d.dc(fmax, 8, 3) + " MHz " +
             d.dc(pp[jmax] - pp[0] * 0.5 - pp[np - 1] * 0.5, 7, 2) + " K " +
                   d.dc((g.get_av() + g.get_avc()) * intg, 6, 1) + " secs");
    }
    if (mode != 0 && g.get_bsw() != 0)
    {
      d.dtext((double)(offset + 1), 95.0, gg, Color.red, txt);
      txt = "av.specinteg" +
        d.dc((g.get_av() + g.get_avc()) * intg / 60.0, 5, 2)
        + " min= " + g.get_bswcycles() + " cyc";

      d.dtext(321.0, 10.0, gg, Color.red, txt);
    }
    if (mode != 0 && g.get_bsw() == 0)
    {
      d.dtext((double)(offset + 1), 95.0, gg, Color.red, txt);
      txt = "av.spectrum integ." +
        d.dc((g.get_av() + g.get_avc()) * intg / 60.0, 5, 2)
        + " min";
      d.dtext(321.0, 10.0, gg, Color.red, txt);
    }
    for (y = 0; y < 2; y++)
    {
      d.lpaint(gg, Color.black, (double)offset, (double)(y * 99),
               (double)(offset + 159), (double)(y * 99));
    }

    d.lpaint(gg, Color.black, (double)(offset), 0.0, (double)(offset),
             98.0);
    d.lpaint(gg, Color.black, xlim, 0.0, xlim, 98.0);

    d.lpaint(gg, Color.black, 218.0, 0.0, 218.0, 98.0);
    yp = 0;
    xp = 1;
    for (j = 1; j < 160; j++)
    {
      x = j;
      xx = j / 160.0;
      k = (int)(xx * (double)np);
      if (k >= np)
        k = np - 1;
      if (scale > 0.0)
        totpp = (pp[k] - dmin) / scale;
      else
        totpp = 0;
      y = (int)(80.0 - totpp * 80.0);
      if (y < 0)
        y = 0;
      if (y > 80)
        y = 80;
      if (j == 1)
        yp = y;
      if (y != yp)
      {
        if (mode == 0)
          d.lpaint(gg, Color.black, (double)(xp + offset), (double)yp,
                   (double)(x + offset), (double)yp);
        else
        {
          d.lpaint(gg, Color.red, (double)(xp + offset), (double)yp,
                   (double)(x + offset), (double)yp);
          if (k == jmax + 1 && np > 1)
          {
            d.lpaint(gg, Color.blue, (double)(xp + offset), (double)yp,
                     (double)(x + offset), (double)yp);
            d.spaint(gg, Color.blue, (double)(xp * 0.5 + x * 0.5 + offset), (double)yp - 3.0, 2);
          }
        }
        xp = x;
        if (y > yp)
        {

          if (mode == 0)
            d.lpaint(gg, Color.black, (double)(x + offset), (double)yp,
                     (double)(x + offset), (double)y);
          else
            d.lpaint(gg, Color.red, (double)(x + offset), (double)yp,
                     (double)(x + offset), (double)y);
        }

        if (yp > y)
        {

          if (mode == 0)
            d.lpaint(gg, Color.black, (double)(x + offset), (double)y,
                     (double)(x + offset), (double)yp);
          else
            d.lpaint(gg, Color.red, (double)(x + offset), (double)y,
                     (double)(x + offset), (double)yp);
        }

      }
      yp = y;
    }
    if (mode == 0)
      d.lpaint(gg, Color.black, (double)(xp + offset), (double)yp,
               (double)(160 + offset), (double)yp);
    else
    {
      d.lpaint(gg, Color.red, (double)(xp + offset), (double)yp,
               (double)(160 + offset), (double)yp);
      if (np == jmax + 1 && np > 1)
      {
        d.lpaint(gg, Color.blue, (double)(xp + offset), (double)yp,
                 (double)(160 + offset), (double)yp);
        d.spaint(gg, Color.blue, (double)(xp * 0.5 + 80.0 + offset), (double)yp - 3.0, 2);
      }
    }
  }

  void plotp(double sec, global g, disp d, Graphics gg)
  // plots power vs time
  {
    int i,
      j,
      x,
      y;
    double a,
      totp,
      pscale;
      totp = 0.0;
      d.lpaint(gg, Color.black, 218.0, 99.0, xlim, 99.0);
      d.lpaint(gg, Color.black, 0.0, ylim0, xlim, ylim0);
    for (i = 0; i < g.get_nfreq(); i++)
        totp += g.get_spec(i);
      totp = totp / (double)g.get_nfreq();
    if (g.get_ppos() > 639)
        g.set_ppos(0);
      x = g.get_ppos();
      g.set_ppos(g.get_ppos() + 2);
    if (g.get_atten() != 0)
        pscale = g.get_pscale() * 10.0;
    else
        pscale = g.get_pscale();
      j = (int)(totp / pscale);
      d.dtext(5.0, 100.0, gg, Color.black, "power vs time scale "
          + d.dc(j * pscale, 4, 0) + " to " + d.dc((j + 1) * pscale, 4, 0) +
              "K " + d.dc(totp, 4, 0) + "K");
      a = Math.IEEEremainder(totp, pscale) / pscale;
    if (a < 0.0)
        a++;
      y = (int)(ylim0 - a * 99.0);
      d.pclear(gg, (double)x + 1.0, 100.0, 10.0, 100.0);
    if (j % 4 == 0)
        d.spaint(gg, Color.black, (double)x, (double)y, 3);
    if (j % 4 == 1)
        d.spaint(gg, Color.blue, (double)x, (double)y, 3);
    if (j % 4 == 2)
        d.spaint(gg, Color.green, (double)x, (double)y, 3);
    if (j % 4 == 3)
        d.spaint(gg, Color.red, (double)x, (double)y, 3);
    for (j = 0; j < 4; j++)
    {
      if (j == 0)
        d.spaint(gg, Color.black, 210.0, 98.0, 3);
      if (j == 1)
        d.spaint(gg, Color.blue, 210.0, 95.0, 3);
      if (j == 2)
        d.spaint(gg, Color.green, 210.0, 92.0, 3);
      if (j == 3)
        d.spaint(gg, Color.red, 210.0, 89.0, 3);
    }
  }
}
