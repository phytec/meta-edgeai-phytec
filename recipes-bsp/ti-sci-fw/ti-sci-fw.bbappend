PR:append = "_edgeai_0"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:j784s4 = " \
    file://0001-Update-ti-dm-and-ti-ipc-for-j784s4.patch \
"

PATCHTOOL = "git"
