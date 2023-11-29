FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:k3 = " \
    file://0001-tiU21.01-arm-mach-k3-j721s2-Enable-QoS-for-DSS.patch \
    file://0002-tiU21.01-arm-mach-k3-j784s4-Enable-QoS-for-DSS.patch \
"

SRC_URI:append:j721e = " \
    file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch \
    file://0001-Optimal-QoS-Settings.patch \
"

SRC_URI:append:j721e:k3r5 = " \
    file://0001-Optimal-QoS-Settings.patch \
"

SRC_URI:append:j721s2 = " \
    file://0001-binman-j721s2-Remove-firewall-ID-5143-5144.patch \
"

PACKAGECONFIG[dm-edgeai] = "DM=${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu1_0.out,,ti-edgeai-firmware"

PACKAGECONFIG:remove:am62axx = " dm"
PACKAGECONFIG:append:am62axx = " dm-edgeai"

PR:append = "_edgeai_2"
