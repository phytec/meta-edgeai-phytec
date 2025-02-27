SUMMARY = "Add a systemd init service to launch Edge AI out-of-box demos"
HOMEPAGE = "https://git.ti.com/cgit/apps/edgeai-gui-app"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "file://dot.profile"

RDEPENDS:${PN} += "edgeai-gst-apps-source"

inherit systemd

do_install() {
    install -d ${D}/root
    install -m 0755 ${WORKDIR}/dot.profile ${D}/root/.profile
}

FILES:${PN} += "/root/.profile"

PR:append = "_edgeai_0"

