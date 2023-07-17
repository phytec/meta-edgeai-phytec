SUMMARY = "EdgeAI TIOVX Apps"
DESCRIPTION = "EdgeAI TIOVX Apps implements Open VX based simple deep learning demos that runs on TI platforms"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-tiovx-apps"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=dc68ab0305d85e56491b9a9aed2309f2"

PV = "${SRCPV}"
BRANCH = "develop"
SRC_URI = "git://github.com/TexasInstruments/edgeai-tiovx-apps.git;branch=${BRANCH};protocol=https"
SRCREV = "${AUTOREV}"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:am62axx = "am62a"

S = "${WORKDIR}/git"

DEPENDS = "ti-vision-apps yaml-cpp"
RDEPENDS:${PN}-source = "cmake"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt"

inherit cmake

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-tiovx-apps
    cp ${CP_ARGS} ${WORKDIR}/git/* ${D}/opt/edgeai-tiovx-apps
    cp ${CP_ARGS} ${WORKDIR}/out/bin ${D}/opt/edgeai-tiovx-apps
    cd ${D}/opt/edgeai-tiovx-apps
    rm -rf build bin lib
}

PR:append = "_edgeai_0"
