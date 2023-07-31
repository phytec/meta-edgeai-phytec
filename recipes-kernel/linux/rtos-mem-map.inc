FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging/${MACHINE}:"

SRC_URI:append = " \
    file://0001-arm64-defconfig-Enable-CONFIG_GPIO_SYSFS.patch \
    file://0001-drm-tidss-Make-zpos-immutable-for-primary-plane.patch \
"

SRC_URI:append:j721e = " \
    file://0003-arm64-dts-ti-k3-j721e-sk-Add-overlay-for-fusion-boar.patch \
    file://0001-arm64-dts-ti-Add-DTB-overlays-for-vision-apps-and-ed.patch \
"

SRC_URI:append:j721s2 = " \
    file://0001-arm64-dts-ti-Add-DTB-overlay-for-vision-apps.patch \
    file://0002-arm64-dts-ti-k3-j721s2-Add-overlay-for-edge-ai.patch \
    file://0003-HACK-arm64-dts-ti-k3-j721s2-main-Disable-sram-for-vp.patch \
"

SRC_URI:append:j784s4 = " \
    file://0001-arm64-dts-ti-Add-DTB-overlay-for-vision-apps.patch \
    file://0002-arm64-dts-ti-k3-j784s4-Add-overlay-for-edge-ai.patch \
    file://0003-tiL6.1-arm64-dts-ti-k3-am69-sk-csi2-ov5640-Fix-powerdown-GPIO.patch \
"

PR:append = "_edgeai_5"
