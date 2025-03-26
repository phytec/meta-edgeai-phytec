#!/bin/sh

BITS="12"
MBUS_FMT="SGRBG${BITS}_1X${BITS}"
setup-pipeline-csi0 -f ${MBUS_FMT}

BIN_PATH="/opt/imaging/ar0144_${BITS}bit/linear/"
SENSOR_NAME="SENSOR_AR0144_PHYCAM"
FMT="grbg${BITS}"
FMT_MSB="$((${BITS}-1))"
WIDTH="1280"
HEIGHT="800"

LOCAL_SINK="kmssink driver-name=tidss sync=true connector-id=40"
REMOTE_SINK="v4l2h264enc ! rtph264pay ! udpsink host=localhost port=8081 host=192.168.3.10"

v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=0,auto_exposure=1

gst-launch-1.0 v4l2src device=/dev/video-csi0 io-mode=dmabuf-import ! \
video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT},framerate=607/10 ! \
tiovxisp sink_0::device=/dev/cam-csi0 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin \
sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT},framerate=607/10 ! \
${LOCAL_SINK}
