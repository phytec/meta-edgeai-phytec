SUMMARY = "EdgeAI DL Inferer"
DESCRIPTION = "A Linux based CPP library for abstracting Open Source Runtime (OSRT) API"
HOMEPAGE = "https://git.ti.com/cgit/edgeai/edgeai-dl-inferer/about/"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4309553a9d3611cdf7a78bd169ec583c"

PV = "${SRCPV}"
BRANCH = "develop"
SRC_URI = "git://git.ti.com/edgeai/edgeai-dl-inferer.git;protocol=git;branch=${BRANCH}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

DEPENDS = "edgeai-apps-utils ti-tidl-osrt yaml-cpp opencv"
RDEPENDS:${PN} += "ti-tidl-osrt-staticdev"
RDEPENDS:${PN}-source = "bash python3-core cmake python3-yamlloader python3-numpy opencv opencv-dev"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm|am62xx"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

FILES:${PN} += "/usr/lib/python3.10/site-packages/"
PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

inherit cmake

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-dl-inferer
    cp ${CP_ARGS} ${S}/* ${D}/opt/edgeai-dl-inferer
    rm -rf ${D}/opt/edgeai-dl-inferer/lib
    rm -rf ${D}/usr/cmake
}

INSANE_SKIP:${PN} += "dev-deps"
INSANE_SKIP:${PN}-source += "dev-deps"

PR:append = "_edgeai_0"
