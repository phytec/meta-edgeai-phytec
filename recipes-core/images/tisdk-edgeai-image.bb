# Produces wic Image for Edge AI demos

require recipes-core/images/tisdk-default-image.bb

PN:adas = "tisdk-adas-image"

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
        edgeai-test-data \
        edgeai-tidl-models \
        ti-gpio-cpp-source \
        ti-gpio-py-source \
"

IMAGE_INSTALL:append = " \
    ${EDGEAI_STACK} \
    packagegroup-arago-gst-sdk-target \
    packagegroup-edgeai-tisdk-addons \
"
# disable matrix gui for PSDKLA
IMAGE_INSTALL:remove = "\
    packagegroup-arago-tisdk-matrix \
    packagegroup-arago-tisdk-matrix-extra \
"

WKS_FILE = "tisdk-edgeai-sdimage.wks"
WIC_CREATE_EXTRA_ARGS += " --no-fstab-update"

do_image_wic[depends] += "${@oe.utils.conditional("MACHINE", "am62axx-evm", "", "edgeai-uenv:do_deploy", d)}"

IMAGE_BOOT_FILES:remove = "uEnv.txt"
IMAGE_BOOT_FILES:append:edgeai = " uEnv_edgeai-apps.txt;uEnv.txt "
IMAGE_BOOT_FILES:append:adas = " uEnv_vision-apps.txt;uEnv.txt "

# Remove edgeai-uenv for AM62a
IMAGE_BOOT_FILES:remove:am62axx-evm:edgeai = "uEnv_edgeai-apps.txt;uEnv.txt"
IMAGE_BOOT_FILES:remove:am62axx-evm:adas = "uEnv_vision-apps.txt;uEnv.txt"
IMAGE_BOOT_FILES:append:am62axx-evm = " uEnv.txt"

# For AM68-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES:remove:j721s2-evm:edgeai = "tiboot3.bin"
IMAGE_BOOT_FILES:append:j721s2-evm:edgeai = " tiboot3-j721s2-hs-fs-evm.bin;tiboot3.bin"

# For AM69-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES:remove:j784s4-evm:edgeai = "tiboot3.bin"
IMAGE_BOOT_FILES:append:j784s4-evm:edgeai = " tiboot3-j784s4-hs-fs-evm.bin;tiboot3.bin"

IMAGE_BASENAME:edgeai = "tisdk-edgeai-image"
IMAGE_BASENAME:adas = "tisdk-adas-image"
export IMAGE_BASENAME

PR:append = "_edgeai_2"
