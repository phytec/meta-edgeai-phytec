SUMMARY = "EdgeAI DL Inferer"
DESCRIPTION = "A Linux based CPP library for abstracting Open Source Runtime (OSRT) API"
HOMEPAGE = "https://git.ti.com/cgit/edgeai/edgeai-dl-inferer/about/"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4309553a9d3611cdf7a78bd169ec583c"

PV = "${SRCPV}"
BRANCH = "main"
SRC_URI = "git://git.ti.com/git/edgeai/edgeai-dl-inferer.git;protocol=https;branch=${BRANCH}"
SRCREV = "744f27adaedae325d41eaac69634578776fb8f9a"

S = "${WORKDIR}/git"

DEPENDS = "edgeai-apps-utils ti-tidl-osrt yaml-cpp opencv ti-vision-apps"
RDEPENDS:${PN} += "ti-tidl-osrt-staticdev"
RDEPENDS:${PN}-source = "bash python3-core cmake python3-yamlloader python3-numpy opencv opencv-dev"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

FILES:${PN} += "/usr/lib/python3.10/site-packages/"
PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

inherit cmake

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-dl-inferer
    cp ${CP_ARGS} ${S}/* ${D}/opt/edgeai-dl-inferer
    cp ${CP_ARGS} ${WORKDIR}/out/bin ${D}/opt/edgeai-dl-inferer
    rm -rf ${D}/usr/cmake
}

INSANE_SKIP:${PN} += "dev-deps"
INSANE_SKIP:${PN}-source += "dev-deps"

PR:append = "_edgeai_1"
