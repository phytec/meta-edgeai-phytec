#!/bin/sh

CSI="0"

setup-pipeline-csi -i ${CSI}

WIDTH="1280"
HEIGHT="800"

LOCAL_SINK="kmssink driver-name=tidss sync=true connector-id=40"
REMOTE_SINK="v4l2h264enc ! rtph264pay ! udpsink host=localhost port=8081 host=192.168.3.10"

v4l2-ctl -d /dev/cam-csi${CSI} -c autogain_analogue=1,auto_exposure=0

gst-launch-1.0 v4l2src device=/dev/video-csi${CSI} ! \
video/x-bayer,format=grbg,width=${WIDTH},height=${HEIGHT} ! \
bayer2rgb ! \
${LOCAL_SINK}
