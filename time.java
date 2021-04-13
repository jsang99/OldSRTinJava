import java.lang. *;
import java.awt. *;
public class time
// gets the time from the PC clock
{
  private long tmil,
    i,
    year,
    yr,
    day,
    days,
    hr,
    min,
    sec;
  private String da,
    h,
    m,
    s,
    ydhms,
    ydh;
  private double gst,
    secs;
  public long getTsec(global g, disp d, Graphics gg)
  {
    tmil = System.currentTimeMillis(); // this is always UT

    /* local time set by time command - check zone
       using windows control panel */
    if (g.get_azelsim() > 1)
    {
      if (g.get_tstart() == 0.0)
        g.set_tstart(tmil);
      tmil = (g.get_tstart() + (tmil - g.get_tstart()) * g.get_azelsim());
      /* speed up */
    }
    if (g.get_azelsim() < 0)
        tmil += -(long)g.get_azelsim() * 3600000;

      day = tmil / 86400000;
      sec = tmil / 1000 - day * 86400;

    for (i = 1970; day > 365; i++)
    {
      days = ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) ? 366 : 365;
      day -= days;
    }
    hr = sec / 3600;
    sec -= hr * 3600;
    min = sec / 60;
    sec = sec - min * 60;
    year = i;
    day = day + 1;
    if (day == 366)             // fix for problem with day 366

    {
      days = ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) ? 366 : 365;
      if (days == 365)
      {
        day -= 365;
        year++;
      }
    }
    da = "" + day;
    if (day < 100)
      da = "0" + da;
    if (day < 10)
      da = "0" + da;
    h = "" + hr;
    if (hr < 10)
      h = "0" + h;
    m = "" + min;
    if (min < 10)
      m = "0" + m;
    s = "" + sec;
    if (sec < 10)
      s = "0" + s;
    ydhms = year + ":" + da + ":" + h + ":" + m + ":" + s;
    if (year < 2000)
      yr = year - 1900;
    else
      yr = year - 2000;
    ydh = yr + da + h;
    if (ydhms.indexOf(g.get_ptime()) == -1)
      d.stext(670.0, 152.0, gg, Color.white, "UT " + g.get_ptime());
    d.stext(670.0, 152.0, gg, Color.black, "UT " + ydhms);
    g.set_ptime(ydhms);
    return tmil / 1000;
  }
  public String getYdhms()
  {
    return ydhms;
  }
  public String getYdh()
  {
    return ydh;
  }
  public long get_year()
  {
    return year;
  }
  public double getGst()
  {
    secs = (1999 - 1970) * 31536000.0 + 17.0 * 3600.0 + 16.0 * 60.0 + 20.1948;
    for (i = 1970; i < 1999; i++)
    {
      if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0)
        secs += 86400.0;
    }
    return Math.IEEEremainder(((double)tmil / 1000.0 - secs), 86164.09053)
    / 86164.09053 * 2.0 * Math.PI;
/* 17 16 20.1948 UT at 0hr newyear1999 */
  }
  public String getMonthday()
  {
    int day_tab[] =
    {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31,
     0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String mon[] =
    {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
     "Aug", "Sep", "Oct", "Nov", "Dec"};
    int k,
      j,
      leap;
    String str;
      j = (int)day;
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
        leap = 1;
    else
        leap = 0;
    for (k = 1; j > day_tab[leap * 13 + k]; k++)
        j -= day_tab[leap * 13 + k];
      str = mon[k - 1];
      str += " " + j;
      return (str);
  }

/* Convert to Seconds since New Year 1970 */
  public double tosec(int yr, int day, int hr, int min, int sec)
  {
    int i;
    double secs;
      secs = (yr - 1970) * 31536000.0 + (day - 1) * 86400.0
    + hr * 3600.0 + min * 60.0 + sec;
    for (i = 1970; i < yr; i++)
    {
      if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0)
        secs += 86400.0;
    }
    if (secs < 0.0)
        secs = 0.0;
    return secs;
  }

}
