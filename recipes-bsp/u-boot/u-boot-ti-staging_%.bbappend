FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-configs-j7-Enable-the-NFS-command-by-default.patch \
    file://0001-net-nfs-Set-mount-version-to-default-2.patch \
"

PACKAGECONFIG[dm-edgeai] = "DM=${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu1_0.out,,ti-edgeai-firmware"

PACKAGECONFIG:remove:am62axx:edgeai = " dm"
PACKAGECONFIG:append:am62axx:edgeai = " dm-edgeai"

PR:append = "_edgeai_4"
