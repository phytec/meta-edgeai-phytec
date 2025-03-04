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

v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=0,auto_exposure=0,horizontal_blanking=2055
v4l2-ctl -d /dev/cam-csi1 -c autogain_analogue=0,auto_exposure=0,horizontal_blanking=2055

# KI_PIPELINE="tiovxmultiscaler name=split_left src_0::roi-startx=0 src_0::roi-starty=0 src_0::roi-width=${WIDTH} src_0::roi-height=${HEIGHT} target=0 \
# split_left. ! queue ! video/x-raw, width=640, height=640 ! tiovxdlpreproc model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640  out-pool-size=4 ! \
# application/x-tensor-tiovx ! tidlinferer target=1  model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640 ! post_left.tensor \
# split_left. ! queue ! video/x-raw, width=${WIDTH}, height=${HEIGHT} ! post_left.sink \
# tidlpostproc name=post_left model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640 alpha=0.200000 viz-threshold=0.600000 top-N=5 display-model=true ! queue ! \"
KI_PIPELINE=""

GST_DEBUG_FILE=trace.log GST_DEBUG=2,*tiovxisp*:6 gst-launch-1.0 \
v4l2src device=/dev/video-csi0 io-mode=dmabuf-import ! queue leaky=2 ! video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT} ! \
tiovxisp sink_0::device=/dev/cam-csi0 sensor-name=${SENSOR_NAME} dcc-isp-file=${BIN_PATH}dcc_viss.bin sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT} ! tee name=cam_left \
cam_left.src_0 ! stereo.left_sink \
cam_left.src_1 ! ${KI_PIPELINE} mosaic.sink_0 \
v4l2src device=/dev/video-csi1 io-mode=dmabuf-import ! queue leaky=2 ! video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT} ! \
tiovxisp sink_0::device=/dev/cam-csi1 sensor-name=${SENSOR_NAME} dcc-isp-file=${BIN_PATH}dcc_viss.bin sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT} ! tee name=cam_right \
cam_right.src_0 ! stereo.right_sink \
cam_right.src_1 ! mosaic.sink_1 \
tiovxsde name=stereo ! tiovxsdeviz ! ticolorconvert ! queue ! video/x-raw,format=NV12 ! mosaic.sink_2 \
tiovxmosaic name=mosaic sink_1::starty="<${HEIGHT}>" sink_2::startx="<${WIDTH}>" ! \
video/x-raw,format=NV12,width=$((WIDTH*2)),height=$((HEIGHT*2)) ! queue ! tiperfoverlay title="Phycam VM-016 Disparity" ! \
${REMOTE_SINK}
