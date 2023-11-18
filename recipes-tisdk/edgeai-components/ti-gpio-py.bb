SUMMARY = "TI GPIO py"
DESCRIPTION = "A Linux based Python library for TI GPIO RPi header enabled platforms"
HOMEPAGE = "https://github.com/TexasInstruments/ti-gpio-py"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.txt;md5=c514977d484aa7daa306d63effcba9bc"

SRC_URI = "git://github.com/TexasInstruments/ti-gpio-py.git;protocol=https;branch=master"
SRCREV = "dc0156f14bc366b2877fb9242d26488f155cd4c6"

S = "${WORKDIR}/git"

DEPENDS = "gpiozero"
COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

inherit setuptools3

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/ti-gpio-py
    cp ${CP_ARGS} ${S}/* ${D}/opt/ti-gpio-py
}

PR:append = "_edgeai_1"
