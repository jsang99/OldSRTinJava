import java.awt. *;
import java.awt.event. *;
import java.text. *;
public class velspec extends Frame
// class for Frame and graphics
{
  int xoff = 1,
    yoff = 50;
  int w,
    h;
  int fsize = 10;
  Font f;
  FontMetrics fm;
  Graphics gf,gsave;
  double sx,
    sy;
  global glb;
  Image vsave;
  public velspec(String title, global g)
  {
    super(title);
    int i;
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      enableEvents(AWTEvent.FOCUS_EVENT_MASK);
      enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
      enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      w = Toolkit.getDefaultToolkit().getScreenSize().width;
      h = Toolkit.getDefaultToolkit().getScreenSize().height;
    if (w > 480)
        w = 480;
    if (h > 360)
        h = 360;
      sx = (double)w / 480.0;
      sy = (double)h / 360.0;
      setBounds(0, 0, w, h);
      glb = g;
      setBackground(Color.white);
      setVisible(true);
      vsave = createImage(w,h);
      gsave = vsave.getGraphics();
      gf = getGraphics();
      f = new Font("Serif", Font.PLAIN, fsize);
      gsave.setFont(f);        
      plotspec(glb);
      gf.drawImage(vsave,0,0,null);
      glb.set_xmark(1);
  }
  public void focus(global g)
  {
    requestFocus();
    gf.drawImage(vsave,0,0,this);
  }
  public void paint(Graphics gg)
  {  // needs to be overridden to avoid blanking when internally called
     if (glb.get_xmark() == 1)
         gf.drawImage(vsave,0,0,this);
  }
  public void actionPerformed(ActionEvent evt)
  {
    requestFocus();
    gf.drawImage(vsave,0,0,this);
  }
  protected void processMouseEvent(MouseEvent evt)
  {
    requestFocus();
    gf.drawImage(vsave,0,0,this);
  }
  public void lpaint(Color color,
                     double x1, double y1, double x2, double y2)
  {
    gsave.setColor(color);
    gsave.setPaintMode();
    gsave.drawLine((int)(x1 * sx) + xoff, (int)(y1 * sy) + yoff,
                (int)(x2 * sx) + xoff, (int)(y2 * sy) + yoff);
  }
  public void text(double xx, double yy, Color color, String text)
  // write text to screen
  {
    gsave.setColor(color);
    gsave.setPaintMode();
    gsave.drawString(text, (int)(xx * sx) + xoff, (int)(yy * sy) + yoff);
  }
  protected void processWindowEvent(WindowEvent e)
  {
// System.out.println(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      dispose();
      gsave.dispose();
      glb.set_xmark(0);
    }
    if (glb.get_xmark() == 1)
        gf.drawImage(vsave,0,0,this);
    super.processWindowEvent(e);
  }
  protected void processComponentEvent(ComponentEvent e)
  {
    if (e.getID() == ComponentEvent.COMPONENT_RESIZED)
    {
      w = getBounds().width;
      h = getBounds().height;
      if (w > 960)
        w = 960;
      if (h > 720)
        h = 720;
      setBounds(0, 0, w, h);
//   System.out.println("resized "+w+" "+h+" fsize "+fsize +"+sx "+sx+sy);
      sx = (double)w / 480.0;
      sy = (double)h / 360.0;
      if (sx < sy)
        f = new Font("Serif", Font.PLAIN, (int)(fsize * sx));
      else
        f = new Font("Serif", Font.PLAIN, (int)(fsize * sy));
      gf.dispose();
      gsave.dispose();
      vsave = createImage(w,h);
      gsave = vsave.getGraphics();
      gf = getGraphics();
      gsave.setFont(f);            // only set if graphics pointer id known
      plotspec(glb);
      gf.drawImage(vsave,0,0,this);
    }
  }
  public String dc(double a, int m, int n)
  // make printable floating point equivalent C %m.nf
  // m chars wide n chars after the decimal point
  {
    int i,
      j;
    String str2,
      str3;
    NumberFormat nf = NumberFormat.getInstance();
      nf.setMaximumFractionDigits(n);
      nf.setMinimumFractionDigits(n);
    if (a >= 0.0)
    {
      if (n > 0)
        nf.setMinimumIntegerDigits(m - n - 1);
      else
        nf.setMinimumIntegerDigits(m);
    }
    else
    {
      if (n > 0)
        nf.setMinimumIntegerDigits(m - n - 2);
      else
        nf.setMinimumIntegerDigits(m - 1);
    }
    nf.setGroupingUsed(false);
    str2 = nf.format(a);
    str3 = "";
    j = 0;
    if (str2.charAt(0) != '-')
    {
      for (i = 0; i < str2.length() - 1; i++)
        if (str2.charAt(i) == '0' && j == 0 && str2.charAt(i + 1) != '.')
          str3 += " ";
        else
        {
          str3 += str2.charAt(i);
          j = 1;
        }
    }
    else
    {
      for (i = 1; i < str2.length() - 1; i++)
      {
        if (str2.charAt(i) == '0' && j == 0 && str2.charAt(i + 1) != '.')
          str3 += " ";
        else
        {
          if (j == 0)
            str3 += "-";
          str3 += str2.charAt(i);
          j = 1;
        }
      }
      if (j == 0)
        str3 += "-";
    }
    str3 += str2.charAt(str2.length() - 1);
    return str3;
  }
  void plotspec(global g)
  // plot the spectrum
  {
    String txt;
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
      n1,
      n2,
      n3,
      xp;
    double xx,
      yy,
      dmax,
      ddmax,
      dmin,
      slope,
      dd,
      ddd,
      totpp,
      scale,
      sigma,
      freq,
      fstart,
      fstop,
      vstart,
      vstop,
      xoffset,
      pp[];
      pp = new double[500];
      gsave.clearRect(0, 0, w, h);
      dmax = ddmax = -1.0e99;
      dmin = 1.0e99;
      dd = sigma = 0.0;
    if (g.get_digital() == 0 || g.get_digital() == 5)
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
      jmax = 0;
    for (j = j1; j < j2; j++)
    {
      if (g.get_bsw() == 0)
          dd = g.get_avspec(j) / (g.get_av() + 1e-6);
      else
          dd = g.get_avspec(j) / (g.get_av() + 1e-6)
            - g.get_avspecc(j) / (g.get_avc() + 1e-6);
      pp[j] = dd;
      dd = g.get_avspec(j) / (g.get_av() + 1e-6);
      if (dd > ddmax)
         ddmax = dd;
    }
    slope = pp[j2 - 1] - pp[j1];
    for (j = j1; j < j2; j++)
    {
      if (np > 1)
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
    xoffset = 80.0;
    if (dmax > dmin)
      scale = 1.2 * (dmax - dmin);
    else
      scale = 1.0;
    if (g.get_freqsep() > 0.0)
      xx = Math.floor(g.get_fcenter() / g.get_freqsep()) * g.get_freqsep();
    else
      xx = g.get_fcenter();

    if (g.get_nfreq() > 1)
    {                           // center of bandpass 0.04 above L.O.

      txt = "av.spectrum integ." +
        dc((g.get_av() + g.get_avc()) * g.get_intg() / 60.0, 5, 2)
        + " min";
      sigma = ddmax / Math.sqrt(g.get_av() * g.get_intg()
                                     * g.get_freqsep() * 1e06);
      if (g.get_bsw() != 0)
         sigma *= Math.sqrt(2.0);
      text(xoffset + 50.0, 270.0, Color.black, txt);
      text(xoffset + 250.0, 270.0, Color.black, g.get_ptime());
      if (g.get_sourn() > 0)
      {
        txt = g.get_sounam(g.get_sourn());
        if (txt.lastIndexOf("Sun") == -1 && txt.lastIndexOf("Moon") == -1)                                    
            txt += " Galactic l = " +
             dc(g.get_glon(),4,0) + " b = " +
             dc(g.get_glat(),3,0);
        text(xoffset + 20.0, 15.0, Color.black, txt);
      }
      for (y = 0; y < 2; y++)
      {
        lpaint(Color.black, xoffset, (double)(y * 219),
               xoffset + 319, (double)(y * 219));
        lpaint(Color.black, xoffset + y * 319, (double)(0),
               xoffset + y * 319, (double)(219));
      }

      yp = 0;
      xp = 319;
      for (j = 1; j < 320; j++)
      {
        x = 320 - j;
        xx = j / 320.0;
        k = (int)(xx * (double)np);
        if (k >= np)
          k = np - 1;
        if (scale > 0.0)
          totpp = (pp[k] - dmin) / scale;
        else
          totpp = 0;
        y = (int)(160.0 - totpp * 160.0);
        if (y < 0)
          y = 0;
        if (y > 160)
          y = 160;
        if (j == 1)
          yp = y;
        if (y != yp)
        {
          lpaint(Color.black, xp + xoffset, (double)yp,
                 x + xoffset, (double)yp);
          xp = x;
          if (y > yp)

            lpaint(Color.black, x + xoffset, (double)yp,
                   x + xoffset, (double)y);

          if (yp > y)

            lpaint(Color.black, x + xoffset, (double)y,
                   x + xoffset, (double)yp);

        }
        yp = y;
      }
      lpaint(Color.black, xp + xoffset, (double)yp,
             1 + xoffset, (double)yp);
      if (g.get_digital() == 0)
        dd = 0.04;
      else
        dd = 0.0;
      fstart = g.get_fcenter()
        + (double)(1 - g.get_nfreq() / 2) * g.get_freqsep() + dd;
      fstop = g.get_fcenter()
        + (double)(np - 1 - g.get_nfreq() / 2) * g.get_freqsep() + dd;
      vstart = -g.get_vlsr() - (fstop - g.get_restfreq()) * 299790.0 / g.get_restfreq();
      vstop = -g.get_vlsr() - (fstart - g.get_restfreq()) * 299790.0 / g.get_restfreq();
      ddd = fstop - fstart;
      n3 = (int)(ddd) + 1;
      ddd = 10.0 / n3;
      j1 = (int)(fstart * ddd);
      j2 = (int)(fstop * ddd);
      for (j = j1 + 1; j <= j2; j++)
      {
        if (g.get_digital() == 0)
          dd = ((double)(j) / ddd - g.get_fcenter()
                + (double)(g.get_nfreq() / 2) * g.get_freqsep() - 0.04)
            * 320.0 / ((double)(np) * g.get_freqsep());
        else
          dd = ((double)(j) / ddd - g.get_fcenter()
                + (double)(g.get_nfreq() / 2) * g.get_freqsep())
            * 320.0 / ((double)(np) * g.get_freqsep());
        lpaint(Color.black, 320.0 - dd + xoffset, 210.0,
               320.0 - dd + xoffset, 219.0);
        text(300.0 - dd + xoffset, 235.0, Color.black, dc((double)(j) / ddd, 6, 1));
        text(125.0 + xoffset, 250.0, Color.black, "frequency (MHz)");

      }
      ddd = 10.0 * n3;
      j1 = (int)(vstart / ddd);
      j2 = (int)(vstop / ddd);
      for (j = j1 + 1; j <= j2; j++)
      {
        freq = g.get_restfreq() -
          ((double)(j) * ddd + g.get_vlsr()) * g.get_restfreq() / 299790.0;
        if (g.get_digital() == 0)
          dd = (freq - g.get_fcenter()
                + (double)(g.get_nfreq() / 2) * g.get_freqsep() - 0.04)
            * 320.0 / ((double)(np) * g.get_freqsep());
        else
          dd = (freq - g.get_fcenter()
                + (double)(g.get_nfreq() / 2) * g.get_freqsep())
            * 320.0 / ((double)(np) * g.get_freqsep());
        lpaint(Color.black, 320.0 - dd + xoffset, 165.0,
               320.0 - dd + xoffset, 175.0);
        text(310.0 - dd + xoffset, 190.0, Color.black, dc((double)(j) * ddd, 4, 0));
        text(135.0 + xoffset, 205.0, Color.black, "VLSR (km/s)");

      }
      dd = Math.log(scale) / Math.log(2.0) - 0.6;
      if (dd < 0.0)
        j = (int)(dd - 0.5);
      else
        j = (int)(dd + 0.5);
      dd = 0.5 * Math.pow(2.0, (double)j);
      n1 = 5;
      if (dd >= 1.0)
        n2 = 1;
      else
        n2 = 2;
      j1 = 0;
      j2 = (int)(scale / dd);
      for (j = 0; j <= j2; j++)
      {
        if (g.get_bsw() != 0)
           ddd = (pp[1] - dmin) / scale;
        else
           ddd = 0.0;
        y = (int)(160.0 - ((double)j * dd / scale) * 160.0 - ddd * 160.0);
        if (y > 0)
        {
           lpaint(Color.black, xoffset, (double)y, xoffset + 10.0, (double)y);
           text(xoffset - 30.0, (double)y, Color.black, dc((double)(j) * dd, n1, n2) +
                "K");
        }
      }
      yy = (sigma / scale) * 160.0;
      if (yy > 0.0)
      {
        lpaint(Color.black, xoffset + 310.0, 5.0, xoffset + 310.0, yy + 5.0);
        lpaint(Color.black, xoffset + 305.0, 5.0, xoffset + 315.0, 5.0);
        lpaint(Color.black, xoffset + 305.0,
               yy + 5.0, xoffset + 315.0, yy + 5.0);
        text(xoffset + 285.0, yy * 0.5 + 4.5, Color.black, "one");
        text(xoffset + 280.0, yy * 0.5 + 12.5, Color.black, "sigma");
      }
    }
  }
}
