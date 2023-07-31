FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:j721s2 = " \
    file://0001-v4l2object-Apply-min-buffers-changes-only-for-codec.patch \
"

SRC_URI:append:j784s4 = " \
    file://0001-v4l2object-Apply-min-buffers-changes-only-for-codec.patch \
"

SRC_URI:append:am62axx = " \
    file://0001-v4l2object-Apply-min-buffers-changes-only-for-codec.patch \
"
