public class global
// class to store all items which need to be globally available
// used set_ and get_ to distinguish from java library set get
{
  private double pwr[],
    spec[],
    specd[],
    avspec[],
    avspecc[],
    graycorr[],
    av,
    avc,
    vlsr,
    glat,
    glon,
    freqsep,
    intg,
    bswav,
    bswsq,
    bswlast;
  private double freqa,
    freq0,
    restfreq,
    azoff,
    eloff,
    elcor,
    azcor,
    elback,
    calcons,
    beamw,
    curvcorr,
    pscale,
    azaxis_tilt;
  private double azlim1,
    azlim2,
    ellim1,
    ellim2,
    tsys,
    tload,
    tspill,
    elaxis_tilt;
  private long tstart;
  private double aznow,
    elnow,
    azcmd,
    elcmd,
    fcenter,
    lat,
    lon;
  private double pazoff,
    peloff,
    tilt_az,
    noisecal;
  private int refresh,
    fstatus,
    nfreq,
    radiosim,
    azelsim,
    mainten,
    azcount,
    elcount,
    recmode,
    recform,
    xmark,
    map,
    mancal,
    south,
    ptoler,
    countperstep,
    digital;

  private double ras[],
    decs[],
    epoc[],
    xlast[],
    ylast[],
    gxlast[];
  private int soutype[];
  private double gylast[],
    mx[],
    my[];
  private String sounam[],
    statnam;
  private String ptime = "";
  private String cmdstr = "";
  private String cmdfile = "srt.cmd";
  private String recfile = "";
  private int clr,
    key,
    hlp,
    port,
    drift,
    scan,
    stow,
    slew,
    atten,
    nsou,
    sourn,
    track;
  private int ppos,
    click,
    mclick,
    nclick,
    nsoucat,
    cmdf,
    cmdfline,
    sig,
    bsw,
    calon,
    docal,
    stopproc;
  private int bswcycles,
    comerr,
    comerad,
    nmess;
  {
    pwr = new double[500];
      spec = new double[500];
      specd = new double[64];
      avspec = new double[500];
      avspecc = new double[500];
      graycorr = new double[500];
      ras = new double[500];
      decs = new double[500];
      epoc = new double[500];
      sounam = new String[500];
      xlast = new double[501];
      ylast = new double[501];
      gxlast = new double[500];
      gylast = new double[500];
      mx = new double[20];
      my = new double[20];
      soutype = new int[500];
  }
  public void set_sounam(String name, int index)
  {
    sounam[index] = name;
  }
  public String get_sounam(int index)
  {
    return sounam[index];
  }
  public void set_statnam(String name)
  {
    statnam = name;
  }
  public String get_statnam()
  {
    return statnam;
  }
  public void set_ptime(String name)
  {
    ptime = name;
  }
  public String get_ptime()
  {
    return ptime;
  }
  public void set_cmdstr(String name)
  {
    cmdstr = name;
  }
  public String get_cmdstr()
  {
    return cmdstr;
  }
  public void set_cmdfile(String name)
  {
    cmdfile = name;
  }
  public String get_cmdfile()
  {
    return cmdfile;
  }
  public void set_recfile(String name)
  {
    recfile = name;
  }
  public String get_recfile()
  {
    return recfile;
  }
  public void set_pwr(double power, int index)
  {
    pwr[index] = power;
  }
  public double get_pwr(int index)
  {
    return pwr[index];
  }
  public void set_spec(double power, int index)
  {
    spec[index] = power;
  }
  public double get_spec(int index)
  {
    return spec[index];
  }
  public void set_specd(double power, int index)
  {
    specd[index] = power;
  }
  public double get_specd(int index)
  {
    return specd[index];
  }
  public void set_avspec(double power, int index)
  {
    avspec[index] = power;
  }
  public double get_avspec(int index)
  {
    return avspec[index];
  }
  public void set_avspecc(double power, int index)
  {
    avspecc[index] = power;
  }
  public double get_avspecc(int index)
  {
    return avspecc[index];
  }
  public void set_graycorr(double power, int index)
  {
    graycorr[index] = power;
  }
  public double get_graycorr(int index)
  {
    return graycorr[index];
  }
  public void set_ras(double power, int index)
  {
    ras[index] = power;
  }
  public double get_ras(int index)
  {
    return ras[index];
  }
  public void set_decs(double power, int index)
  {
    decs[index] = power;
  }
  public double get_decs(int index)
  {
    return decs[index];
  }
  public void set_epoc(double power, int index)
  {
    epoc[index] = power;
  }
  public double get_epoc(int index)
  {
    return epoc[index];
  }
  public void set_soutype(int i, int index)
  {
    soutype[index] = i;
  }
  public int get_soutype(int index)
  {
    return soutype[index];
  }
  public void set_xlast(double p, int index)
  {
    xlast[index] = p;
  }
  public double get_xlast(int index)
  {
    return xlast[index];
  }
  public void set_ylast(double p, int index)
  {
    ylast[index] = p;
  }
  public double get_ylast(int index)
  {
    return ylast[index];
  }
  public void set_gylast(double p, int index)
  {
    gylast[index] = p;
  }
  public double get_gylast(int index)
  {
    return gylast[index];
  }
  public void set_gxlast(double p, int index)
  {
    gxlast[index] = p;
  }
  public double get_gxlast(int index)
  {
    return gxlast[index];
  }
  public void set_mx(double p, int index)
  {
    mx[index] = p;
  }
  public double get_mx(int index)
  {
    return mx[index];
  }
  public void set_my(double p, int index)
  {
    my[index] = p;
  }
  public double get_my(int index)
  {
    return my[index];
  }
  public void set_av(double p)
  {
    av = p;
  }
  public double get_av()
  {
    return av;
  }
  public void set_bswav(double p)
  {
    bswav = p;
  }
  public double get_bswav()
  {
    return bswav;
  }
  public void set_bswsq(double p)
  {
    bswsq = p;
  }
  public double get_bswsq()
  {
    return bswsq;
  }
  public void set_bswlast(double p)
  {
    bswlast = p;
  }
  public double get_bswlast()
  {
    return bswlast;
  }
  public void set_vlsr(double p)
  {
    vlsr = p;
  }
  public double get_vlsr()
  {
    return vlsr;
  }
  public void set_glat(double p)
  {
    glat = p;
  }
  public double get_glat()
  {
    return glat;
  }
  public void set_glon(double p)
  {
    glon = p;
  }
  public double get_glon()
  {
    return glon;
  }
  public void set_avc(double p)
  {
    avc = p;
  }
  public double get_avc()
  {
    return avc;
  }
  public void set_freqsep(double p)
  {
    freqsep = p;
  }
  public double get_freqsep()
  {
    return freqsep;
  }
  public void set_intg(double p)
  {
    intg = p;
  }
  public double get_intg()
  {
    return intg;
  }
  public void set_freqa(double p)
  {
    freqa = p;
  }
  public double get_freqa()
  {
    return freqa;
  }
  public void set_freq0(double p)
  {
    freq0 = p;
  }
  public double get_freq0()
  {
    return freq0;
  }
  public void set_restfreq(double p)
  {
    restfreq = p;
  }
  public double get_restfreq()
  {
    return restfreq;
  }
  public void set_azoff(double p)
  {
    azoff = p;
  }
  public double get_azoff()
  {
    return azoff;
  }
  public void set_eloff(double p)
  {
    eloff = p;
  }
  public double get_eloff()
  {
    return eloff;
  }
  public void set_pazoff(double p)
  {
    pazoff = p;
  }
  public double get_pazoff()
  {
    return pazoff;
  }
  public void set_noisecal(double p)
  {
    noisecal = p;
  }
  public double get_noisecal()
  {
    return noisecal;
  }
  public void set_peloff(double p)
  {
    peloff = p;
  }
  public double get_peloff()
  {
    return peloff;
  }
  public void set_elcor(double p)
  {
    elcor = p;
  }
  public double get_elcor()
  {
    return elcor;
  }
  public void set_azcor(double p)
  {
    azcor = p;
  }
  public double get_azcor()
  {
    return azcor;
  }
  public void set_elback(double p)
  {
    elback = p;
  }
  public double get_elback()
  {
    return elback;
  }
  public void set_calcons(double p)
  {
    calcons = p;
  }
  public double get_calcons()
  {
    return calcons;
  }
  public void set_beamw(double p)
  {
    beamw = p;
  }
  public double get_beamw()
  {
    return beamw;
  }
  public void set_curvcorr(double p)
  {
    curvcorr = p;
  }
  public double get_curvcorr()
  {
    return curvcorr;
  }
  public void set_pscale(double p)
  {
    pscale = p;
  }
  public double get_pscale()
  {
    return pscale;
  }
  public void set_azaxis_tilt(double p)
  {
    azaxis_tilt = p;
  }
  public double get_azaxis_tilt()
  {
    return azaxis_tilt;
  }
  public void set_elaxis_tilt(double p)
  {
    elaxis_tilt = p;
  }
  public double get_elaxis_tilt()
  {
    return elaxis_tilt;
  }
  public void set_tilt_az(double p)
  {
    tilt_az = p;
  }
  public double get_tilt_az()
  {
    return tilt_az;
  }
  public void set_azlim1(double p)
  {
    azlim1 = p;
  }
  public double get_azlim1()
  {
    return azlim1;
  }
  public void set_azlim2(double p)
  {
    azlim2 = p;
  }
  public double get_azlim2()
  {
    return azlim2;
  }
  public void set_ellim2(double p)
  {
    ellim2 = p;
  }
  public double get_ellim2()
  {
    return ellim2;
  }
  public void set_ellim1(double p)
  {
    ellim1 = p;
  }
  public double get_ellim1()
  {
    return ellim1;
  }
  public void set_tsys(double p)
  {
    tsys = p;
  }
  public double get_tsys()
  {
    return tsys;
  }
  public void set_tload(double p)
  {
    tload = p;
  }
  public double get_tload()
  {
    return tload;
  }
  public void set_tspill(double p)
  {
    tspill = p;
  }
  public double get_tspill()
  {
    return tspill;
  }
  public void set_tstart(long p)
  {
    tstart = p;
  }
  public long get_tstart()
  {
    return tstart;
  }
  public void set_aznow(double p)
  {
    aznow = p;
  }
  public double get_aznow()
  {
    return aznow;
  }
  public void set_elnow(double p)
  {
    elnow = p;
  }
  public double get_elnow()
  {
    return elnow;
  }
  public void set_azcmd(double p)
  {
    azcmd = p;
  }
  public double get_azcmd()
  {
    return azcmd;
  }
  public void set_elcmd(double p)
  {
    elcmd = p;
  }
  public double get_elcmd()
  {
    return elcmd;
  }
  public void set_fcenter(double p)
  {
    fcenter = p;
  }
  public double get_fcenter()
  {
    return fcenter;
  }
  public void set_lat(double p)
  {
    lat = p;
  }
  public double get_lat()
  {
    return lat;
  }
  public void set_lon(double p)
  {
    lon = p;
  }
  public double get_lon()
  {
    return lon;
  }
  public void set_refresh(int p)
  {
    refresh = p;
  }
  public int get_refresh()
  {
    return refresh;
  }
  public void set_fstatus(int p)
  {
    fstatus = p;
  }
  public int get_fstatus()
  {
    return fstatus;
  }
  public void set_nfreq(int p)
  {
    nfreq = p;
  }
  public int get_nfreq()
  {
    return nfreq;
  }
  public void set_radiosim(int p)
  {
    radiosim = p;
  }
  public int get_radiosim()
  {
    return radiosim;
  }
  public void set_azelsim(int p)
  {
    azelsim = p;
  }
  public int get_azelsim()
  {
    return azelsim;
  }
  public void set_mainten(int p)
  {
    mainten = p;
  }
  public int get_mainten()
  {
    return mainten;
  }
  public void set_azcount(int p)
  {
    azcount = p;
  }
  public int get_azcount()
  {
    return azcount;
  }
  public void set_elcount(int p)
  {
    elcount = p;
  }
  public int get_elcount()
  {
    return elcount;
  }
  public void set_mancal(int p)
  {
    mancal = p;
  }
  public int get_mancal()
  {
    return mancal;
  }
  public void set_south(int p)
  {
    south = p;
  }
  public int get_south()
  {
    return south;
  }
  public void set_ptoler(int p)
  {
    ptoler = p;
  }
  public int get_ptoler()
  {
    return ptoler;
  }
  public void set_countperstep(int p)
  {
    countperstep = p;
  }
  public int get_countperstep()
  {
    return countperstep;
  }
  public void set_digital(int p)
  {
    digital = p;
  }
  public int get_digital()
  {
    return digital;
  }
  public void set_recmode(int p)
  {
    recmode = p;
  }
  public int get_recmode()
  {
    return recmode;
  }
  public void set_recform(int p)
  {
    recform = p;
  }
  public int get_recform()
  {
    return recform;
  }
  public void set_xmark(int p)
  {
    xmark = p;
  }
  public int get_xmark()
  {
    return xmark;
  }
  public void set_map(int p)
  {
    map = p;
  }
  public int get_map()
  {
    return map;
  }
  public void set_docal(int p)
  {
    docal = p;
  }
  public int get_docal()
  {
    return docal;
  }
  public void set_stopproc(int p)
  {
    stopproc = p;
  }
  public int get_stopproc()
  {
    return stopproc;
  }
  public void set_click(int p)
  {
    click = p;
  }
  public int get_click()
  {
    return click;
  }
  public void set_mclick(int p)
  {
    mclick = p;
  }
  public int get_mclick()
  {
    return mclick;
  }
  public void set_nclick(int p)
  {
    nclick = p;
  }
  public int get_nclick()
  {
    return nclick;
  }
  public void set_ppos(int p)
  {
    ppos = p;
  }
  public int get_ppos()
  {
    return ppos;
  }
  public void set_clr(int p)
  {
    clr = p;
  }
  public int get_clr()
  {
    return clr;
  }
  public void set_key(int p)
  {
    key = p;
  }
  public int get_key()
  {
    return key;
  }
  public void set_hlp(int p)
  {
    hlp = p;
  }
  public int get_hlp()
  {
    return hlp;
  }
  public void set_port(int p)
  {
    port = p;
  }
  public int get_port()
  {
    return port;
  }
  public void set_drift(int p)
  {
    drift = p;
  }
  public int get_drift()
  {
    return drift;
  }
  public void set_scan(int p)
  {
    scan = p;
  }
  public int get_scan()
  {
    return scan;
  }
  public void set_stow(int p)
  {
    stow = p;
  }
  public int get_stow()
  {
    return stow;
  }
  public void set_slew(int p)
  {
    slew = p;
  }
  public int get_slew()
  {
    return slew;
  }
  public void set_atten(int p)
  {
    atten = p;
  }
  public int get_atten()
  {
    return atten;
  }
  public void set_nsou(int p)
  {
    nsou = p;
  }
  public int get_nsou()
  {
    return nsou;
  }
  public void set_sourn(int p)
  {
    sourn = p;
  }
  public int get_sourn()
  {
    return sourn;
  }
  public void set_track(int p)
  {
    track = p;
  }
  public int get_track()
  {
    return track;
  }
  public void set_nsoucat(int p)
  {
    nsoucat = p;
  }
  public int get_nsoucat()
  {
    return nsoucat;
  }
  public void set_cmdf(int p)
  {
    cmdf = p;
  }
  public int get_cmdf()
  {
    return cmdf;
  }
  public void set_cmdfline(int p)
  {
    cmdfline = p;
  }
  public int get_cmdfline()
  {
    return cmdfline;
  }
  public void set_sig(int p)
  {
    sig = p;
  }
  public int get_sig()
  {
    return sig;
  }
  public void set_bsw(int p)
  {
    bsw = p;
  }
  public int get_bsw()
  {
    return bsw;
  }
  public void set_bswcycles(int p)
  {
    bswcycles = p;
  }
  public int get_bswcycles()
  {
    return bswcycles;
  }
  public void set_comerr(int p)
  {
    comerr = p;
  }
  public int get_comerr()
  {
    return comerr;
  }
  public void set_comerad(int p)
  {
    comerad = p;
  }
  public int get_comerad()
  {
    return comerad;
  }
  public void set_nmess(int p)
  {
    nmess = p;
  }
  public int get_nmess()
  {
    return nmess;
  }
  public void set_calon(int p)
  {
    calon = p;
  }
  public int get_calon()
  {
    return calon;
  }
}
