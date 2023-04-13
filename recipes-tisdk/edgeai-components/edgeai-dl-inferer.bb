SUMMARY = "EdgeAI DL Inferer"
DESCRIPTION = "A Linux based CPP library for abstracting Open Source Runtime (OSRT) API"
HOMEPAGE = "https://git.ti.com/cgit/edgeai/edgeai-dl-inferer/about/"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4309553a9d3611cdf7a78bd169ec583c"

SRC_URI = "git://git.ti.com/edgeai/edgeai-dl-inferer.git;tag=EDGEAI_APP_STACK_08_06_00_02;nobranch=1;protocol=git"

S = "${WORKDIR}/git"

DEPENDS = "ti-tidl-osrt yaml-cpp opencv"
RDEPENDS:${PN} += "ti-tidl-osrt-staticdev"
RDEPENDS:${PN}-source = "bash python3-core"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE"

FILES:${PN} += "/usr/lib/python3.8/site-packages/"
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

PR:append = "_edgeai_0"
