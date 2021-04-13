import java.awt. *;
public class geom               // methods for source coordinates
 {
  private double ra,
    dec,
    raout,
    decout,
    azim,
    elev,
    glat,
    glon,
    vel;
  private double pelback = 0.0;
  private double x,
    y;
  private double ylim = 415.0;
  private double ylim0 = 200.0;
  private time tim = new time();
  double get_moonra(double time, global g)
  {
    double ttime,
      asnode,
      amon,
      peri,
      em,
      aim,
      aam,
      vsm,
      alamm,
      moonsun,
      evn,
      var;
    double x,
      y,
      z,
      xx,
      yy,
      zz,
      ram,
      decm,
      ha,
      inc;
/* calc moon ra and dec  */
/* see notes and formulae Astronomical Almanac page D2 Moon, 1999 */
      ttime = tim.tosec(1999, 0, 0, 0, 0); // Jan 0 1999

      ttime = (time - ttime) / 86400.00;
    /* asnode=long of mean ascending node */
      asnode = 144.452077 - 0.05295377 * ttime;
    /* amon=omga plus mean lunar longitude */
      amon = 69.167124 + 13.17639648 * ttime;
    /* peri=asnode plus mean lunar longitude of perigee */
      peri = 42.524057 + 0.11140353 * ttime;
    /* moonsun is the elongation of moon from the sun */
      moonsun = 149.940812 + 12.19074912 * ttime;
    /* em is the eccentricity of lunar orbit */
      em = 0.054900489;
    /* aim=inclination of lunar orbit to ecliptic */
      aim = 5.1453964 * Math.PI / 180.0;
    /* vsm=true anomaly */
    /* the following are correction terms */
      vsm = 2.0 * em * Math.sin((amon - peri) * Math.PI / 180.0); /* elliptical orbit */
      evn = (1.274 / 57.3) * Math.sin((2 * moonsun - (amon - peri)) * Math.PI / 180.0); /* evection */
      var = (0.658 / 57.3) * Math.sin(2 * moonsun * Math.PI / 180.0); /* variation */
      alamm = (amon - asnode) * Math.PI / 180.0 + vsm + evn + var;
      x = Math.cos(alamm);
      y = Math.sin(alamm);
      z = 0;
      xx = x;
      yy = y * Math.cos(aim);
      zz = y * Math.sin(aim);
      ram = Math.atan2(yy, xx) + asnode * Math.PI / 180.0;
      decm = Math.atan2(zz, Math.sqrt(xx * xx + yy * yy));
      x = Math.cos(ram) * Math.cos(decm);
      y = Math.sin(ram) * Math.cos(decm);
      z = Math.sin(decm);
      inc = 23.45 * Math.PI / 180.0;
      xx = x;
      yy = y * Math.cos(inc) - z * Math.sin(inc);
      zz = z * Math.cos(inc) + y * Math.sin(inc);
    /* aam is the semi-major axis of orbit earth radii */
      aam = 60.2665;
      z = zz - Math.sin(g.get_lat()) / aam; /* correct for parallax */
      ha = tim.getGst() - g.get_lon();
      x = xx - Math.cos(g.get_lat()) * Math.cos(ha) / aam;
      y = yy - Math.cos(g.get_lat()) * Math.sin(ha) / aam;
      ra = Math.atan2(y, x);
      dec = Math.atan2(z, Math.sqrt(x * x + y * y));
      return ra;
  }
  double get_moondec()          // make 2nd. call to get moon's declination
   {
    return dec;
  }

  void setsounam(global g, disp d, Graphics gg, time tim)
  {
// plots the catalog sources on the screen
    double has,
      azs,
      elevs,
      sec,
      azss,
      elevss,
      azz,
      ell;
    int j,
      year;
      sec = tim.getTsec(g, d, gg);
      year = (int)tim.get_year();
      azs = elevs = has = 0.0;
    for (j = 1; j < g.get_nsou(); j++)
    {
      if (g.get_soutype(j) == 0)
      {
        raout = get_precess_ra(g.get_ras(j), g.get_decs(j), g.get_epoc(j), year);
        decout = get_precess_dec();
      }
      if (g.get_sounam(j).lastIndexOf("Sun") > -1)
      {
        raout = get_sunra(sec);
        decout = get_sundec();
      }
      if (g.get_sounam(j).lastIndexOf("Moon") > -1)
      {
        raout = get_moonra(sec, g);
        decout = get_moondec();
      }
      if (g.get_soutype(j) == 0)
      {
        has = tim.getGst() - raout - g.get_lon();
        azs = get_radec_az(has, decout, g.get_lat());
        elevs = get_radec_el();
      }
      if (g.get_soutype(j) == 4)
      {
        azs = get_sattosky_az(g.get_lon(), g.get_ras(j), g);
        elevs = get_sattosky_el();
      }
      if (g.get_soutype(j) == 1)
      {
        azs = g.get_ras(j);
        elevs = g.get_decs(j);
      }
      if (g.get_sourn() == j)
      {
        azz = azs * 180.0 / Math.PI;
        ell = elevs * 180. / Math.PI;
        g.set_mx(8.0, 3);
        g.set_my(384.0, 3);
        d.dtext(670.0, 200.0, gg, Color.black, d.dcs(g.get_sounam(j), 20));
        if (g.get_sounam(j).lastIndexOf("Sun") == -1
            && g.get_sounam(j).lastIndexOf("Moon") == -1
            && g.get_soutype(j) == 0)
          d.dtext(670.0, 216.0, gg, Color.black,
                  get_radecp(g.get_ras(j), g.get_decs(j))
                  + " " + d.dc(g.get_epoc(j), 4, 0));
        else
          d.ppclear(gg, 670.0, 216.0, 130.0);

        d.dtext(670.0, 232.0, gg, Color.black,
                "azel " + d.dc(azz, 6, 1) + " " + d.dc(ell, 6, 1) + " deg");
        d.dtext(670.0, 56.0, gg, Color.black, "total offsets: " +
                d.dc(g.get_azoff() + g.get_pazoff(), 6, 1) +
                d.dc(g.get_eloff() + g.get_peloff() + pelback, 6, 1));
        d.dtext(670.0, 72.0, gg, Color.black, "pointing corr" +
                d.dc(g.get_pazoff(), 5, 1) + d.dc(g.get_peloff(), 5, 1));
        d.dtext(670.0, 88.0, gg, Color.black, "axis corr"
                + d.dc(g.get_azcor(), 5, 1) + d.dc(g.get_elcor(), 5, 1) +
                "   ");
        if (g.get_drift() != 0)
        {
          azss = get_radec_az(has + 0.05, decout, g.get_lat());
          elevss = get_radec_el();
          g.set_azoff(azss * 180.0 / Math.PI - azz);
          g.set_eloff(elevss * 180. / Math.PI - ell);
          g.set_drift(g.get_drift() + 1);
        }
        if (g.get_track() != -1)
        {
          if (Math.cos(azz * Math.PI / 180.0) > 0.0 && ell < 80.0
              && g.get_south() == 1)
            pelback = g.get_elback();
          else
            pelback = 0.0;
          g.set_azcmd(azz + g.get_azoff() + g.get_pazoff());
          g.set_elcmd(ell + g.get_eloff() + g.get_peloff() + pelback);
          if (g.get_drift() > 2)
          {
            g.set_track(-1);
            g.set_drift(0);
            g.set_azoff(0.0);
            g.set_eloff(0.0);
          }
        }
      }
      y = g.get_ylast(j);
      if (y < ylim || elevs > 0.0)
      {
        x = g.get_xlast(j);
        d.spaint(gg, Color.white, x, y, 2);
        d.stext(x, y, gg, Color.white, g.get_sounam(j));
        x = azs * 320.0 / Math.PI;
        if (g.get_south() == 0)
        {
          x -= 320.0;
          if (x < 0.0)
            x += 640.0;
        }
        y = ylim - elevs * (ylim - ylim0) * 2 / Math.PI;
        if (y < ylim)
        {
          if (g.get_sourn() == j)
          {
            d.stext(x, y, gg, Color.red, g.get_sounam(j));
            d.spaint(gg, Color.red, x, y, 2);
          }
          else
          {
            if (g.get_soutype(j) != 4)
            {
              d.stext(x, y, gg, Color.black, g.get_sounam(j));
              d.spaint(gg, Color.black, x, y, 2);
            }
            if (g.get_soutype(j) == 4)
            {
              d.stext(x, y, gg, Color.black, g.get_sounam(j));
              d.spaint(gg, Color.blue, x, y, 2);
            }
          }
        }
        g.set_xlast(x, j);
        g.set_ylast(y, j);
      }
    }
    galactp(g, d, gg, tim);
  }

  void galactp(global g, disp d, Graphics gg, time tim)
  // plots the galactic plane
  {
    double has,
      xg,
      yg,
      xr,
      yr,
      zr,
      ra,
      dec,
      sec,
      azs,
      elevs;
    int j;
    for (j = 0; j < 360; j += 5)
    {
      xg = Math.cos((j + 2.5) * Math.PI / 180.0);
      yg = Math.sin((j + 2.5) * Math.PI / 180.0);
      xr = xg * Math.sin(27.1 * Math.PI / 180.0);
      yr = yg;
      zr = -xg * Math.cos(27.1 * Math.PI / 180.0);
      dec = Math.atan2(zr, Math.sqrt(xr * xr + yr * yr));
      ra = Math.atan2(yr, xr) + (12.0 + 51.4 / 60.0) * Math.PI / 12.0;
      sec = tim.getTsec(g, d, gg);
      has = tim.getGst() - ra - g.get_lon();
      azs = get_radec_az(has, dec, g.get_lat());
      elevs = get_radec_el();
      y = g.get_gylast(j);
      if (y < ylim || elevs > 0.0)
      {
        x = g.get_gxlast(j);
        d.dpaint(gg, Color.white, x, y);
        x = azs * 320.0 / Math.PI;
        if (g.get_south() == 0)
        {
          x -= 320.0;
          if (x < 0.0)
            x += 640.0;
        }
        y = ylim - elevs * (ylim - ylim0) * 2 / Math.PI;
        if (y < ylim)
          d.dpaint(gg, Color.black, x, y);
        g.set_gxlast(x, j);
        g.set_gylast(y, j);
      }
    }
  }

  double get_sattosky_az(double longw, double sat, global g)
  // coordinate conversion for synchronous orbit satellites
  {
    double re,
      rs,
      satlong,
      ws,
      gs,
      ps,
      wsat,
      gsat,
      psat,
      wss,
      gss,
      pss;
    double west,
      radd,
      zen,
      north;
/* convert from satellite long to sky coords  */
/* input longw;sat; output azim;elev */
      re = 6378.16;
      rs = 42240.4;
      satlong = sat * Math.PI / 180.0;
/* geocentric coords of site ws;gs;ps west;greenwich;pole */
      ws = re * Math.cos(g.get_lat()) * Math.sin(longw);
      gs = re * Math.cos(g.get_lat()) * Math.cos(longw);
      ps = re * Math.sin(g.get_lat());
/* satellite coords wsat;gsat;psat */
      wsat = rs * Math.sin(satlong);
      gsat = rs * Math.cos(satlong);
      psat = 0;
      wss = wsat - ws;
      gss = gsat - gs;
      pss = psat - ps;
      west = wss * Math.cos(longw) - gss * Math.sin(longw);
      radd = gss * Math.cos(longw) + wss * Math.sin(longw);
      zen = radd * Math.cos(g.get_lat()) + pss * Math.sin(g.get_lat());
      north = pss * Math.cos(g.get_lat()) - radd * Math.sin(g.get_lat());
      elev = Math.atan2(zen, Math.sqrt(north * north + west * west));
      azim = Math.atan2(-west, north);
    if (azim < 0.0)
        azim += Math.PI * 2.0;
      return azim;
  }
  double get_sattosky_el()
  {
    return elev;
  }

  double get_galactic_ra(double time, double az, double el, global g, time tim)
  // convert from azimuth and elavation to galactic coords, ra and dec and
  // calculate velocity of the local standard of rest
  {
    double north,
      west,
      zen,
      pole,
      rad,
      ha,
      gwest,
      grad,
      gpole,
      rac,
      decc,
      ra,
      dec;
    double ggwest,
      lon0,
      vsun,
      x0,
      y0,
      z0,
      sunlong,
      soulong,
      soulat,
      x,
      y,
      z,
      dp,
      rp;
      decc = -(28.0 + 56.0 / 60.0) * Math.PI / 180.0;
      rac = (17.0 + 45.5 / 60.0) * Math.PI / 12.0;
      dp = 27.1 * Math.PI / 180.0;
      rp = (12.0 + 51.4 / 60.0) * Math.PI / 12.0;
      north = Math.cos(az * Math.PI / 180.0) * Math.cos(el * Math.PI / 180.0);
      west = -Math.sin(az * Math.PI / 180.0) * Math.cos(el * Math.PI / 180.0);
      zen = Math.sin(el * Math.PI / 180.0);
      pole = north * Math.cos(g.get_lat()) + zen * Math.sin(g.get_lat());
      rad = zen * Math.cos(g.get_lat()) - north * Math.sin(g.get_lat());
      dec = Math.atan2(pole, Math.sqrt(rad * rad + west * west));
      ha = Math.atan2(west, rad);
      ra = -ha + tim.getGst() - g.get_lon();
      x0 = 20.0 * Math.cos(18.0 * Math.PI / 12.0) * Math.cos(30.0 * Math.PI / 180.0);
      y0 = 20.0 * Math.sin(18.0 * Math.PI / 12.0) * Math.cos(30.0 * Math.PI / 180.0);
      z0 = 20.0 * Math.sin(30.0 * Math.PI / 180.0); /* sun 20km/s towards ra=18h dec=30.0 */
      vsun = -x0 * Math.cos(ra) * Math.cos(dec) - y0 * Math.sin(ra) * Math.cos(dec)
    - z0 * Math.sin(dec);
      x0 = Math.cos(ra) * Math.cos(dec);
      y0 = Math.sin(ra) * Math.cos(dec);
      z0 = Math.sin(dec);
      x = x0;
      y = y0 * Math.cos(23.5 * Math.PI / 180.0) + z0 * Math.sin(23.5 * Math.PI / 180.0);
      z = z0 * Math.cos(23.5 * Math.PI / 180.0) - y0 * Math.sin(23.5 * Math.PI / 180.0);
      soulat = Math.atan2(z, Math.sqrt(x * x + y * y));
      soulong = Math.atan2(y, x);
      sunlong = (time * 360.0 / (365.25 * 86400.0) + 280.0) * Math.PI / 180.0; /* long=280 day 1 */
      vel = vsun - 30.0 * Math.cos(soulat) * Math.sin(sunlong - soulong);
    if (ra > Math.PI * 2.0)
        ra += -Math.PI * 2.0;
      gwest = Math.cos(decc) * Math.cos(rp - rac);
      grad = Math.cos(decc) * Math.sin(rp - rac);
      ggwest = gwest * Math.sin(dp) - Math.sin(decc) * Math.cos(dp);
      gpole = gwest * Math.cos(dp) + Math.sin(decc) * Math.sin(dp);
      lon0 = (Math.atan2(ggwest, grad)) * 180.0 / Math.PI;
      gwest = Math.cos(dec) * Math.cos(rp - ra);
      grad = Math.cos(dec) * Math.sin(rp - ra);
      ggwest = gwest * Math.sin(dp) - Math.sin(dec) * Math.cos(dp);
      gpole = gwest * Math.cos(dp) + Math.sin(dec) * Math.sin(dp);
      glat = (Math.atan2(gpole, Math.sqrt(ggwest * ggwest + grad * grad))) * 180.0 / Math.PI;
      glon = (Math.atan2(ggwest, grad)) * 180.0 / Math.PI - lon0;
    if (glon < 0.0)
        glon += 360.0;
    if (glon > 360.0)
        glon += -360.0;
      raout = ra * 12.0 / Math.PI;
      decout = dec * 180.0 / Math.PI;
    if (raout < 0.0)
        raout += 24.0;
      return raout;
  }
  double get_galactic_dec()
  {
    return decout;
  }
  double get_galactic_glat()
  {
    return glat;
  }
  double get_galactic_glon()
  {
    return glon;
  }
  double get_galactic_vel()
  {
    return vel;
  }

  double antiltel(double az, global g)
  // calculate the elevation correction
  // associated with a tilt in azimuth axis
  {
    double a,
      b;
      a = g.get_azaxis_tilt() * Math.cos((az - g.get_tilt_az()) * Math.PI / 180.0);
      b = g.get_azaxis_tilt() *
      Math.cos((g.get_azlim1() - g.get_tilt_az()) * Math.PI / 180.0);
      return (a - b);
  }
  double antiltaz(double az, double el, global g)
  // calculate azimuth correction associated with axis tilts
  {
    double a,
      b,
      x;
    if (el < 89.9)
    {
      a = (g.get_azaxis_tilt() * Math.sin((az - g.get_tilt_az()) * Math.PI / 180.0)
           + g.get_elaxis_tilt()) * Math.tan(el * Math.PI / 180.0) * Math.PI / 180.0;
      a = Math.atan(a);
      b = (g.get_azaxis_tilt() *
           Math.sin((g.get_azlim1() - g.get_tilt_az()) * Math.PI / 180.0)
           + g.get_elaxis_tilt()) * Math.tan(g.get_ellim1() * Math.PI / 180.0) * Math.PI / 180.0;
      b = Math.atan(b);
      x = a - b;
    }
    else
        x = 0.0;
    return (x * 180.0 / Math.PI);
  }

/* Approximate Precession */
  double get_precess_ra(double rain, double decin,
                        double epin, double epout)
  {
    decout = decin + 0.0000972 * Math.cos(rain) * (epout - epin);
    raout = rain +
    ((0.000223 + 0.0000972 * Math.sin(rain) * Math.tan(decin))
     * (epout - epin));
    return raout;
  }
  double get_precess_dec()
  {
    return decout;
  }

/* Calculate Sun ra and dec (approximate) */
/* see Astronomical Almanac page C24 Sun 1999 */
  double get_sunra(double time)
  {
    double n,
      g,
      lon,
      ecl;
      n = -365.5 + (time - tim.tosec(1999, 1, 0, 0, 0)) / 86400.0;
      g = (357.528 + 0.9856003 * n) * Math.PI / 180.0;
      lon = (280.460 + 0.9856474 * n + 1.915 * Math.sin(g) + 0.02
             * Math.sin(2 * g)) * Math.PI / 180.0;
      ecl = (23.439 - 0.0000004 * n) * Math.PI / 180.0;
      ra = Math.atan2(Math.sin(lon) * Math.cos(ecl), Math.cos(lon));
      dec = Math.asin(Math.sin(lon) * Math.sin(ecl));
      return ra;
  }
  double get_sundec()
  {
    return dec;
  }

  double get_radec_az(double has, double decs, double lat)
  {
/* convert from sky to antenna coords (azel mount) */
/* input: has,decs,lat
   output: azs=azimuth of source
   elev=elevation of source
 */
    double p,
      w,
      r,
      zen,
      north,
      azs,
      elevs;
      p = Math.sin(decs);
      w = Math.sin(has) * Math.cos(decs);
      r = Math.cos(has) * Math.cos(decs);
      zen = r * Math.cos(lat) + p * Math.sin(lat);
      north = -r * Math.sin(lat) + p * Math.cos(lat);
      elev = Math.atan2(zen, Math.sqrt(north * north + w * w));
      azs = Math.atan2(-w, north);
    if (azs < 0)
        azs = azs + Math.PI * 2.0;
      return azs;
  }
  double get_radec_el()
  {
    return elev;
  }

  String get_radecp(double ra, double dec)
  // make a printable version of ra and dec
  {
    int hr,
      min,
      sec,
      deg;
    String rahhmm,
      h,
      m,
      s,
      d;
      sec = (int)(ra * 12.0 * 3600.0 / Math.PI);
      hr = sec / 3600;
      sec -= hr * 3600;
      min = sec / 60;
      sec = sec - min * 60;
      h = "" + hr;
    if (hr < 10)
        h = "0" + h;
      m = "" + min;
    if (min < 10)
        m = "0" + m;
      s = "" + sec;
    if (sec < 10)
        s = "0" + s;
      rahhmm = h + ":" + m + ":" + s;
      sec = (int)(Math.abs(dec) * 180.0 * 3600.0 / Math.PI);
      deg = sec / 3600;
      sec -= deg * 3600;
      min = sec / 60;
      sec = sec - min * 60;
      d = "" + deg;
    if (deg < 10)
        d = "0" + d;
    if (dec < 0.0)
        d = "-" + d;
      m = "" + min;
    if (min < 10)
        m = "0" + m;
      s = "" + sec;
    if (sec < 10)
        s = "0" + s;
      rahhmm += " " + d + ":" + m + ":" + s;
      return (rahhmm);
  }
}
