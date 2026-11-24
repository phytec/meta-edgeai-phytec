SUMMARY = "EdgeAI GST plugins"
DESCRIPTION = "EdgeAI GST plugins implements custom elements to offload compute to HW accelerators and DSPs on TI devices"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-gst-plugins"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f7721ee7d288457c5a70d0c8ff44b87"

PV = "1.0.0"
BRANCH = "main"
SRC_URI = "git://github.com/TexasInstruments/edgeai-gst-plugins.git;branch=${BRANCH};protocol=https"
SRCREV = "52361829b2cc90afe37161b6f2ee8adeac537927"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j742s2 = "j742s2"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"
PLAT_SOC:am62dxx = "am62d"

DEPENDS = "edgeai-tiovx-modules edgeai-apps-utils gstreamer1.0-plugins-base edgeai-dl-inferer ti-tidl-osrt"
DEPENDS:remove:adas = " edgeai-dl-inferer ti-tidl-osrt"
DEPENDS:am62dxx = "gstreamer1.0-plugins-base ti-tidl-osrt ti-librpmsg-dma-example"
RDEPENDS:${PN}-source = "bash meson ninja"

COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j742s2|j722s|am62axx|am62dxx"

export SOC = "${PLAT_SOC}"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"
FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"

EXTRA_OEMESON = "--prefix=/usr -Dpkg_config_path=${S}/pkgconfig"
EXTRA_OEMESON:append:adas = " -Ddl-plugins=disabled"
EXTRA_OEMESON:append:am62dxx = " -Ddl-plugins=disabled -Denable-tidl=disabled -Dtvm-plugins=enabled -Dstft-plugins=enabled"

inherit meson pkgconfig

#   This is a HACK: Current pkg-config version doesnt prepend
#   sysroot_dir if the .pc file doesnt exists in sysroot,
#   see: [0]
#   This loop uses sed to rewrite the prefix path in all .pc
#   files, redirecting them from the host's /usr directory to
#   the Yocto target sysroot to ensure correct cross-compilation.
#
#   [0] https://github.com/pkgconf/pkgconf/pull/226


do_configure:prepend:am62dxx() {
    # Fix pkg-config files for cross-compilation
    sed -i "s|^prefix=/usr|prefix=${STAGING_DIR_TARGET}/usr|g" ${S}/pkgconfig/*.pc
}

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-gst-plugins/
    cp ${CP_ARGS} ${S}/* ${D}/opt/edgeai-gst-plugins
    cp ${CP_ARGS} ${B}/tests ${D}/opt/edgeai-gst-plugins/build
}

#   This is a HACK: Current pkg-config version doesnt prepend
#   sysroot_dir if the .pc file doesnt exists in sysroot,
#   see: [0]
#   This loop strips the absolute Yocto sysroot paths from the
#   installed .pc files and replaces them with standard /usr
#   paths so that the packages remain portable and usable once
#   installed on the actual target hardware..
#
#   [0] https://github.com/pkgconf/pkgconf/pull/226

do_install:append:am62dxx() {
    # Fix pkg-config paths in installed source files
    sed -i "s|${STAGING_DIR_TARGET}/usr|/usr|g" ${D}/opt/edgeai-gst-plugins/pkgconfig/*.pc
}

INSANE_SKIP:${PN}-source += "dev-deps"
INSANE_SKIP:${PN} += "rpaths"

PR = "_edgeai_1"
