#!/bin/sh

HW_ARCH=`uname --hardware-platform`
if [ "${HW_ARCH}" = "aarch64" ]; then
	echo "You are likely running this on the target. This script is meant to be run on your host. Copy it there."
	exit
fi

UDP_PORT=8081
if [ -n "$1" ] && [ $1 -ge 1024 ]; then
	UDP_PORT=$1
	echo "Listen for stream on port ${UDP_PORT}"
else
	echo "Listen on default port ${UDP_PORT}"
fi

gst-launch-1.0 udpsrc port=${UDP_PORT} ! application/x-rtp,media=video,clock-rate=90000,encoding-name=H264,payload=96 ! \
rtph264depay ! h264parse ! queue leaky=2 ! avdec_h264 ! queue leaky=2 ! videoconvert ! autovideosink sync=false
