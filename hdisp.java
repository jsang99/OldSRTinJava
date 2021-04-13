import java.awt. *;
import java.awt.event. *;
import java.text. *;
public class hdisp extends Frame implements ActionListener
// class for Frame, mouse control and graphics
{
  int w,
    h;
  int h0 = 0;
  int h1 = 0;
  int h2 = 0;
  int h3 = 0;
  int h4 = 0;
  int h5 = 0;
  String str4 = "";
  global glb;
  TextArea ta = new TextArea();
  Button b[] = new Button[20];
  String but[] =
  {
    "srt.cat", "srt.cmd", "plots", "outputfile", "cmdline", "howto"};
  public hdisp(String title, global g)
  {
    super(title);
    int i;
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      w = 600;
      h = 300;
      setBounds(0, 0, w, h);
      glb = g;
    Panel p = new Panel(new GridLayout(1, 0));
    for (i = 0; i < 6; i++)
    {
      b[i] = new Button(but[i]);
      p.add(b[i]);
      b[i].addActionListener(this);
    }
    add(p, BorderLayout.NORTH);
      add(ta, BorderLayout.CENTER);
      setBackground(Color.white);
      ta.setBackground(Color.white);
      g.set_hlp(1);
      str4 = "click on buttons above for info";
      ta.insert(str4, 0);
      setVisible(true);
  }
  void focus(global g)
  {
    requestFocus();
  }
  protected void processWindowEvent(WindowEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      dispose();
//    System.out.println("closehelp");
      glb.set_hlp(0);
    }
    super.processWindowEvent(e);
  }
  public void actionPerformed(ActionEvent evt)
  {
    String str;
//    System.out.println("id "+evt.getActionCommand());
      str = evt.getActionCommand();
    if (str.equals("srt.cat") && h0 == 0)
    {
      h1 = h2 = h3 = h4 = h5 = 0;
      if (str4.length() > 0)
        ta.replaceRange(" ", 0, str4.length());
      str4 = "srt.cat file contains keywords which are case sensitive\n";
      str4 += "keywords:\n";
      str4 += "STATION lat(deg) lon(west deg) name\n";
      str4 += "AZLIMITS anti-clockwise_viewed_from_above(deg) clockwise(deg)\n";
      str4 += "ELLIMITS lower(deg) upper(deg) - upper limit will be greater" +
      " than 90 degrees as dish goes over or it's back to look north\n";
      str4 += "[COMM serial_port(default = 1) normally 0 for Linux]\n";
      str4 += "[CALCONS initial_ratio_of_degK_per_count - gets changed by" +
      " calibration]\n";
      str4 += "[TLOAD load_temperature(K) - default 300K]\n";
      str4 += "[MANCAL 0 - motor driven vane MANCAL 1 - vane calibration is" +
      " to be done manualy]\n";
      str4 += "[TSPILL antenna_spill_over(K) - default 20K]\n";
      str4 += "[BEAMWIDTH antenna_beamwidth(deg) - default 5 deg]\n";
      str4 += "[AXISTILT az_axis_tilt(deg) el_axis_tilt(deg) - defaults 0 0]\n";
      str4 += "SSAT satellite_ID sync_orbit_satellite(deg_west)\n";
      str4 += "AZEL az(deg) el(deg) name - catalogs a fixed azel position\n";
      str4 += "GALACTIC long(deg) lat(deg) name\n";
      str4 += "SOU ra(hh mm ss) dec(dd mm ss) name [epoc]" +
      " negative declination must have - in front of dd\n";
      str4 += "Sun - add the Sun to the catalog\n";
      str4 += "Moon - add the Moon to the catalog\n";
      str4 += "DIGITAL - if present assumes receiver is digital\n";
      str4 += "NOISECAL temp(K) - calibration temperature of noise diode" +
      " - needs to be present to support electronic noisecal\n";
      str4 += "[TOLERANCE counts - counts of error which can accumulate" +
      " before command to drive [default = 1]]\n";
      str4 += "[COUNTPERSTEP counts - counts per step for stepped" +
      " antenna motion [default = no stepped motion]]\n";
      str4 += "[ELBACKLASH - optional correction for" +
      " elevation backlash to improve pointing in flipped mode [default = 0]]\n";
      str4 += "[CURVATURE - optional correction for" +
      " curvature in spectrum [default = 0]]\n";
      str4 += "[RECORDFORM TAB - adds tabs to separate columns in output" +
      " file - default is space delimited  VLSR adds vlsr and\n";
      str4 += "DAY forces a file change at each new day]\n";
      str4 += "* in first column makes line a comment and items within a " +
      "line following / are comments\n";
      ta.insert(str4, 0);
      h0 = 1;
    }
    if (str.equals("srt.cmd") && h1 == 0)
    {
      h0 = h2 = h3 = h4 = h5 = 0;
      if (str4.length() > 0)
        ta.replaceRange(" ", 0, str4.length());
      str4 =  "command file rules:             (filename srt.cmd)\n";
      str4 += "1] reads one line at a time skipping blank lines,"
        + " and lines which start with * and lines with past times\n";
      str4 += "2] stops at and executes any line with current or future time\n"
        + "time format:\n line starts with yyyy:ddd:hh:mm:ss and is" +
        " followed by cmd\n";
      str4 += "alternate format:   LST:hh:mm:ss   cmd\n"
        + "current time format:   :   cmd\n";
      str4 += "current time plus n seconds format:   :n   cmd\n"
        + "each line MUST start with time or :  or * for comment\n";
      str4 += "command keywords:                items in [ ] are optional\n";
      str4 += "sourcename,mode,radec,azel,galactic,offset,stow,calibrate,"+
              "noisecal,record,freq,roff\n";
      str4 += " sourcename (any name in catalog) [mode]\n";
      str4 += " mode n(for 25_point scan)    b(for beamswitch)\n";
      str4 += " radec hh:mm:ss  [sign]dd:mm:ss [epoch] [mode]\n";
      str4 += " azel az_deg el_deg\n";
      str4 += " galactic glat_deg glon_deg\n";
      str4 += " offset azoff_deg eloff_deg\n";
      str4 += " stow\n";
      str4 += " calibrate (for vane) or noisecal (for noise diode)\n";
      str4 += " record [filename] [recordmode" +
        "(0=normal,1=short,2=add vlsr,3=special,4=summary only)]\n";
      str4 += " roff (turns off record)\n";
      str4 += "short - suppresses recording cmds\n";
      str4 += " freq frequency num [spacing] - for analog receiver\n";
      str4 += " freq frequency digitalmode - for digital receiver\n";
      str4 += "Note that 25_point scan runs only once while beamswitch until next cmd\n";
      str4 += "For example:\n:  Sun\n:60\n:600 Sun n\n:  stow\n";
      str4 += "goes to the sun,waits 60 seconds,does 25 point\n";
      str4 += "and after 600 seconds goes to stow\n";
      str4 += "Note that the seconds to wait is right next to the \":\"\n";
      str4 += "and otherwise there is a space before the command\n";
      str4 += "\n A command file can be checked by running java in simulate mode"
              + "  -see help on cmdline options\n";
      str4 += "another name for a command file can be entered by clicking\n";
      str4 += "on the text area.\n";
      h1 = 1;
      ta.insert(str4, 0);
    }
    if (str.equals("plots") && h2 == 0)
    {
      h0 = h1 = h3 = h4 = h5 = 0;
      if (str4.length() > 0)
        ta.replaceRange(" ", 0, str4.length());
      str4 = "instantaneous spectrum:\n";
      str4 += "black plot on right is the spectrum from the last\n";
      str4 += "reply from the receiver\n";
      str4 += "\naccumulated spectrum:\n";
      str4 += "red plot is the accumulated spectrum with a baseline\n";
      str4 += "constant and slope removed\n";
      str4 += "in beamswith mode the difference in total power from\n";
      str4 += " the sum of all frequencies is given with error estimate\n";
      str4 += "\naccumuated spectra plot:\n";
      str4 += "a separate plot window can capture the accumulated spectrum"
        + " by clicking on the spectrum\n";
      str4 += "when the size of the window is changed a new spectrum is"
        + " captured\n";
      str4 += "the window can be minimized and later restored without"
        + " forcing a new capture\n";
      str4 += "if a new capture is desired - close and reopen the window\n";
      str4 += "\ncontour map:\n";
      str4 += "the last npoint map can be replotted by clicking on the\n";
      str4 += "plot area\n";
      str4 += "\npower vs time:\n";
      str4 += "a \"strip chart\" of the total power is plotted just above\n";
      str4 += "the sky display. The plot offsets modulo the plot scale.\n";
      str4 += "colors change with the offset starting witth black for zero offset\n";
      str4 += "the plot scale default is 400K but can be toggled to 40K\n";
      str4 += "then 4K and back to 400K by clicking on the text\n";
      h2 = 1;
      ta.insert(str4, 0);
    }
    if (str.equals("outputfile") && h3 == 0)
    {
      h0 = h1 = h2 = h4 = h5 = 0;
      if (str4.length() > 0)
        ta.replaceRange(" ", 0, str4.length());
      str4 = "the output file is an ASCII file with spaces or "
        + " optionally tabs as delimiters\n";
      str4 += "the fields are as follows:\n";
      str4 += "field 1 - time (yyyy:ddd:hh:mm:ss)\n";
      str4 += "field 2 - azimuth(deg)\n";
      str4 += "field 3 - elevation(deg)\n";
      str4 += "field 4 - azimuth offset(deg)\n";
      str4 += "field 5 - elevation offset(deg)\n";
      str4 += "field 6 - first frequency(MHz)\n";
      str4 += "field 7 - digital frequency separation(MHz) or first freq " +
                         " for analog receiver\n";
      str4 += "field 8 - digital mode\n";
      str4 += "field 9 - number of frequencies in digital mode\n";
      str4 += "field 10 - first frequency channel for digital receiver\n";
      str4 += "field 11 - second frequency\n";
      str4 += "following fields - continuation of frequency sequence\n";
      str4 += "units are in deg K\n";
      str4 += "last field optionally selected by keyword RECORFORM VLSR " +
              " is the velocity of the local standard of rest VLSR (km/s)\n";
      str4 += "other information is added with * in first column\n";
      str4 += "if the output file doesn't exist it is created in the "
              + "current directory\n";
      str4 += "If the file already exists the data is appended to the "
              + "end of the file\n";
      h3 = 1;
      ta.insert(str4, 0);
    }
    if (str.equals("cmdline") && h4 == 0)
    {
      h0 = h1 = h2 = h3 = h5 = 0;
      if (str4.length() > 0)
        ta.replaceRange(" ", 0, str4.length());
      str4 = "when srt is run from the command prompt\n";
      str4 += "command line options: \n";
      str4 += "srt without arguments to get options and version\n";
      str4 += "srt 0 to run with receiver + antenna\n";
      str4 += "srt 0 1 to simulate antenna\n";
      str4 += "srt 1 to simulate receiver\n";
      str4 += "srt 1 1 to simulate both receiver and antenna\n";
      str4 += "srt 1 10 to simulate and speed-up time x 10\n";
      str4 += "srt 1 -1 to simulate with 1 hour advance\n";
      str4 += "srt 1 0 1 for antenna maintenance\n";
      str4 += "takes incremental commands and doesn't drive to stow\n";
      str4 += "\nSimulation:\n";
      str4 += "in simulation signals are generated for the Sun, Moon\n";
      str4 += "and Cass with strengths of 100K,1K and 2.6K respectively\n";
      str4 += "in addition Gaussian noise is added with the theoretical\n";
      str4 += "amount for each receiver mode\n";
      h4 = 1;
      ta.insert(str4, 0);
    }
    if (str.equals("howto") && h5 == 0)
    {
      h0 = h1 = h2 = h3 = h4 = 0;
      if (str4.length() > 0)
        ta.replaceRange(" ", 0, str4.length());
      str4 = "pointing adjustment:\n";
      str4 += "run npoint on the sun and subtract the azimuth offset"
              + " from the start of the azimuth limit\n";
      str4 += "subtract the elevation offset from the starting elevation"
              + " limit\n";
      str4 += "if needed tilt and backlash corrections can be added\n";
      str4 += "\nchecking receiver communications problems:\n";
      str4 += "if antenna communication doesn't work check this first\n";
      str4 += "make sure receiver is connected - try powering the controller"
              + " down and back up to reboot the receiver\n";
      str4 += "\nchecking antenna communications:\n";
      str4 += "disconnect RS232 cable from controller or turn controller off\n";
      str4 += "srt software should say \"waiting on azimuth\"\n";
      str4 += "disconnect antenna coax and antenna cable\n";
      str4 += "srt software should indicate an immediate timeout in azimuth"
              + " and elevation\n";
      str4 += "\nscreen saver:\n";
      str4 += "the screen saver may cause the srt program to stall\n";
      str4 += "possible solutions are:\n";
      str4 += "a) minimize srt program when not using\n";
      str4 += "b) turn off screen saver\n";
      str4 += "\nerror calculations:\n";
      str4 += "the noise in each spectral bin for a single response from\n";
      str4 += "the receiver (a single line in the output file) is given\n";
      str4 += "by tsys / sqrt(resolution * intg. period)\n";
      str4 += "the resolution for the digital receiver is equal to the spacing.\n";
      str4 += "However in many cases the errors are determined by systematic\n";
      str4 += "effects. For example the errors in a beamswitch continuum observation\n";
      str4 += "are estimated from the variance in the data from many on/offs\n";
      h5 = 1;
      ta.insert(str4, 0);
    }
  }
}
