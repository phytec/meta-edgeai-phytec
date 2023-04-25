SUMMARY = "EdgeAI TIOVX modules"
DESCRIPTION = "EdgeAI TIOVX modules implements simple APIs to create and use OpenVX nodes"
HOMEPAGE = "https://git.ti.com/cgit/edgeai/edgeai-tiovx-modules"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f7721ee7d288457c5a70d0c8ff44b87"

PV = "${SRCPV}"
SRC_URI = "git://git.ti.com/edgeai/edgeai-tiovx-modules.git;branch=develop;protocol=git"
SRCREV = "${AUTOREV}"

PLAT_SOC = ""
PLAT_SOC:j721e-evm = "j721e"
PLAT_SOC:j721e-hs-evm = "j721e"
PLAT_SOC:j721s2-evm = "j721s2"
PLAT_SOC:j721s2-hs-evm = "j721s2"
PLAT_SOC:j784s4-evm = "j784s4"
PLAT_SOC:j784s4-hs-evm = "j784s4"
PLAT_SOC:am62axx-evm = "am62a"

S = "${WORKDIR}/git"

DEPENDS = "ti-vision-apps edgeai-tiovx-kernels"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DINSTALL_SRC=on -DCMAKE_SKIP_RPATH=TRUE"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

inherit cmake

PR:append = "_edgeai_0"
