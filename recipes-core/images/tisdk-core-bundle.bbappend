PR:append = "_edgeai_0"

EDGEAI_SRCS = " \
    edgeai-apps-utils-src \
    edgeai-dl-inferer-src \
    edgeai-gst-apps-src \
    edgeai-gst-plugins-src \
    edgeai-tiovx-kernels-src \
    edgeai-tiovx-modules-src \
"

DEPLOY_IMAGES_NAME:append = " ipc_echo_testb_mcu1_0_release_strip.xer5f"

# Add EdgeAI Sources to SDK installer
IMAGE_INSTALL:append = " \
   ${EDGEAI_SRCS}  \
"

TARGET_IMAGES:edgeai = "tisdk-edgeai-image"
TARGET_IMAGES:adas = "tisdk-adas-image"
