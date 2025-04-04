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

v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=0,auto_exposure=1,horizontal_blanking=1755

gst-launch-1.0 v4l2src device=/dev/video-csi0 io-mode=dmabuf-import ! \
video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT}  ! \
queue ! tiovxisp sink_0::device=/dev/cam-csi0 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin \
sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT} ! \
tiovxmultiscaler name=split_01 src_0::roi-startx=0 src_0::roi-starty=0 src_0::roi-width=${WIDTH} src_0::roi-height=${HEIGHT} target=0 \
split_01. ! queue ! video/x-raw, width=640, height=640 ! tiovxdlpreproc model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640  out-pool-size=4 ! application/x-tensor-tiovx ! tidlinferer target=1  model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640 ! post_0.tensor \
split_01. ! queue ! video/x-raw, width=${WIDTH}, height=${HEIGHT} ! post_0.sink \
tidlpostproc name=post_0 model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640 alpha=0.200000 viz-threshold=0.600000 top-N=5 display-model=true ! queue ! mosaic_0. \
tiovxmosaic name=mosaic_0 target=1 src::pool-size=4 \
sink_0::startx="<0>" sink_0::starty="<0>" sink_0::widths="<${WIDTH}>" sink_0::heights="<${HEIGHT}>" \
! video/x-raw,format=NV12, width=${WIDTH}, height=${HEIGHT} ! queue ! tiperfoverlay title="Phycam VM-016 Keypoint Detection" ! \
${LOCAL_SINK}
