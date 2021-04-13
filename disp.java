import java.awt. *;
import java.awt.event. *;
import java.text. *;
public class disp extends Frame implements ActionListener
// class for Frame, mouse control and graphics
{
  int xoff = 1,
    yoff = 50;
  int gff = 0;
  int w,
    h;
  int fsize = 12;               // for Windows
  int ffsize;
  //  int fsize = 10; // for Linux

  Font f;
  FontMetrics fm;
  Graphics gf;
  double sx,
    sy;
  double fontfac;
  global glb;
  outfile out;
  velspec v;
  TextField tf = new TextField();
  Button b[] = new Button[20];
  checkey che;
  MouseEventHandler mhand = new MouseEventHandler();
  String but[] =
  {
    "clear", "atten", "Help", "Stow", "track", "Azel", "npoint", "bmsw",
    "freq", "offset", "Drift", "record", "Rcmdfl", "Cal", "Vane"};
  public disp(String title, global g, outfile o, checkey c)
  {
    super(title);
    int i;
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      enableEvents(AWTEvent.KEY_EVENT_MASK);
      enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
      enableEvents(AWTEvent.FOCUS_EVENT_MASK);
      w = Toolkit.getDefaultToolkit().getScreenSize().width;
      h = Toolkit.getDefaultToolkit().getScreenSize().height;
    if (w > 960)
        w = 960;
    if (h > 720)
        h = 720;
      sx = (double)w / 800.0;
      sy = ((double)h - 80.0) / 530.0;
      setBounds(0, 0, w, h);
      glb = g;
      out = o;
      che = c;
      add(tf, BorderLayout.SOUTH);
      tf.addActionListener(this);
    Panel p = new Panel(new GridLayout(1, 0));
    for (i = 0; i < 15; i++)
    {
      b[i] = new Button(but[i]);
      p.add(b[i]);
      b[i].addActionListener(this);
      b[i].addMouseListener(mhand);
    }
    add(p, BorderLayout.NORTH);
      mhand.mou(glb, this, che);
      setBackground(Color.white);
      tf.setBackground(Color.white);
      setVisible(true);
  }
  public void set_font(Graphics gg)
  {
    if (sx < sy)
      ffsize = (int)(fsize * sx + 0.5);
    else
      ffsize = (int)(fsize * sy + 0.5);
    f = new Font("Serif", Font.PLAIN, ffsize);
    gg.setFont(f);
    if (ffsize > 0)
      fontfac = gg.getFontMetrics().stringWidth("1234567890") / ffsize;
    else
      fontfac = 5.0;
//    System.out.println(fontfac);
    if (fontfac > 5.5 && fsize == 12)
    {
    fsize = 10; // reduce size of font
    if (sx < sy)
      ffsize = (int)(fsize * sx + 0.5);
    else
      ffsize = (int)(fsize * sy + 0.5);
    f = new Font("Serif", Font.PLAIN, ffsize);
    gg.setFont(f);
    }
    gf = gg;
    gff = 1;
  }
  public void set_bc(Color color, int n)
  {
    b[n].setForeground(color);
  }
  public void dpaint(Graphics g, Color color, double xx, double yy)
  {
    g.setColor(color);
    g.setPaintMode();
    g.drawLine((int)(xx * sx) + xoff, (int)(yy * sy) + yoff,
               (int)(xx * sx) + xoff, (int)(yy * sy) + yoff);
  }
  public void lpaint(Graphics g, Color color,
                     double x1, double y1, double x2, double y2)
  {
    g.setColor(color);
    g.setPaintMode();
    g.drawLine((int)(x1 * sx) + xoff, (int)(y1 * sy) + yoff,
               (int)(x2 * sx) + xoff, (int)(y2 * sy) + yoff);
  }
  public void spaint(Graphics g, Color color, double xx, double yy, int siz)
  {
    g.setColor(color);
    g.setPaintMode();
    g.fillOval((int)(xx * sx) + xoff - siz / 2,
               (int)(yy * sy) + yoff - siz / 2, siz, siz);
  }
  public void rpaint(Graphics g, Color color, double xx, double yy)
  {
    g.setColor(color);
    g.setPaintMode();
    g.fillRect((int)(xx * sx) + xoff, (int)(yy * sy) + yoff, 1 + (int)sx, 1 + (int)sx);
  }
  public void pclear(Graphics g, double xx, double yy, double wid, double hi)
  {
    g.clearRect((int)(xx * sx) + xoff, (int)(yy * sy) + yoff,
                (int)(wid * sx), (int)(hi * sy));
  }
  public void ppclear(Graphics g, double xx, double yy, double wid)
  {
    g.clearRect((int)(xx * sx) + xoff, (int)(yy * sy) + yoff
                - g.getFontMetrics().getHeight()
                + g.getFontMetrics().getDescent(), (int)(wid * sx),
                g.getFontMetrics().getHeight());
  }
  public void dtext(double xx, double yy, Graphics g, Color color, String text)
  // print text to screen - with erase to remove prior graphics
  // within text region
  {
    g.setColor(color);
    g.setPaintMode();
    g.clearRect((int)(xx * sx) + xoff, (int)(yy * sy) + yoff + 1
                - g.getFontMetrics().getMaxAscent(),
                g.getFontMetrics().stringWidth(text) +
                g.getFontMetrics().stringWidth("   "),
                g.getFontMetrics().getMaxAscent() +
                g.getFontMetrics().getMaxDescent() - 1);
//  System.out.println(g.getFontMetrics().stringWidth(text)+" "
    //     +g.getFontMetrics().getHeight());
    g.drawString(text, (int)(xx * sx) + xoff, (int)(yy * sy) + yoff);
  }
  public void stext(double xx, double yy, Graphics g, Color color, String text)
  // write text to screen
  {
    g.setColor(color);
    g.setPaintMode();
    g.drawString(text, (int)(xx * sx) + xoff, (int)(yy * sy) + yoff);
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
  public String dcs(String s, int m)
  // fix string to m spaces long by adding spaces
  {
    int i;
    String str2 = s;
    for (i = 0; i < m - str2.length(); i++)
        str2 += " ";
      return str2;
  }
  protected void processWindowEvent(WindowEvent e)
  {
//   System.out.println("close");
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      if (glb.get_fstatus() == 1)
        out.closeoutfile(glb, this, gf);
      dispose();
      System.exit(0);
    }
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
//   System.out.println("resized "+w+" "+h+" fsize "+fsize +"+sx "+sx+" "+sy+
//    " " +gf.getFontMetrics().stringWidth("1234567890"));
      sx = (double)w / 800.0;
      sy = ((double)h - 80.0) / 530.0;
      if (gff == 1)
      {
        set_font(gf);             // only set if graphics pointer id known
        gf.clearRect(0, 0, w, h);
      }
    }
  }
  protected void processMouseEvent(MouseEvent evt)
  // process mouse and check for nearest source on display
  {
    double dx,
      dy,
      r,
      min;
    int i,
      j,
      mx,
      my;
    if (evt.getClickCount() >= 1)
    {
      glb.set_mclick(1);
      mx = evt.getX();
      my = evt.getY();
      min = 20.0;
      j = -1;
      for (i = 1; i < glb.get_nsou(); i++)
      {
        dx = (double)(mx - xoff - glb.get_xlast(i) * sx);
        dy = (double)(my - yoff - glb.get_ylast(i) * sy);
        r = dx * dx + dy * dy;
        if (Math.sqrt(r) < min)
        {
          min = Math.sqrt(r);
          j = i;
        }
      }
      if (j > 0)
      {
        if (j != glb.get_sourn() && glb.get_fstatus() == 1)
          out.stroutfile(glb, "* " + glb.get_sounam(j));
        glb.set_sourn(j);
        glb.set_bsw(0);
        glb.set_azoff(0.0);
        glb.set_eloff(0.0);
        glb.set_clr(1);
      }
      if (my - yoff < 100 * sy && mx > 160 * sx && mx < 320 * sx
         && evt.getID() == MouseEvent.MOUSE_PRESSED) 
      {
        if (glb.get_map() == 1)
            glb.set_map(2);
      }
      if (my - yoff < 100 * sy && my - yoff > 80 * sy && mx < 160 * sx
         && evt.getID() == MouseEvent.MOUSE_PRESSED) 
      {
        if (glb.get_pscale() >= 40.0)
            glb.set_pscale(glb.get_pscale() * 0.1);
        else
            glb.set_pscale(400.0);
      }
      if (my - yoff < 100 * sy && mx > 320 * sx && mx < 480 * sx
         && evt.getID() == MouseEvent.MOUSE_PRESSED) 
      {                         // check for click on spectrum
        if (glb.get_xmark() == 0)
          v = new velspec("accumulated spectrum", glb);
        else
        {
            v.focus(glb);
        }
        //   System.out.println("mouse "+mx+" "+my+"nfr"+glb.get_nfreq());
      }
    }
  }
  public void actionPerformed(ActionEvent evt)
  {
    String str2;
    char cmd;
//  System.out.println("act "+tf.getText()+"evt= "+evt);
    //  System.out.println("id "+evt.getActionCommand());
      str2 = tf.getText();
      cmd = 0;
//  System.out.println("char="+cmd+" text "+str2);
      if (gff == 1)    // make sure graphics pointer available
          che.check(1, cmd, glb, this, str2);
      tf.setText("");
    if (!evt.getActionCommand().equals("Help"))
        requestFocus();         // needed so prevent loss of key events

  }
}
class MouseEventHandler extends MouseAdapter
{
  global glb;
  disp dd;
  checkey che;
  public void mou(global g, disp d, checkey c)
  {
    glb = g;
    dd = d;
    che = c;
  }
  public void mouseEntered(MouseEvent evt)
  {
//    System.out.println("mouseent "+ evt.getSource());
    che.check(2, ((Button) evt.getSource()).getLabel().charAt(0),
              glb, dd, "");
  }
  public void mouseExited(MouseEvent evt)
  {
    che.check(3, '0', glb, dd, "");
  }
}
