FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging/${MACHINE}:"

SRC_URI:append = " \
    file://0001-arm64-defconfig-Enable-OMAP2MAILBOX-in-the-kernel.patch \
"

SRC_URI:append:j721e-evm = " \
    file://0001-arm64-dts-ti-Add-DTB-overlays-for-vision-apps-and-ed.patch \
"

SRC_URI:append:j721s2-evm = " \
    file://0001-arm64-dts-ti-Add-DTB-overlay-for-vision-apps.patch \
    file://0002-arm64-dts-ti-k3-j721s2-Add-overlay-for-edge-ai.patch \
"

SRC_URI:append:j784s4-evm = " \
    file://0001-arm64-dts-ti-Add-DTB-overlay-for-vision-apps.patch \
    file://0002-arm64-dts-ti-k3-j784s4-Add-overlay-for-edge-ai.patch \
"

SRC_URI:append:j721e-hs-evm = " \
    file://0001-arm64-dts-ti-Add-DTB-overlays-for-vision-apps-and-ed.patch \
"

SRC_URI:append:j721s2-hs-evm = " \
    file://0001-arm64-dts-ti-Add-DTB-overlay-for-vision-apps.patch \
    file://0002-arm64-dts-ti-k3-j721s2-Add-overlay-for-edge-ai.patch \
"

SRC_URI:append:j784s4-hs-evm = " \
    file://0001-arm64-dts-ti-Add-DTB-overlay-for-vision-apps.patch \
    file://0002-arm64-dts-ti-k3-j784s4-Add-overlay-for-edge-ai.patch \
"

PR:append = "_edgeai_1"
