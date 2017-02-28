# Loadcell

See this video for a brief (albeit out of date) demonstration of system capabilities:
https://www.facebook.com/chris.oaks.12/posts/1059656317459959


Current status:

This software system collects real time EKG (muscle tension) and Loadcell (impact) data and presents it to the user in real time.

Goals (To do list):

Integrate a real time video feed from a suitable high fps camera.
Serialize and save sessions to disk.
Detect local maxima and generate a post session report

As a hobby martial artist I wanted to create a system to conduct strike analysis and present it to the user in real time. If you have the proper hardware this allows you to calculate the impact of your punches or kicks and the exact timing and muscular engagement patterns.

The main current roadblock is finding a suitable high fps camera for the video feed.  This setting requires 60 fps or greater, and I've been working on using the CL-eye camera but there doesn't seem to be a dll compatible with windows 64.

The alternative approach is to use cameras that collect data and record it for later use but this kills the awesomeness of a real time approach.
