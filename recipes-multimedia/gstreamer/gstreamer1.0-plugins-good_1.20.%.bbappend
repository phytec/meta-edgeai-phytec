FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:j721e = " \
    file://0002-j721e-v4l2object-Make-number-of-buffers-in-own-pool-same-a.patch \
"

SRC_URI:append:j721s2 = " \
    file://0002-v4l2object-Make-number-of-buffers-in-own-pool-same-a.patch \
"

SRC_URI:append:j784s4 = " \
    file://0002-v4l2object-Make-number-of-buffers-in-own-pool-same-a.patch \
"

SRC_URI:append:am62axx = " \
    file://0002-v4l2object-Make-number-of-buffers-in-own-pool-same-a.patch \
"
