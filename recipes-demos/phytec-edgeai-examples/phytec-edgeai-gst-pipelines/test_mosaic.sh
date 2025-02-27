#!/bin/sh

BITS="12"
MBUS_FMT="SGRBG${BITS}_1X${BITS}"
setup-pipeline-csi0 -f ${MBUS_FMT}
setup-pipeline-csi1 -f ${MBUS_FMT}
BIN_PATH="/opt/imaging/ar0144_${BITS}bit/linear/"
SENSOR_NAME="SENSOR_AR0144_PHYCAM"
FMT="grbg${BITS}"
FMT_MSB="$((${BITS}-1))"
WIDTH="1280"
HEIGHT="800"

WAYLAND_SINK="waylandsink fullscreen=true"
REMOTE_SINK="v4l2h264enc ! rtph264pay ! udpsink host=localhost port=8081 host=192.168.3.10"

v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=0,auto_exposure=1,horizontal_blanking=2055
v4l2-ctl -d /dev/cam-csi1 -c autogain_analogue=0,auto_exposure=1,horizontal_blanking=2055

GST_DEBUG_FILE=trace.log GST_DEBUG=2,v4l2*:6,*kms*:6,*tiovx*:6 gst-launch-1.0 \
v4l2src device=/dev/video-csi0 io-mode=dmabuf-import ! queue leaky=2 ! video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT} ! \
queue ! tiovxisp sink_a::device=/dev/cam-csi0 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin \
sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT} ! mosaic.sink_0 \
v4l2src device=/dev/video-csi1 io-mode=dmabuf-import ! queue leaky=2 ! video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT} ! \
queue ! tiovxisp sink_b::device=/dev/cam-csi1 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin \
sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT} ! mosaic.sink_1 \
tiovxmosaic name=mosaic sink_1::starty="<${HEIGHT}>" ! \
video/x-raw,format=NV12,width=${WIDTH},height=$((HEIGHT*2)) ! queue ! \
${REMOTE_SINK}

## side-by-side
# tiovxmosaic name=mosaic sink_1::startx="<${WIDTH}>" ! \
# video/x-raw,format=NV12,width=$((WIDTH*2)),height=${HEIGHT} ! queue ! \

# timosaic name=mosaic \
# sink_0::startx=0 sink_0::starty=0 \
# sink_1::startx=${WIDTH} sink_1::starty=0 ! \
