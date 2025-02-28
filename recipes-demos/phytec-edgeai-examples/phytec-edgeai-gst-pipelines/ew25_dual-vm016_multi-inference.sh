#!/bin/sh
ulimit -Sn 10240                                                       
ulimit -Hn 40960 
export XDG_RUNTIME_DIR=/run/user/0
export WAYLAND_DISPLAY=/run/wayland-0
export XDG_SESSION_ID=c2

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
                                                                       
v4l2-ctl -d /dev/cam-csi0 -c autogain_analogue=0,auto_exposure=0,horizontal_blanking=1755
v4l2-ctl -d /dev/cam-csi1 -c autogain_analogue=0,auto_exposure=0,horizontal_blanking=1755 

gst-launch-1.0 -e \
v4l2src device=/dev/video-csi0 io-mode=5 ! queue ! video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT} ! \
queue ! tiovxisp sink_a::device=/dev/cam-csi0 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12 ! \
tiovxmultiscaler name=split_01 src_0::roi-startx=0 src_0::roi-starty=0 src_0::roi-width=1280 src_0::roi-height=800 \
split_01. ! queue ! video/x-raw, width=640, height=640 ! tiovxdlpreproc model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640  out-pool-size=4 ! application/x-tensor-tiovx ! \
tidlinferer model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640 ! post_0.tensor \
split_01. ! queue ! video/x-raw, width=1280, height=800 ! post_0.sink \
tidlpostproc name=post_0 model=/opt/model_zoo/ONR-KD-7060-human-pose-yolox-s-640x640 alpha=0.200000 viz-threshold=0.600000 top-N=5 display-model=true ! queue ! mosaic.sink_0 \
\
v4l2src device=/dev/video-csi1 io-mode=5 ! queue ! video/x-bayer,format=${FMT},width=${WIDTH},height=${HEIGHT} ! \
queue ! tiovxisp sink_b::device=/dev/cam-csi1 sensor-name=${SENSOR_NAME} \
dcc-isp-file=${BIN_PATH}dcc_viss.bin sink_0::dcc-2a-file=${BIN_PATH}dcc_2a.bin format-msb=${FMT_MSB} ! \
queue ! video/x-raw,format=NV12 ! \
tiovxmultiscaler name=split_11 src_0::roi-startx=0 src_0::roi-starty=0 src_0::roi-width=1280 src_0::roi-height=800 \
\
split_11. ! queue ! video/x-raw, width=640, height=640 ! tiovxdlpreproc model=/opt/model_zoo/ONR-OD-8420-yolox-s-lite-mmdet-widerface-640x640  out-pool-size=4 ! \
application/x-tensor-tiovx ! tidlinferer model=/opt/model_zoo/ONR-OD-8420-yolox-s-lite-mmdet-widerface-640x640 ! post_1.tensor \
split_11. ! queue ! video/x-raw, width=1280, height=800 ! post_1.sink \
tidlpostproc name=post_1 model=/opt/model_zoo/ONR-OD-8420-yolox-s-lite-mmdet-widerface-640x640 alpha=0.200000 viz-threshold=0.500000 top-N=5 display-model=true ! queue ! mosaic.sink_1 \
tiovxmosaic src::pool-size=6 name=mosaic latency=10000000 \
sink_0::startx="<0>" sink_0::starty="<0>" sink_0::widths="<1280>" sink_0::heights="<800>" \
sink_1::startx=0 sink_1::starty="<1000>" sink_1::widths="<1280>" sink_1::heights="<800>" ! \
queue ! tiperfoverlay main-title="phyCORE-AM68x" title="Dual Camera Multi Inference" ! waylandsink fullscreen=true sync=false
