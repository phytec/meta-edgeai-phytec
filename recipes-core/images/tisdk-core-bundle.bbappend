PR:append = "_edgeai_0"

EDGEAI_SRCS = " \
    edgeai-apps-utils-src \
    edgeai-dl-inferer-src \
    edgeai-gst-apps-src \
    edgeai-gst-plugins-src \
    edgeai-tiovx-kernels-src \
    edgeai-tiovx-modules-src \
"

# Add EdgeAI Sources to SDK installer
IMAGE_INSTALL:append = " \
   ${EDGEAI_SRCS}  \
"

TARGET_IMAGES = "tisdk-edgeai-image"
