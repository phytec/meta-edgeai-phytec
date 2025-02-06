DESCRIPTION = "Edgeai demo apps"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
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
        edgeai-studio-agent \
"
