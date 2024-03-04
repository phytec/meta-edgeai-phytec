# Produces wic Image for Edge AI demos

require recipes-core/images/tisdk-default-image.bb

PN:adas = "tisdk-adas-image"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm"

EDGEAI_STACK = " \
        ti-vision-apps-dev \
        ti-edgeai-firmware \
        ti-tidl-dev \
        edgeai-tiovx-kernels-dev \
        edgeai-tiovx-modules-dev \
        edgeai-tiovx-kernels-source \
        edgeai-tiovx-modules-source \
        edgeai-apps-utils-source \
        edgeai-test-data \
        edgeai-tidl-models \
"

EDGEAI_STACK:append:edgeai = " \
        ti-tidl-osrt-dev \
        ti-tidl-osrt-staticdev \
        edgeai-init \
        edgeai-gui-app \
        edgeai-gst-plugins-dev \
        edgeai-dl-inferer-staticdev \
        edgeai-gst-apps-source \
        edgeai-gst-plugins-source \
        edgeai-gst-apps \
        edgeai-dl-inferer-source \
        ti-gpio-cpp \
        ti-gpio-py \
        ti-gpio-cpp-source \
        ti-gpio-py-source \
        edgeai-studio-agent \
"

EDGEAI_STACK:append:adas = " \
        ti-tidl-osrt-staticdev \
        edgeai-gst-plugins \
        edgeai-tiovx-apps-dev \
        edgeai-tiovx-apps-source \
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

IMAGE_INSTALL:append = " \
    resize-rootfs \
"

IMAGE_INSTALL:append:j721e = " pmic-fix"

WKS_FILE = "tisdk-edgeai-sdimage.wks"
WIC_CREATE_EXTRA_ARGS += " --no-fstab-update"

# For AM68-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES:remove:j721s2-evm:edgeai = "tiboot3.bin"
IMAGE_BOOT_FILES:append:j721s2-evm:edgeai = " tiboot3-j721s2-hs-fs-evm.bin;tiboot3.bin"

# For AM69-SK, default tiboot3.bin should be HSFS
IMAGE_BOOT_FILES:remove:j784s4-evm:edgeai = "tiboot3.bin"
IMAGE_BOOT_FILES:append:j784s4-evm:edgeai = " tiboot3-j784s4-hs-fs-evm.bin;tiboot3.bin"

# Package both HS-FS and gp binaries for Adas images
IMAGE_BOOT_FILES:append:j784s4-evm:adas = " tiboot3-j784s4-gp-evm.bin tiboot3-j784s4-hs-fs-evm.bin"
IMAGE_BOOT_FILES:append:j721s2-evm:adas = " tiboot3-j721s2-gp-evm.bin tiboot3-j721s2-hs-fs-evm.bin"

IMAGE_BASENAME:edgeai = "tisdk-edgeai-image"
IMAGE_BASENAME:adas = "tisdk-adas-image"
export IMAGE_BASENAME

PR:append = "_edgeai_6"
