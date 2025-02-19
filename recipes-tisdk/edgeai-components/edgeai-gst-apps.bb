SUMMARY = "EdgeAI Gst Apps"
DESCRIPTION = "EdgeAI Gst Apps implements gstreamer based simple deep learning demos that runs on TI platforms"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-gst-apps"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=dc68ab0305d85e56491b9a9aed2309f2"

PV = "1.0.0"
BRANCH = "main"
SRC_URI = "git://github.com/TexasInstruments/edgeai-gst-apps.git;branch=${BRANCH};protocol=https"
SRCREV = "87e8cc685d4d39f5afa9fc6f74f7616620c49c3a"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"
PLAT_SOC:am62xx = "am62x"
PLAT_SOC:am62pxx = "am62p"

S = "${WORKDIR}/git/apps_cpp"

DEPENDS = "ti-vision-apps edgeai-dl-inferer yaml-cpp gstreamer1.0 opencv"
DEPENDS:remove:am62xx = "ti-vision-apps"
DEPENDS:remove:am62pxx = "ti-vision-apps"

RDEPENDS:${PN} += "edgeai-gst-plugins edgeai-dl-inferer-staticdev"

RDEPENDS:${PN}-source += "bash python3-core edgeai-dl-inferer-dev python3-yamlloader python3-numpy opencv cmake dialog"

COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j722s|am62axx|am62xx|am62pxx"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt"

inherit cmake pkgconfig

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-gst-apps
    cp ${CP_ARGS} ${WORKDIR}/git/* ${D}/opt/edgeai-gst-apps
    cp ${CP_ARGS} ${WORKDIR}/out/bin ${D}/opt/edgeai-gst-apps/apps_cpp/
}

INSANE_SKIP:${PN}-source += "dev-deps"

PR:append = "_edgeai_2"
