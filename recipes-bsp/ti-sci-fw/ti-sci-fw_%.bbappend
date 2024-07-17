PR:append = "_edgeai_0"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://tiLFW-1-3-ti-sysfw-Update-System-firmware-to-v10.00.07-for-Jacinto-devices.patch \
"

PATCHTOOL = "git"
