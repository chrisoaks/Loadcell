# This was a rough python prototype of the concept prior to porting everything over to JavaFX
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
