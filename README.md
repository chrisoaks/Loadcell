# Loadcell

The goal:

A standalone application that can read sensors, display data in a couple graphical ways, and store/save the results.

A sensor processing program that combines data from three sources:
* video
* load cell
* EKG sensors

This display needs to be synchronized, scrollable, zoomable.

The usage works as follows: video cameras, loadcell sensors, and ekg sensors will all simultaneously be running.  A person will punch the loadcell while they are hooked up to the ekg sensors.  This will be caught on camera.  This leads to three time domain information: video, muscle tension, and force output.

All of this will be interpreted by the software and displayed graphically.  The video will be on top, and underneath will be a scroll where you can move forward and backward in time.  Under this will be the ekg data and the loadcell data, both displayed as a function of time.  


There must be a secondary display mode -> in this mode the data from many different strikes is displayed on a scatterplot to show things like speed and power tradeoffs.  There would be no video in this mode, unless clicking on any of the dots on the scatterplot could queue the video from that strike.

The data needs to be serialized and saved to disk.  And able to be opened again later.

There are a couple other concerns: live stream data vs saved data -- currently I have one webcam (live) and many cameras that can only record to SD card.  If we use these cameras, we would have to find a way to time synchronize their information with the information from the other sensors.  And if we are going the route of reading in stored data, then it would simplify things in other ways -- I wouldn't have to figure out how to live read the USB data from the loadcell -- I coud just use exported data from the software that came with the device.
