DESCRIPTION = "Edgeai core components"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
        ti-vision-apps-dev \
        ti-tidl-dev \
        edgeai-tiovx-kernels-dev \
        edgeai-tiovx-modules-dev \
        edgeai-tiovx-kernels-source \
        edgeai-tiovx-modules-source \
        edgeai-apps-utils-source \
        edgeai-test-data \
        edgeai-tidl-models \
        edgeai-tiovx-apps-dev \
        edgeai-tiovx-apps-source \
"
