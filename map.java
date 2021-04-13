import java.awt. *;
public class map
// contours the 25 point map
{
  void map(global g, disp d, Graphics gg, outfile out)
  {
    int i,
      j,
      nnfreq,
      x,
      y,
      mx,
      my,
      py[],
      px;
    double sum,
      az,
      el,
      a,
      b,
      mmax,
      max,
      min,
      dx,
      dy,
      azwid,
      elwid;
      py = new int[100];
      max = mmax = -1e29;
      min = 1e29;
      px = 0;
    for (i = 0; i < 25; i++)
    {
      if (g.get_pwr(i + 1) > max)
        max = g.get_pwr(i + 1);
      if (g.get_pwr(i + 1) < min)
        min = g.get_pwr(i + 1);
    }
    mx = my = 0;
    for (x = -50; x < 50; x++)
    {
      for (y = -50; y < 49; y++)
      {
        sum = 0.0;
        for (i = 0; i < 25; i++)
        {
          az = (double)((i % 5) - 2);
          el = (double)(i / 5 - 2);
          dx = x * 0.05 - az;
          dy = y * 0.05 - el;
          a = dx * Math.PI;
          b = dy * Math.PI;
          if (a == 0.0)
            a = 1.0;
          else
            a = Math.sin(a) / a;
          if (b == 0.0)
            b = 1.0;
          else
            b = Math.sin(b) / b;
          sum += a * b * (g.get_pwr(i + 1) - min);
        }
        a = sum;
        if (a > mmax)
        {
          mmax = a;
          mx = x;
          my = y;
        }
        i = (int)(sum * 7.0 / (max - min));
        j = 0;
        if (i < 0)
          i = 0;
        if (i > 7)
          i = 7;
        if (i <= 2)
          j = i;
        if (i == 3)
          j = 4;
        if (i == 4)
          j = 9;
        if (i == 5)
          j = 10;
        if (i == 6)
          j = 12;
        if (i == 7)
          j = 15;
        if ((i != py[y + 50] || i != px) && x > -50 && y > -50)
          j = 15;
        d.rpaint(gg,
          Color.getHSBColor((float)j / (float)15.0, (float)1.0, (float)1.0),
                 (double)x + 50.0 + 219.0, 49.0 - (double)y);
        py[y + 50] = i;
        px = i;
      }
    }
    azwid = elwid = 0.0;
    for (x = -50; x < 50; x++)
    {
      for (y = -50; y < 49; y++)
      {
        sum = 0.0;
        for (i = 0; i < 25; i++)
        {
          az = (double)((i % 5) - 2);
          el = (double)(i / 5 - 2);
          dx = x * 0.05 - az;
          dy = y * 0.05 - el;
          a = dx * Math.PI;
          b = dy * Math.PI;
          if (a == 0.0)
            a = 1.0;
          else
            a = Math.sin(a) / a;
          if (b == 0.0)
            b = 1.0;
          else
            b = Math.sin(b) / b;
          sum += a * b * (g.get_pwr(i + 1) - min);
        }
        a = sum;
        if (a >= mmax * 0.5 && x == mx)
          azwid++;
        if (a >= mmax * 0.5 && y == my)
          elwid++;
      }
    }
    if (g.get_digital() > 0)
      nnfreq = g.get_nfreq() - 20;
    else
      nnfreq = g.get_nfreq();
    g.set_azoff(
                 g.get_beamw() * 0.5 * 0.05 * mx / Math.cos(g.get_elcmd() * Math.PI / 180.0));
    g.set_eloff(g.get_beamw() * 0.5 * 0.05 * my);
    d.dtext(665.0, 378.0, gg, Color.black, "Scan results:");
    d.dtext(670.0, 394.0, gg, Color.black,
            "max " + d.dc((mmax / (double)nnfreq), 5, 0) +
      " at " + d.dc(g.get_azoff(), 5, 1) + " " + d.dc(g.get_eloff(), 5, 1));
    d.dtext(670.0, 410.0, gg, Color.black,
            "azwid " + d.dc(azwid * 0.025 * g.get_beamw(), 5, 1) +
            " elwid" + d.dc(elwid * 0.025 * g.get_beamw(), 5, 1) + " deg");
    if (g.get_fstatus() == 1)
      out.stroutfile(g,
             "* NPOINT max " + d.dc(((mmax + min) / (double)nnfreq), 5, 0) +
      " at " + d.dc(g.get_azoff(), 5, 1) + " " + d.dc(g.get_eloff(), 5, 1));
  }
}
