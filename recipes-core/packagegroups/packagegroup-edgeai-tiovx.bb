DESCRIPTION = "Task to install all OpenVX packages needed on target, i.e. for the edgeai/adas SDK"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES += " \
        ${PN}-src \
"
PROVIDES = "${PACKAGES}"

RDEPENDS:${PN} = "\
        edgeai-tiovx-kernels \
        edgeai-tiovx-modules \
"

RDEPENDS:${PN}-dev = "\
        edgeai-tiovx-kernels-dev \
        edgeai-tiovx-modules-dev \
        edgeai-tiovx-apps-dev \
"

RDEPENDS:${PN}-src = "\
        edgeai-tiovx-kernels-source \
        edgeai-tiovx-modules-source \
        edgeai-tiovx-apps-source \
"
