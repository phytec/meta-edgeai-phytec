# Produces wic Image for Edge AI demos

require recipes-core/images/tisdk-default-image.bb
require recipes-core/images/tisdk-default-image-append.inc

COMPATIBLE_MACHINE = "j7-evm|j7-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

EDGEAI_STACK = " \
        ti-tisdk-firmware-dev \
        ti-tidl-dev \
        ti-tidl-osrt-dev \
        ti-tidl-osrt-staticdev \
        edgeai-gst-apps \
        edgeai-init \
        edgeai-gui-app \
        ti-gpio-cpp \
        ti-gpio-py \
        edgeai-studio-agent \
        edgeai-tiovx-kernels-dev \
        edgeai-tiovx-modules-dev \
        edgeai-gst-plugins-dev \
        edgeai-dl-inferer-staticdev \
        edgeai-gst-apps-source \
        edgeai-gst-plugins-source \
        edgeai-tiovx-kernels-source \
        edgeai-tiovx-modules-source \
        edgeai-apps-utils-source \
        edgeai-dl-inferer-source \
        ti-gpio-cpp-source \
        ti-gpio-py-source \
"

IMAGE_INSTALL_append = " \
	${EDGEAI_STACK} \
"

do_image_wic[depends] += "edgeai-uenv:do_deploy"
IMAGE_BOOT_FILES_remove = "uEnv.txt"
IMAGE_BOOT_FILES_append = " uEnv_edgeai-apps.txt;uEnv.txt "

# For AM68-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES_remove_j721s2-evm = "tiboot3.bin"
IMAGE_BOOT_FILES_append_j721s2-evm = " tiboot3-j721s2-gp-evm.bin tiboot3-j721s2-hs-fs-evm.bin tiboot3-j721s2-hs-fs-evm.bin;tiboot3.bin"

# For AM69-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES_remove_j784s4-evm = "tiboot3.bin"
IMAGE_BOOT_FILES_append_j784s4-evm = " tiboot3-j784s4-gp-evm.bin tiboot3-j784s4-hs-fs-evm.bin tiboot3-j784s4-hs-fs-evm.bin;tiboot3.bin"

export IMAGE_BASENAME = "tisdk-edgeai-image"

PR_append = "_edgeai_0"
