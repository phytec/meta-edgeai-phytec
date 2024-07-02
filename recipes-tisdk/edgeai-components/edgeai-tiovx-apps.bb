SUMMARY = "EdgeAI TIOVX Apps"
DESCRIPTION = "EdgeAI TIOVX Apps implements Open VX based simple deep learning demos that runs on TI platforms"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-tiovx-apps"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=dc68ab0305d85e56491b9a9aed2309f2"

PV = "1.0.0"
BRANCH = "main"
SRC_URI = "git://github.com/TexasInstruments/edgeai-tiovx-apps.git;branch=${BRANCH};protocol=https"
SRCREV = "6abbb460ef29b7aba650e29c1703d9edaba174aa"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"

S = "${WORKDIR}/git"

DEPENDS = "edgeai-tiovx-modules yaml-cpp glib-2.0 ffmpeg libdrm"
RDEPENDS:${PN}-source = "cmake bash python3-core"

COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j722s|am62axx"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt"
ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-dev = "1"

inherit cmake pkgconfig

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-tiovx-apps
    cp ${CP_ARGS} ${WORKDIR}/git/* ${D}/opt/edgeai-tiovx-apps
    cp ${CP_ARGS} ${WORKDIR}/out/bin ${D}/opt/edgeai-tiovx-apps
    rm -rf ${D}/usr/cmake
}

INSANE_SKIP:${PN} += "dev-deps"
INSANE_SKIP:${PN}-source += "dev-deps"

PR:append = "_edgeai_3"
