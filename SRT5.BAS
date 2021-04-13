' pin0 = listens for ASCII from ground 
' pin1 = sends ASCII
' pin2 = syn data
' pin3 = syn clock
' pin4 = syn enable
' pin5 = reads pulse length from V/F converter
' pin6 = reads L.O. lock

      dirs = %00011110                             'initial I/O set
      pin4 = 1                                     'disable syn cmd
      pin1 = 0                                     'pin 1 low 
start: serin 0,t2400,("freq"),b11,b10,b9,b8   'wait for command
                                                   'b11 = syn A upper 3bits
                                                   'b10 = syn A next byte
                                                   'b9 = syn A lower byte
                                                   'b8 = mode
           'i.e. freq = (b11*256+b10)*64+b9)*10/250  MHz
           'ref divider set to 200 = 40 kHz   8 MHz xtal    
       pin4 = 0
       pin2=0:                                  'set ref divider
       for b0=1 to 7: pulsout 3,1: next
       pin2=1: pulsout 3,1: pulsout 3,1         '%00000001 R reg upper byte
       pin2=0: pulsout 3,1: pulsout 3,1         '%10010001 R reg lower byte
       pin2=1: pulsout 3,1
       pin2=0: pulsout 3,1: pulsout 3,1: pulsout 3,1
       pin2=1: pulsout 3,1
       pin4=1
        b0=b11*32: pin4=0: b3=3: gosub syn           'set upper 3 bits
        b0=b10
        b3=8: gosub syn                             'A reg next 8 bits
        b0=b9*2: b3=7: gosub syn                     'A reg next 7 bits
        pin2=0: pulsout 3,1: pin4=1                 'A control bit
        w1 = 0:                                     'set sum to zero
        w2 = 0:                                 'set num pulses to zer
        pause 5                                     'wait for lock
pulse:  pulsin 5,1,w0                               'measure pulse length
        if w0=0 then send                           'skip if timeout
        w2 = w2 + 1                                 'count number of reads
        w1 = w1 + w0 max 65000                    'add to sum limit to max
        if w1 < 10000 then pulse                    'repeat
                                                    'return power data
send: serout 1,n2400,(b4,b3,b2)
      goto start                                    'wait for next cmd

syn:   for b2=1 to b3                               'loop for b3 bits
       pin2=bit7                                    'msb of b0
       pulsout 3,1                                  'pulse clock line
       b0=b0*2                                      'left shift one bit
       next
       return 
