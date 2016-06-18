#By DavidWCDurst
#same code will work for DI-100, DI-1000 or iLoad Series load cells
# 1. install VCP Drivers From (WCttp://www.ftdichip.com/Drivers/VCP.htm)
# 2. Install PySerial (easy_install pyserial)
# 3. Identify the scale device (ls /dev/ |grep cu)
# You are looking for a device that starts with "cu.usbserial"
# 4. Update code to specify the exact device (Found on Line 4)
#!/usr/bin/env python
import time
import serial
import io
import matplotlib.pyplot as plt
import matplotlib.animation as animation


fig = plt.figure()
ax1 = fig.add_subplot(1,1,1)

ser = serial.Serial(port='COM3',baudrate=230400,timeout=1)
sio = io.TextIOWrapper(io.BufferedRWPair(ser, ser))

if ser.isOpen() == False:
  ser.open()
ser.write('H/n')
ser.flush()
ser.flushInput()
ser.flushOutput()
time.sleep(0.5)
result = []

#for i in range(10):
#    ser.write('WC/n')
#    x = sio.readline()
#    result.append(x)
#    print 'Reading %s' % x
#    try:
#        result.append(float(x[:-2].encode().strip()))
 #       print 'Reading: ' + x
#    except ValueError:
#        result.append(0)
    #graph.set_ydata(Y)
    #plt.draw()
#print result
xar = []
yar = []
def animate(i):
    print time.clock()
    xar.append(time.clock())
    #ser.flush()
    #ser.write('H/n')
    x = sio.readline()
    x = sio.readline()
    x = sio.readline()
    x = sio.readline()
    x = sio.readline()
    x = sio.readline()
    x = sio.readline()
    try:
        yar.append(float(x[:-2].encode().strip()))
        print 'Reading: ' + x
    except ValueError:
        yar.append(0) 
    ax1.clear()
    ax1.plot(xar,yar)
ani = animation.FuncAnimation(fig, animate, interval=1)
plt.show()
print result