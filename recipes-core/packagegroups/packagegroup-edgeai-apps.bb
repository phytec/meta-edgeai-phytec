DESCRIPTION = "Edgeai demo apps"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES += " \
        ${PN}-src \
"
PROVIDES = "${PACKAGES}"

RDEPENDS:${PN} = "\
        edgeai-apps-utils \
        edgeai-gst-apps \
        edgeai-studio-agent \
        edgeai-tidl-models \
        edgeai-test-data \
"

RDEPENDS:${PN}-src = "\
        edgeai-gst-apps-source \
        edgeai-apps-utils-source \
" 