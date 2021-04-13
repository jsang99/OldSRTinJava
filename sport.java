import javax.comm. *;
import java.awt. *;
import java.util. *;
import java.text. *;
import java.lang. *;
import java.io. *;

public class sport
// serial port class - talks to the motor control and radio
// STAMP microcontrollers
// must talk to one stamp at a time and always get response
// before talking again
{
  private geom geom = new geom();
  private time t = new time();
  static CommPortIdentifier portId;
  static Enumeration portList;
  InputStream inputStream;
  static OutputStream outputStream;
  SerialPort serialPort;
  private double ylim = 415.0;
  private double ylim0 = 200.0;
  private int azatstow = 0;
  private int elatstow = 0;

  public sport(global g)
  {
    int found = 0;
    if (g.get_azelsim() == 0 || g.get_radiosim() == 0)
    {
      portList = CommPortIdentifier.getPortIdentifiers();
//      System.out.println("port" + portList);
      while (portList.hasMoreElements())
      {
        portId = (CommPortIdentifier) portList.nextElement();
//        System.out.println(portId);
        //        System.out.println(portId.getPortType());
        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
        {
//          System.out.println(portId.getName());
          if (portId.getName().equals("COM" + g.get_port()) ||
              portId.getName().equals("/dev/ttyS" + g.get_port()))
          {
            try
            {
              serialPort = (SerialPort) portId.open("SRT", 2000);
              found = 1;
            }
            catch(PortInUseException e)
            {
              System.out.println(portId.getName());
              System.out.println(e);
              System.out.println("exit window and try again");
              System.exit(0);
            }
            try
            {
              outputStream = serialPort.getOutputStream();
            }
            catch(IOException e)
            {
              System.out.println(e);
            }
            try
            {
              inputStream = serialPort.getInputStream();
            }
            catch(IOException e)
            {
              System.out.println(e);
            }
            try
            {
              serialPort.setSerialPortParams(2400,
                                             SerialPort.DATABITS_8,
                                             SerialPort.STOPBITS_1,
                                             SerialPort.PARITY_NONE);
            }
            catch(UnsupportedCommOperationException e)
            {
              System.out.println(e);
            }
          }
        }
      }
      if (found == 0)
      {
        portList = CommPortIdentifier.getPortIdentifiers();
        System.out.println("port" + portList);
        System.out.println("port not found - ports that were found:");
        while (portList.hasMoreElements())
        {
          portId = (CommPortIdentifier) portList.nextElement();
          System.out.println(portId.getName());
        }
        System.exit(0);
      }
    }
  }

  void azel(double az, double el, global g, disp d, Graphics gg, outfile o)
  // command antenna movement
  {
    int i,
      k,
      kk,
      n,
      mm,
      ax,
      axis,
      count,
      ccount,
      rcount,
      flip;
    double scale,
      azz,
      ell,
      ra,
      dec,
      glat,
      glon,
      vel,
      sec,
      x,
      y;
    int j;
    char m[] = new char[80];    /* avoid byte array compiler bug */
    char recv[] = new char[80];
    String str,
      str2;
    StringTokenizer parser;
      mm = count = 0;
      d.dtext(670.0, 24.0, gg, Color.black, "cmd "
              + d.dc(az, 5, 1) + " " + d.dc(el, 5, 1) + " deg");
    if (g.get_azelsim() == 0)
    {
      str = "antenna drive status:";
      if (g.get_comerr() > 0)
        str += " comerr= " + g.get_comerr();
    }
    else
        str = "antenna drive simulated";
    d.dtext(16.0, 32.0, gg, Color.black, str);
    if (g.get_south() == 0)
    {
      az = az + 360.0;          /* put az in range 180 to 540 */
      if (az > 540.0)
        az -= 360.0;
      if (az < 180.0)
        az += 360.0;
    }

    if ((az < g.get_azlim1() && az > g.get_azlim2() - 180.0) ||
        (az < g.get_azlim1() + 180.0 && az > g.get_azlim2()) ||
        (az > g.get_azlim1() && az < g.get_azlim2() && el < g.get_ellim1() ||
         ((az > g.get_azlim1() + 180.0 || az < g.get_azlim2() - 180.0)
          && el < 180.0 - g.get_ellim2()))
        || el > 90.0)
    {
      d.dtext(16.0, 48.0, gg, Color.red, "cmd out of limits");
      d.set_bc(Color.black, 4);
      if (g.get_fstatus() == 1 && g.get_track() != 0)
        o.stroutfile(g, "* ERROR cmd out of limits");
      g.set_track(0);
      return;
    }
    flip = 0;
    if (az > g.get_azlim2())
    {
      az -= 180.0;
      el = 180.0 - el;
      flip = 1;
    }
    if (az < g.get_azlim1() && flip == 0)
    {
      az += 180.0;
      el = 180.0 - el;
      flip = 1;
    }
    azz = az - g.get_azlim1();
    ell = el - g.get_ellim1();
    scale = 52.0 * 27.0 / 120.0;
/* mm=1=clockwize incr.az mm=0=ccw mm=2= down when pointed south */
    g.set_slew(0);
    if (ell * scale > g.get_elcount())
      axis = 1;                 // move in elevation first

    else
      axis = 0;
    for (ax = 0; ax < 2; ax++)
    {
      if (axis == 0)
      {
        if (azz * scale > g.get_azcount())
        {
          mm = 1;
          count = (int)(azz * scale - g.get_azcount());
        }
        if (azz * scale <= g.get_azcount())
        {
          mm = 0;
          count = (int)(g.get_azcount() - azz * scale);
        }
      }
      else
      {
        if (ell * scale > g.get_elcount())
        {
          mm = 3;
          count = (int)(ell * scale - g.get_elcount());
        }
        if (ell * scale <= g.get_elcount())
        {
          mm = 2;
          count = (int)(g.get_elcount() - ell * scale);
        }
      }
      ccount = count;
      if (g.get_stow() == 1 && g.get_azcmd() == g.get_azlim1()
          && g.get_elcmd() == g.get_ellim1()) // drive to stow

      {
        count = 5000;
        if (axis == 0)
        {
          mm = 0;
          if (azatstow == 1)
            count = 0;
        }
        if (axis == 1)
        {
          mm = 2;
// complete azimuth motion to stow before completely drop in elevation
          if (elatstow == 1 || (ccount <=  2.0 * g.get_countperstep()
              && azatstow == 0))
            count = 0;
        }
        flip = 0;
      }
      if (count > g.get_countperstep() && ccount > g.get_countperstep())
        count = g.get_countperstep();
      if (count >= g.get_ptoler() && g.get_track() != -1)
      {
        if (count > g.get_ptoler())
        {
          g.set_slew(1);
          d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status: slewing   ");
          d.set_bc(Color.black, 4);
          d.set_bc(Color.black, 3);
        }
        x = g.get_xlast(500);
        y = g.get_ylast(500);
        if (x != g.get_xlast(0) || y != g.get_ylast(0))
        {
          d.lpaint(gg, Color.white, (double)(x - 10), (double)y,
                   (double)(x + 10), (double)y);
          d.lpaint(gg, Color.white, (double)x, (double)(y - 10),
                   (double)x, (double)(y + 10));
        }
        x = (int)(g.get_azcmd() * 640.0 / 360.0);
        if (g.get_south() == 0)
          x -= 320;
        if (x < 0)
          x += 640;
        if (x > 640)
          x -= 640;
        y = (int)(ylim - g.get_elcmd() * (ylim - ylim0) / 90);
        g.set_xlast(x, 500);
        g.set_ylast(y, 500);
        d.lpaint(gg, Color.yellow, (double)(x - 10), (double)y,
                 (double)(x + 10), (double)y);
        d.lpaint(gg, Color.yellow, (double)(x), (double)(y - 10),
                 (double)(x), (double)(y + 10));
        str = "  move " + mm + " " + count + "\n"; /* need space at start and end */
        n = 0;
        if (g.get_azelsim() != 0)
        {
          if (count < 5000)
            str2 = "M " + count + "\n";
          else
            str2 = "T " + count + "\n";
          str2.getChars(0, str2.length(), recv, 0);
          n = str2.length();
        }
        d.dtext(16.0, 64.0, gg, Color.black,
                "trans " + str.substring(0, str.length() - 1) + "     ");
        d.ppclear(gg, 16.0, 80.0, 180.0);
        j = 0;
        kk = -1;
        if (g.get_azelsim() == 0)
        {
          try
          {
            serialPort.enableReceiveTimeout(100);
          }
          catch(UnsupportedCommOperationException e)
          {
            System.out.println(e);
          }
          try
          {
            outputStream.write(str.getBytes());
            j = n = rcount = kk = 0;
            while (kk >= 0 && kk < 3000)
            {
              d.ppclear(gg, 16.0, 48.0, 180.0);
              if (axis == 0)
                d.dtext(16.0, 48.0, gg, Color.black, "waiting on azimuth   " + kk);
              else
                d.dtext(16.0, 48.0, gg, Color.black, "waiting on elevation " + kk);

              j = inputStream.read();
              kk++;
              if (j >= 0 && n < 80)
              {
                recv[n] = (char)j;
                n++;
              }
              if (n > 0 && j == -1)
                kk = -1;        // end of message

              t.getTsec(g, d, gg);
            }
            d.ppclear(gg, 16.0, 48.0, 180.0);
          }
          catch(IOException e)
          {
            System.out.println(e);
          }
          // no need to close
        }
        if (kk != -1 || (recv[0] != 'M' && recv[0] != 'T'))
        {
          d.dtext(16.0, 16.0, gg, Color.red, "comerr j=" + j + " n=" + n + "mm" + count);
          g.set_comerr(g.get_comerr() + 1);
          if (g.get_fstatus() == 1)
            o.stroutfile(g, "* ERROR comerr");
          if (g.get_mainten() == 0)
            g.set_stow(1);
          return;
        }

        if (g.get_azelsim() != 0 && g.get_azelsim() < 10)
        {
          d.ppclear(gg, 16.0, 48.0, 180.0);
          try
          {
            Thread.sleep(100);
          }
          catch(InterruptedException e)
          {
            System.out.println(e);
          }
        }
        str = String.copyValueOf(recv, 0, n - 1);
        d.ppclear(gg, 16.0, 80.0, 180.0);
        d.dtext(16.0, 80.0, gg, Color.black, "recv " + str);
        parser = new StringTokenizer(str);
        try
        {
          str2 = parser.nextToken();
        }
        catch(NoSuchElementException e)
        {
        }
        rcount = 0;
        try
        {
          str2 = parser.nextToken();
          rcount = Integer.valueOf(str2).intValue();
        }
        catch(NoSuchElementException e)
        {
        }
        if (rcount != count && g.get_azcmd() != g.get_azlim1()
            && g.get_elcmd() != g.get_ellim1())
        {
          d.dtext(16.0, 48.0, gg, Color.red, "lost count goto Stow");
          d.dtext(440.0, ylim + 40.0, gg, Color.blue, "ERROR:  ");
          d.dtext(8.0, ylim + 60.0, gg, Color.black, "received " +
                  rcount + " counts out of " + count + " counts expected");
          if (mm == 1)
            d.dtext(8.0, ylim + 74.0, gg, Color.black,
                    "while going clockwise in azimuth");
          if (mm == 0)
            d.dtext(8.0, ylim + 74.0, gg, Color.black,
                    "while going counter-clockwise in azimuth");
          if (mm == 3)
            d.dtext(8.0, ylim + 74.0, gg, Color.black,
                    "while going clockwise in elevation");
          if (mm == 2)
            d.dtext(8.0, ylim + 74.0, gg, Color.black,
                    "while going counter-clockwise in elevation");
          d.dtext(8.0, ylim + 88.0, gg, Color.black,
                  "motor stalled or limit prematurely reached");

          if (g.get_fstatus() == 1)
            o.stroutfile(g, "* ERROR lost count");
          if (g.get_mainten() == 0)
          {
            g.set_stow(1);
            g.set_azcmd(g.get_azlim1());
            g.set_elcmd(g.get_ellim1());
          }
          return;
        }
        if (mm == 2 && recv[0] == 'T')
        {
          elatstow = 1;
          g.set_elcount(0);
          g.set_elnow(g.get_ellim1());
        }
        if (mm == 0 && recv[0] == 'T')
        {
          azatstow = 1;
          g.set_azcount(0);
          g.set_aznow(g.get_azlim1());
        }
        if (recv[0] == 'T' && g.get_stow() == 0)
        {
          d.dtext(16.0, 32.0, gg, Color.black, "timeout from antenna");
        }
        if (recv[0] == 'M')
        {
          if (axis == 0)
          {
            azatstow = 0;
            if (mm == 1)
              g.set_azcount(g.get_azcount() + count);
            else
              g.set_azcount(g.get_azcount() - count);
          }
          if (axis == 1)
          {
            elatstow = 0;
            if (mm == 3)
              g.set_elcount(g.get_elcount() + count);
            else
              g.set_elcount(g.get_elcount() - count);
          }
        }
        if (g.get_azelsim() == 0)
        {
          try
          {
            Thread.sleep(5);
          }
          catch(InterruptedException e)
          {
            System.out.println(e);
          }
        }
      }
      axis++;
      if (axis > 1)
        axis = 0;
    }
    if (g.get_track() != -1)
    {
      if (g.get_slew() == 1)
        g.set_track(0);
      else
        g.set_track(1);
    }
    g.set_aznow(g.get_azlim1() - g.get_azcor() + g.get_azcount() / scale);
    if (g.get_aznow() > 360.0)
      g.set_aznow(g.get_aznow() - 360.0);
    g.set_elnow(g.get_ellim1() - g.get_elcor() + g.get_elcount() / scale);
    if (g.get_elnow() > 90.0)
    {
      if (g.get_aznow() >= 180.0)
        g.set_aznow(g.get_aznow() - 180.0);
      else
        g.set_aznow(g.get_aznow() + 180.0);
      g.set_elnow(180.0 - g.get_elnow());
    }
    d.dtext(670.0, 40.0, gg, Color.black,
    "azel  " + d.dc(g.get_aznow(), 5, 1) + " " + d.dc(g.get_elnow(), 5, 1) +
            " deg");
    x = g.get_xlast(0);
    y = g.get_ylast(0);
    d.lpaint(gg, Color.white, (double)(x - 10), (double)y,
             (double)(x + 10), (double)y);
    d.lpaint(gg, Color.white, (double)x, (double)(y - 10),
             (double)x, (double)(y + 10));
    x = (int)(g.get_aznow() * 640.0 / 360.0);
    if (g.get_south() == 0)
    {
      x -= 320;
      if (x < 0)
        x += 640;
    }
    y = (int)(ylim - g.get_elnow() * (ylim - ylim0) / 90);
    g.set_xlast(x, 0);
    g.set_ylast(y, 0);
    d.lpaint(gg, Color.red, (double)(x - 10), (double)y,
             (double)(x + 10), (double)y);
    d.lpaint(gg, Color.red, (double)x, (double)(y - 10),
             (double)x, (double)(y + 10));
    if (g.get_aznow() == g.get_azlim1() && g.get_elnow() == g.get_ellim1())
    {
      d.set_bc(Color.green, 3);
      d.dtext(340.0, ylim + 40.0, gg, Color.black, "Status: at stow");
      g.set_stow(-1);           // at stow

    }
    else
    {
      d.set_bc(Color.black, 3);
      if (g.get_stow() == -1)
        g.set_stow(0);
    }
    if (g.get_stow() != 0)
    {
      g.set_track(0);
    }
    sec = (double)t.getTsec(g, d, gg);
    ra = geom.get_galactic_ra(sec, g.get_aznow(), g.get_elnow(), g, t);
    dec = geom.get_galactic_dec();
    glat = geom.get_galactic_glat();
    glon = geom.get_galactic_glon();
    vel = geom.get_galactic_vel();
    if (Math.abs(g.get_fcenter() - 1665.4) < 1.0)
      g.set_restfreq(1665.4);
    else
      g.set_restfreq(1420.406);
    if (Math.abs(g.get_fcenter() - 1612.231) < 1.0)
      g.set_restfreq(1612.231);
    g.set_vlsr(vel);
    g.set_glat(glat);
    g.set_glon(glon);
    if (Math.abs(g.get_fcenter() - 1667.359) < 1.0)
      g.set_restfreq(1667.359);
    d.dtext(670.0, 120.0, gg, Color.black,
            "radec " + d.dc(ra, 5, 1) + " hrs " + d.dc(dec, 5, 1) + " deg");
    d.dtext(670.0, 104.0, gg, Color.black, "Galactic l ="
            + d.dc(glon, 4, 0) + " b =" + d.dc(glat, 3, 0));
    d.dtext(665.0, 426.0, gg, Color.black,
            "VLSR    " + d.dc(vel, 6, 1) + " km/s");
    vel = -vel - (g.get_fcenter() - g.get_restfreq()) * 299790.0 / g.get_restfreq();
    d.dtext(665.0, 442.0, gg, Color.black,
            "Vcenter " + d.dc(vel, 6, 1) + " km/s");
    d.lpaint(gg, Color.black, 0.0, 85.0, 217.0, 85.0);
    d.dtext(16.0, 16.0, gg, Color.black,
     g.get_statnam() + " lat " + d.dc(g.get_lat() * 180.0 / Math.PI, 4, 1) +
            " lonw " + d.dc(g.get_lon() * 180.0 / Math.PI, 4, 1));
    d.lpaint(gg, Color.black, 0.0, 0.0, 217.0, 0.0);
    return;
  }
  double radio(double freq, global g, disp d, Graphics gg, outfile o)
  // communicate with the radiometer
  {
    double power,
      tsig,
      a;
    int k,
      n,
      j,
      i;
    byte m[] = new byte[8];
    int recv[] = new int[8];
    byte b8,
      b9,
      b10,
      b11;
      power = tsig = 0.0;
      j = (int)(freq * (1.0 / 0.04) + 0.5); /* bits for reference divider of syn */
      b8 = (byte) g.get_atten(); /* radio atten */
      b9 = (byte) (j & 0x3f);
      b10 = (byte) ((j >> 6) & 0xff);
      b11 = (byte) ((j >> 14) & 0xff);
      m[0] = (byte) 'f';
      m[1] = (byte) 'r';
      m[2] = (byte) 'e';
      m[3] = (byte) 'q';
      m[4] = b11;
      m[5] = b10;
      m[6] = b9;
      m[7] = b8;
      g.set_freqa(((b11 * 256.0 + (b10 & 0xff)) * 64.0 + (b9 & 0xff)) * 0.04);
      j = n = 0;
    if (g.get_radiosim() == 0)
    {
      try
      {
        for (i = 0; i < 8; i++)
        {
          outputStream.write(m[i]);
        }
        try
        {
          serialPort.enableReceiveTimeout(10);
        }
        catch(UnsupportedCommOperationException e)
        {
          System.out.println(e);
        }
        j = n = i = 0;
        while (i >= 0 && i < 200)
        {
          j = inputStream.read();
          if (j >= 0 && n < 8)
          {
            recv[n] = j;
            n++;
          }
          i++;
          if (n > 2 && j == -1)
            i = -1;             // end of message

        }
        t.getTsec(g, d, gg);
        d.ppclear(gg, 16.0, 48.0, 180.0);
        if (i >= 200)
          d.dtext(16.0, 48.0, gg, Color.red, "waiting on recvr");
      }
      catch(IOException e)
      {
        System.out.println(e);
      }
    }
// no need to inputStream.close();

    if (n != 3 && g.get_radiosim() == 0)
    {
      d.dtext(16.0, 16.0, gg, Color.red,
              " error comm with radio " + n);
      g.set_comerad(g.get_comerad() + 1);
      if (g.get_fstatus() == 1)
        o.stroutfile(g, "* ERROR communicating with radio");
      return -1.0;
    }
    else
    {
      if (g.get_radiosim() != 0)
      {
        power = 200.0 + g.get_tspill();
        if (g.get_sourn() > 0)
        {
          if (g.get_sounam(g.get_sourn()).lastIndexOf("Moon") > -1)
            tsig = 1.0;
          if (g.get_sounam(g.get_sourn()).lastIndexOf("Cass") > -1)
            tsig = 2.6;
          if (g.get_sounam(g.get_sourn()).lastIndexOf("Sun") > -1)
            tsig = 1000.0;
        }
        if (g.get_scan() != 0 || g.get_track() != 0)
        {
          a = g.get_azoff() * Math.cos(g.get_elcmd() * Math.PI / 180.0);
          a = ((g.get_eloff() * g.get_eloff() + a * a + 0.1) / g.get_beamw()) * 0.7;
          power += tsig * Math.pow(2.718, -a);
        }
        try
        {
          Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
          System.out.println(e);
        }
        if (g.get_calon() == 1)
          power = power + g.get_tload() - g.get_tspill();
        if (g.get_calon() == 2)
          power = power + g.get_noisecal();
        power += power * gauss() / Math.sqrt(40e3 * 0.1);
        d.dtext(16.0, 80.0, gg, Color.black, "generating random data");
        if (g.get_atten() != 0)
          power = power * 0.1;
      }
      if (g.get_radiosim() == 0)
      {
        if (recv[0] > 0)
        {
          power = (recv[1] * 256.0 + recv[2]) / (double)recv[0];
          power = 1e06 / power;
        }
        else
          power = 0.0;
      }
      if (g.get_atten() != 0)
        a = g.get_calcons() * power * 10.0;
      else
        a = g.get_calcons() * power;
      d.dtext(8.0, ylim + 40.0, gg, Color.black, "Recvr freq: " +
              d.dc(g.get_freqa(), 7, 2) + " pwr: "
              + d.dc(power, 4, 0) + " counts"
              + " temp: " + d.dc(a, 4, 0) + "K"
              + " atten=" + g.get_atten() * 10 + "dB");
      return a;
    }
  }
  double radiodg(double freq, global g, disp d, Graphics gg, outfile o)
  // communicate with the radiometer
  {
    double power,
      avpower,
      tsig,
      a;
    int k,
      n,
      j,
      mode,
      i;
    byte m[] = new byte[10];
    int recv[] = new int[128];
    byte b8,
      b9,
      b10,
      b11;
      power = tsig = 0.0;
      j = (int)(freq * (1.0 / 0.04) + 0.5); /* bits for reference divider of syn */
      mode = g.get_digital() - 1;
    if (g.get_digital() == 4 || g.get_digital() == 5)
        mode = 0;
      b8 = (byte) mode;         /* mode */
      b9 = (byte) (j & 0x3f);
      b10 = (byte) ((j >> 6) & 0xff);
      b11 = (byte) ((j >> 14) & 0xff);
      m[0] = 0;
      m[1] = (byte) 'f';
      m[2] = (byte) 'r';
      m[3] = (byte) 'e';
      m[4] = (byte) 'q';
      m[5] = b11;
      m[6] = b10;
      m[7] = b9;
      m[8] = b8;
      g.set_freqa(((b11 * 256.0 + (b10 & 0xff)) * 64.0 + (b9 & 0xff)) * 0.04 - 0.8);
      j = n = 0;
    if (g.get_radiosim() == 0)
    {
      try
      {
        for (i = 0; i < 9; i++)
        {
          outputStream.write(m[i]);
        }
        try
        {
          serialPort.enableReceiveTimeout(100);
        }
        catch(UnsupportedCommOperationException e)
        {
          System.out.println(e);
        }
        j = n = i = 0;
        while (i >= 0 && i < 200)
        {
          j = inputStream.read();
          if (j >= 0)
          {
            if (n < 128)
              recv[n] = j;
            n++;
          }
          i++;
          if (n >= 128 && j == -1)
            i = -1;             // end of message

        }
        t.getTsec(g, d, gg);
        d.ppclear(gg, 16.0, 48.0, 180.0);
        if (i >= 200)
          d.dtext(16.0, 48.0, gg, Color.red, "waiting on recvr");
      }
      catch(IOException e)
      {
        System.out.println(e);
      }
    }
// no need to inputStream.close();

    if (n != 128 && g.get_radiosim() == 0)
    {
      d.dtext(16.0, 16.0, gg, Color.red,
              " error comm with radio " + n);
      g.set_comerad(g.get_comerad() + 1);
      if (g.get_fstatus() == 1)
        o.stroutfile(g, "* ERROR communicating with radio");
      return -1.0;
    }
    else
    {
      a = avpower = 0;
      for (i = 0; i < 64; i++)
      {
        if (g.get_radiosim() != 0)
        {
          power = 200.0 + g.get_tspill();
          if (g.get_sourn() > 0 && i == 0)
          {
            if (g.get_sounam(g.get_sourn()).lastIndexOf("Moon") > -1)
              tsig = 1.0;
            if (g.get_sounam(g.get_sourn()).lastIndexOf("Cass") > -1)
              tsig = 2.6;
            if (g.get_sounam(g.get_sourn()).lastIndexOf("Sun") > -1)
              tsig = 1000.0;
          }
          if (g.get_scan() != 0 || g.get_track() != 0)
          {
            a = g.get_azoff() * Math.cos(g.get_elcmd() * Math.PI / 180.0);
            a = ((g.get_eloff() * g.get_eloff() + a * a + 0.1) / g.get_beamw()) * 0.7;
            power += tsig * Math.pow(2.718, -a);
          }
          try
          {
            Thread.sleep(5);
          }
          catch(InterruptedException e)
          {
            System.out.println(e);
          }
          if (g.get_calon() == 1)
            power = power + g.get_tload() - g.get_tspill();
          if (g.get_calon() == 2)
            power = power + g.get_noisecal();
          power += power * gauss() / Math.sqrt(g.get_freqsep() * 1e06 * g.get_intg());
          d.dtext(16.0, 80.0, gg, Color.black, "generating random data");
          power = power * g.get_graycorr(i);
        }
        if (g.get_radiosim() == 0)
        {
          if (i <= 31)
            k = (i + 32) * 2;
          else
            k = (i - 32) * 2;
          power = (recv[k] * 256.0 + recv[k + 1]);
        }
        if (g.get_digital() < 5)
          a = (i - 32) * g.get_freqsep() * 0.4;
        else
          a = 0;
        if (g.get_graycorr(i) > 0.8)
          power = power / (g.get_graycorr(i) * (1.0 + a * a * g.get_curvcorr()));
        a = g.get_calcons() * power;
        if (i > 0)
          g.set_specd(a, 64 - i); // reverse lower sideband - makes 31 DC

        else
          g.set_specd(a, 0);
        if (i >= 10 && i < 54)
          avpower += power;
      }
      avpower = avpower / 44.0;
      a = g.get_calcons() * avpower;
      d.dtext(8.0, ylim + 40.0, gg, Color.black, "digital Recvr freq: " +
              d.dc(g.get_freqa(), 7, 2) + " pwr: "
              + d.dc(avpower, 4, 0) + " counts"
              + " temp: " + d.dc(a, 4, 0) + "K");
      return a;
    }
  }

  double gauss()
  {
    double v1,
      v2,
      r,
      fac,
      amp,
      vv1;
      r = v1 = 0.0;
    while (r > 1.0 || r == 0.0)
    {
      v1 = 2.0 * Math.random() - 1.0;
      v2 = 2.0 * Math.random() - 1.0;
      r = v1 * v1 + v2 * v2;
    }
    fac = Math.sqrt(-2.0 * Math.log(r) / r);
    vv1 = v1 * fac;
    amp = vv1;
    return amp;
  }

  void cal(int mode, global g, disp d, Graphics gg, outfile o)
  // command calibration vane
  {

// mode=0 calout mode=1 calin
    int j,
      k,
      kk,
      n,
      i;
    String str,
      str2;
    char m[] = new char[80];
    char recv[] = new char[80];
    if (g.get_azelsim() == 0)
        d.dtext(16.0, 32.0, gg, Color.black,
                "vane calibrator status:");
    else
        d.dtext(16.0, 32.0, gg, Color.black, "calibrator simulated");
      str = "  move " + (mode + 4) + " 0 \n"; // need space at start and end

      d.dtext(16.0, 64.0, gg, Color.black,
              "trans " + str.substring(0, str.length() - 1) + "     ");
      d.ppclear(gg, 16.0, 80.0, 180.0);
    if (g.get_azelsim() != 0)
    {
      str2 = "T \n";
      str2.getChars(0, str2.length(), recv, 0);
      n = str2.length();
    }
    j = n = kk = 0;

    if (g.get_azelsim() == 0)
    {
      try
      {
        outputStream.write(str.getBytes());
        try
        {
          serialPort.enableReceiveTimeout(1000);
        }
        catch(UnsupportedCommOperationException e)
        {
          System.out.println(e);
        }
        j = n = kk = 0;
        while (kk >= 0 && kk < 60)
        {
          if (mode == 1)
            d.dtext(16.0, 48.0, gg, Color.black,
                    "waiting on calin     " + kk);
          else
            d.dtext(16.0, 48.0, gg, Color.black,
                    "waiting on calout    " + kk);

          j = inputStream.read();
          kk++;
          if (j >= 0 && n < 80)
          {
            recv[n] = (char)j;
            n++;
          }
          if ((n > 0 && j == -1) || j == 13)
            kk = -1;

          t.getTsec(g, d, gg);
        }
        d.ppclear(gg, 16.0, 48.0, 180.0);
      }
      catch(IOException e)
      {
        System.out.println(e);
      }
    }

    recv[n] = 0;
    if (kk != -1 && g.get_azelsim() == 0)
    {
      d.dtext(16.0, 16.0, gg, Color.red, "comerr j=" + j + " n=" + n);
      g.set_comerr(g.get_comerr() + 1);
      if (g.get_fstatus() == 1)
        o.stroutfile(g, "* ERROR comerr on cal");
      return;
    }
    d.dtext(16.0, 80.0, gg, Color.black, "recv " + str);
    return;
  }
}
