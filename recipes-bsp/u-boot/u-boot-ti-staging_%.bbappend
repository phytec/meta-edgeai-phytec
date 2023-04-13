FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append_k3 = " \
    file://0001-tiU21.01-arm-mach-k3-j721s2-Enable-QoS-for-DSS.patch \
    file://0002-tiU21.01-arm-mach-k3-j784s4-Enable-QoS-for-DSS.patch \
"

SRC_URI:append:j721e-evm = " \
    file://0001-Optimal-QoS-Settings.patch \
"
SRC_URI:append:j721e-evm = " \
    file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch \
"
SRC_URI:append:j721e-evm-k3r5 = " \
    file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch \
"
SRC_URI:append:j721e-hs-evm = " \
    file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch \
"
SRC_URI:append:j721e-hs-evm-k3r5 = " \
    file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch \
"
SRC_URI:append:j721e-hs-evm-k3r5-sr1-1 = " \
    file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch \
"
SRC_URI:append:j721e-evm-k3r5 = " \
    file://0001-Optimal-QoS-Settings.patch \
"
SRC_URI:append:j721e-hs-evm = " \
    file://0001-Optimal-QoS-Settings.patch \
"
SRC_URI:append:j721e-hs-evm-k3r5 = " \
    file://0001-Optimal-QoS-Settings.patch \
"
SRC_URI:append:j721e-hs-evm-k3r5-sr1-1 = " \
    file://0001-Optimal-QoS-Settings.patch \
"

PR:append = "_edgeai_1"
