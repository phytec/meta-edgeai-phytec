# Produces wic Image for Edge AI demos

require recipes-core/images/tisdk-default-image.bb

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

EDGEAI_STACK = " \
        ti-vision-apps-dev \
        ti-edgeai-firmware \
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

IMAGE_INSTALL:append = " \
    ${EDGEAI_STACK} \
    packagegroup-arago-gst-sdk-target \
"
# disable matrix gui for PSDKLA
IMAGE_INSTALL:remove = "\
    packagegroup-arago-tisdk-matrix \
    packagegroup-arago-tisdk-matrix-extra \
"

WKS_FILE = "tisdk-edgeai-sdimage.wks"
WIC_CREATE_EXTRA_ARGS += " --no-fstab-update"

do_image_wic[depends] += "edgeai-uenv:do_deploy"
IMAGE_BOOT_FILES:remove = "uEnv.txt"
IMAGE_BOOT_FILES:append = " uEnv_edgeai-apps.txt;uEnv.txt "

# For AM68-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES:remove:j721s2-evm = "tiboot3.bin"
IMAGE_BOOT_FILES:append:j721s2-evm = " tiboot3-j721s2-gp-evm.bin tiboot3-j721s2-hs-fs-evm.bin tiboot3-j721s2-hs-fs-evm.bin;tiboot3.bin"

# For AM69-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES:remove:j784s4-evm = "tiboot3.bin"
IMAGE_BOOT_FILES:append:j784s4-evm = " tiboot3-j784s4-gp-evm.bin tiboot3-j784s4-hs-fs-evm.bin tiboot3-j784s4-hs-fs-evm.bin;tiboot3.bin"

export IMAGE_BASENAME = "tisdk-edgeai-image"

PR:append = "_edgeai_0"
