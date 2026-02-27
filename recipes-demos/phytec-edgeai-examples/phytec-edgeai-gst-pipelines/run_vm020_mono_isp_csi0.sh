#!/bin/sh

CSI="0"
BITS="10"
MBUS_FMT="Y${BITS}_1X${BITS}"
setup-pipeline-csi -i ${CSI} -f ${MBUS_FMT}
if [ "$?" = '1' ]; then
	echo "Error on pipeline setup!"; exit 1
fi

BIN_PATH="/opt/imaging/ar0234/10bit/mono/"
SENSOR_NAME="SENSOR_AR0234_PHYCAM"
FMT="grbg${BITS}"
FMT_MSB="$((${BITS}-1))"
WIDTH="1920"
HEIGHT="1200"

LOCAL_SINK="kmssink driver-name=tidss sync=true connector-id=40"
REMOTE_SINK="v4l2h264enc ! rtph264pay ! udpsink port=8081 host=192.168.3.10"

v4l2-ctl -d /dev/cam-csi${CSI} -c autogain_analogue=0,auto_exposure=0

gst-launch-1.0 v4l2src device=/dev/video-csi${CSI} io-mode=dmabuf-import ! \
video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT},framerate=1203/10 ! \
tiovxisp sink_0::device=/dev/cam-csi${CSI} sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin \
sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT},framerate=1203/10 ! \
${REMOTE_SINK}
