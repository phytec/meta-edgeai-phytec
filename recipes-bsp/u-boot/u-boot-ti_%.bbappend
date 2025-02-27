FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:j721s2 = " \
    file://0001-tiU21.01-arm-mach-k3-j721s2-Enable-QoS-for-DSS.patch \
"
