DESCRIPTION = "Edgeai core components"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES += " \
        ${PN}-src \
"
PROVIDES = "${PACKAGES}"

RDEPENDS:${PN} = "\
        phytec-edgeai-firmware \
        ti-tidl \
        ti-tidl-osrt-staticdev \
        ti-vision-apps \
        edgeai-dl-inferer-staticdev \
        edgeai-tidl-models \
        edgeai-tiovx-kernels \
        edgeai-tiovx-modules \
        edgeai-gst-plugins \
"

RDEPENDS:${PN}-dev = "\
        ti-tidl-dev \
        ti-tidl-osrt-dev \
        ti-vision-apps-dev \
        edgeai-test-data \
        edgeai-gst-plugins-dev \    
"

RDEPENDS:${PN}-src = "\
        edgeai-gst-plugins-source \
        edgeai-dl-inferer-source \
" 
