PR:append = "_edgeai_0"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://tiLFW-2-3-ti-dm-Update-firmware-for-Jacinto-devices.patch \
"

PATCHTOOL = "git"
