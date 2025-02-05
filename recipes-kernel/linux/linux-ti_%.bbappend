FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://k3-j721s2-rtos-memory-map.dtsi;subdir=ti \
    file://k3-j721s2-edgeai-apps.dtso;subdir=ti \
    file://k3-j721s2-vision-apps.dtso;subdir=ti \
    file://0001-makefile-edgeai-overlay.patch \
    file://0003-media-ar0144-Adjust-max_fps-setting-for-ISP-modes.patch \
    file://0005-media-ar0144-Fix-memory-access-from-ISP-ioctls.patch \
    file://0006-BSPIMX8M-2382-media-ar0144-Allow-to-configure-a-mini.patch \
"

#TODO makefile patch (or dtso_fixup), install/add to KERNEL_DEVICETREE


do_configure:prepend() {
	cp -r ${WORKDIR}/ti ${S}/arch/arm64/boot/dts/
}
