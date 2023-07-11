SUMMARY = "EdgeAI Apps Utils"
DESCRIPTION = "EdgeAI Apps Utils implements ARMv8 neon optimized utility functions and also NV12 post process utility functions "
HOMEPAGE = "https://git.ti.com/cgit/edgeai/edgeai-apps-utils"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f7721ee7d288457c5a70d0c8ff44b87"

PV = "${SRCPV}"
BRANCH = "develop"
SRC_URI = "git://git.ti.com/edgeai/edgeai-apps-utils.git;protocol=git;branch=${BRANCH}"
SRCREV = "17d4d44c70b82131b7f25e85942a1d103b9c8964"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:am62axx = "am62a"
PLAT_SOC:am62xx = "am62x"

S = "${WORKDIR}/git"

RDEPENDS:${PN}-source += "python3-core cmake"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm|am62xx"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

inherit cmake

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-apps-utils
    cp ${CP_ARGS} ${S}/* ${D}/opt/edgeai-apps-utils
    cd ${D}/opt/edgeai-apps-utils
    rm -rf build lib
}

PR:append = "_edgeai_0"
