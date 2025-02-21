#!/bin/sh

setup-pipeline-csi0

WIDTH="1280"
HEIGHT="800"

v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=1,auto_exposure=0

gst-launch-1.0 v4l2src device=/dev/video-csi0 ! \
video/x-bayer,format=grbg,width=${WIDTH},height=${HEIGHT} ! \
bayer2rgb ! \
kmssink driver-name=tidss sync=false connector-id=40
