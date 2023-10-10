FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-gstv4l2object-Increase-min-buffers.patch \
"
