FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:j784s4 = " \
    file://0001-ti-dm-Update-firmware-for-J784s4-devices.patch \
"

PATCHTOOL = "git"

PR:append = "_edgeai_0"
