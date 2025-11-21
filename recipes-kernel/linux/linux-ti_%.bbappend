FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DT_SUBDIR = "ti"

SRC_URI:append = " \
    file://k3-j721s2-rtos-memory-map.dtsi;subdir=${DT_SUBDIR} \
    file://k3-j721s2-edgeai-apps.dtso;subdir=${DT_SUBDIR} \
    file://k3-j721s2-vision-apps.dtso;subdir=${DT_SUBDIR} \
    file://0003-media-ar0144-Adjust-max_fps-setting-for-ISP-modes.patch \
    file://0005-media-ar0144-Fix-memory-access-from-ISP-ioctls.patch \
    file://0006-BSPIMX8M-2382-media-ar0144-Allow-to-configure-a-mini.patch \
    file://0001-arm64-dts-k3-am68-phyboard-izar-Add-display-port-ove.patch \
"

do_configure:prepend() {
	cp -r ${WORKDIR}/${DT_SUBDIR} ${S}/arch/arm64/boot/dts/
}
