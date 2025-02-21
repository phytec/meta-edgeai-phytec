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

v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=0,auto_exposure=1

gst-launch-1.0 v4l2src device=/dev/video-csi0 io-mode=dmabuf-import ! \
video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT}  ! \
queue ! tiovxisp sink_0::device=/dev/cam-csi0 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin \
sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
video/x-raw,format=NV12,width=${WIDTH},height=${HEIGHT} ! \
tiovxmemalloc pool-size=12 ! \
tiovxmultiscaler name=split_01 src_0::roi-startx=0 src_0::roi-starty=0 src_0::roi-width=1280 src_0::roi-height=800 target=0 \
split_01. ! queue ! video/x-raw, width=416, height=416 ! tiovxdlpreproc model=/opt/model_zoo/ONR-OD-8200-yolox-nano-lite-mmdet-coco-416x416  out-pool-size=4 ! \
application/x-tensor-tiovx ! tidlinferer target=1  model=/opt/model_zoo/ONR-OD-8200-yolox-nano-lite-mmdet-coco-416x416 ! post_0.tensor \
split_01. ! queue ! video/x-raw, width=1280, height=800 ! post_0.sink \
tidlpostproc name=post_0 model=/opt/model_zoo/ONR-OD-8200-yolox-nano-lite-mmdet-coco-416x416 alpha=0.200000 viz-threshold=0.600000 top-N=5 ! queue ! mosaic_0. \
tiovxmosaic name=mosaic_0 target=1 src::pool-size=4 \
sink_0::startx="<0>" sink_0::starty="<0>" sink_0::widths="<1280>" sink_0::heights="<800>" \
! video/x-raw,format=NV12, width=1280, height=800 ! queue ! tiperfoverlay title="Phycam VM-016 Object Detection" ! kmssink driver-name=tidss sync=true connector-id=40
