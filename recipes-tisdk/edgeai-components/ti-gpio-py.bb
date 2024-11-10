SUMMARY = "TI GPIO py"
DESCRIPTION = "A Linux based Python library for TI GPIO RPi header enabled platforms"
HOMEPAGE = "https://github.com/TexasInstruments/ti-gpio-py"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.txt;md5=c514977d484aa7daa306d63effcba9bc"

SRC_URI = "git://github.com/TexasInstruments/ti-gpio-py.git;protocol=https;branch=master"
SRCREV = "4de745bee18c1a348d4ca803dff005c0f46aa2ca"

S = "${WORKDIR}/git"

DEPENDS = "gpiozero"
RDEPENDS:${PN} = "python3-gpiod"
COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j722s|am62axx|am62pxx"

inherit setuptools3

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/ti-gpio-py
    cp ${CP_ARGS} ${S}/* ${D}/opt/ti-gpio-py
}

PR:append = "_edgeai_2"
