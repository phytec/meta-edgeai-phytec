FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-v4l2-utils-update-Linux-headers-for-multiplexed-stre.patch \
    file://0002-v4l2-ctl-Add-routing-and-streams-support.patch \
    file://0003-media-ctl-add-support-for-routes-and-streams.patch \
    file://0004-v4l2-ctl-compliance-add-routing-and-streams-multiple.patch \
    file://0001-media-ctl-add-support-for-RGBIr-bayer-formats.patch \
"

PR:append = "_edgeai_0"
