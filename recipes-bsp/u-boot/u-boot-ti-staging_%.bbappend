FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://tiU25.01-TI-HACK-Kconfig-Add-default-bootcmd-for-all-devices-of-ARCH_K3.patch \
"

SRC_URI:append:j721e = " \
    file://0001-Optimal-QoS-Settings.patch \
    file://0001-arch-arm-dts-k3-j721e-Update-memory-map-for-PSDK-RTO.patch \
"

SRC_URI:append:j721s2 = " \
    file://0001-arch-arm-dts-k3-j721s2-k3-am68-Update-memory-map-for.patch \
"

SRC_URI:append:j784s4 = " \
    file://0001-arch-arm-dts-k3-j784s4-k3-am69-Update-memory-map-for.patch \
    file://0001-HACK-remoteproc-k3-r5-Release-processor-control-for-.patch \
    file://0002-HACK-arm-mach-k3-j7xx_hardware.h-Expose-MCU-R5-proc-.patch \
    file://0003-HACK-arch-mach-k3-common-Add-support-to-shutdown-MCU.patch \
    file://0004-include-environment-ti-Add-firmware-for-MCU-R5-core1.patch \
    file://0005-arm-dts-k3-j7xx-binman-Enable-split-mode-for-MCU-R5F.patch \
    file://0006-arm-dts-k3-j7xx-mcu-wakeup-Enable-split-mode-for-MCU.patch \
    file://tiU25.01-TI-HACK-arm-dts-k3-j784s4-evm-u-boot-Add-PBIST_14-node.patch \
"

SRC_URI:append:j742s2 = " \
    file://0001-arch-arm-dts-k3-j742s2-Update-memory-map-for-PSDK-RT.patch \
"

SRC_URI:append:j722s = " \
    file://0001-arch-arm-dts-k3-j722s-Update-memory-map-for-PSDK-RTO.patch \
"

PACKAGECONFIG[dm-edgeai] = "DM=${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu1_0.out,,ti-edgeai-firmware"

PACKAGECONFIG:remove:am62axx:edgeai = " dm"
PACKAGECONFIG:append:am62axx:edgeai = " dm-edgeai"

PR:append = "_edgeai_5"
