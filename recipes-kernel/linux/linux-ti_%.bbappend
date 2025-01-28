FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://k3-j721s2-rtos-memory-map.dtsi;subdir=ti \
    file://k3-j721s2-edgeai-apps.dtso;subdir=ti \
    file://k3-j721s2-vision-apps.dtso;subdir=ti \
    file://0001-makefile-edgeai-overlay.patch \
"

#TODO makefile patch (or dtso_fixup), install/add to KERNEL_DEVICETREE


do_configure:prepend() {
	cp -r ${WORKDIR}/ti ${S}/arch/arm64/boot/dts/
}
