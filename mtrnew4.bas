dirs = %00000000
LOW 7
LOW 6
LOW 5
LOW 2

Main:
w2 = 0
SERIN 0,N2400,("move"),#b8,#w5
'b8=2
'w5=100
'b8=1
'debug b8,w5
BRANCH b8, (M1CW,M1CCW,M2CW,M2CCW,CALOUT,CALIN,NCALOFF,NCALON)
SEROUT 1,N2400,("E"," ",#b8," ",#w5,13)
dir1 = 0                                       'open output
GOTO Main

Pulse1:

PULSIN 3,1,w0

	IF w0=0 THEN Timeout
		w2=w2+1
IF w2<w5 THEN Pulse1
GOTO MOFF


Pulse2:

PULSIN 4,1,w0
	IF w0=0 THEN Timeout
		w2=w2+1
IF w2<w5 THEN Pulse2
GOTO MOFF

MOFF:

  w5=0
  LOW 7
  LOW 6
  LOW 5
  SEROUT 1,N2400,("M ",#w2," ",#w5," ",#b8,13)
  dir1 = 0
  GOTO Main

M2CW:

  LOW 6
  high 5
  GOTO Pulse2

M2CCW:

  HIGH 5
  high 6
  GOTO Pulse2

M1CW:

  LOW 6
  high 7
  GOTO Pulse1

M1CCW:

  HIGH 6
  high 7
  GOTO Pulse1


Timeout:

  pause 50
  SEROUT 1,N2400,("T","  ",#w2," ",#b8," ",#w5,13)
  dir1 = 0
  w5=0
  LOW 7
  LOW 6
  LOW 5
  GOTO Main

CALOUT:

 high 6
 high 2
 pause 20000
 SEROUT 1,N2400,("O"," ",#w2," ",#b8," ",#b5,13)
 dir1=0
 low 2
 goto Main

CALIN:

 low 6
 high 2
 pause 20000
 SEROUT 1,N2400,("I"," ",#w2," ",#b8," ",#b5,13)
 dir1=0
 low 2
 goto Main

NCALOFF:

 low 2
 SEROUT 1,N2400,("I"," ",#w2," ",#b8," ",#b5,13)
 dir1=0
 goto Main

 NCALON:

 high 6
 high 2
 SEROUT 1,N2400,("I"," ",#w2," ",#b8," ",#b5,13)
 dir1=0
 goto Main
