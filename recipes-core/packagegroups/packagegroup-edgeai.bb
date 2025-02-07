DESCRIPTION = "Task to install all packages needed on target, for the edgeai/adas SDK"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
        phytec-edgeai-firmware \
        ti-vision-apps \
        ti-tidl \
        ti-tidl-osrt-staticdev \
        edgeai-dl-inferer-staticdev \
        edgeai-tidl-models \
        edgeai-tiovx-kernels \
        edgeai-tiovx-modules \
        edgeai-gst-plugins \
        edgeai-gst-apps \
"



RDEPENDS:${PN}-dev = "\
        ti-tidl-dev \
        ti-tidl-osrt-dev \
        edgeai-apps-utils-source \
        edgeai-test-data \
        ti-vision-apps-dev \
        edgeai-gst-plugins-dev \
        edgeai-gst-apps-source \
        edgeai-gst-plugins-source \
        edgeai-dl-inferer-source \        
"
