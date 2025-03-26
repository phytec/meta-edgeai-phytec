#!/bin/sh

HW_ARCH=`uname --hardware-platform`
if [ "${HW_ARCH}" = "aarch64" ]; then
	echo "You are likely running this on the target. This script is meant to be run on your host. Copy it there."
	exit
fi
gst-launch-1.0 udpsrc port=8081 ! application/x-rtp,media=video,clock-rate=90000,encoding-name=H264,payload=96 ! \
rtph264depay ! h264parse ! queue leaky=2 ! avdec_h264 ! queue leaky=2 ! videoconvert ! autovideosink sync=false
