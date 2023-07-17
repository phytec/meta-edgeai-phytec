DESCRIPTION = "Package that will install a uEnv_edgeai-apps.txt file into the SDK prebuilt-binaries directory"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

SRC_URI:edgeai = "\
    file://uEnv_edgeai-apps.txt \
"

SRC_URI:adas = "\
    file://uEnv_vision-apps.txt \
"

PR:append = "_edgeai_0"
PV = "1.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}"

do_install () {
    install -d ${D}/board-support/prebuilt-images
}

do_install:append:edgeai () {
    install -m 0644 ${S}/uEnv_edgeai-apps.txt ${D}/board-support/prebuilt-images/
}

do_install:append:adas () {
    install -m 0644 ${S}/uEnv_vision-apps.txt ${D}/board-support/prebuilt-images/
}

FILES:${PN} += "board-support/*"

# deploy files for wic image
inherit deploy
do_deploy() {
    install -d ${DEPLOYDIR}
}

do_deploy:append:edgeai () {
    install -m 0644 ${S}/uEnv_edgeai-apps.txt ${DEPLOYDIR}
}

do_deploy:append:adas () {
    install -m 0644 ${S}/uEnv_vision-apps.txt ${DEPLOYDIR}
}

addtask deploy before do_build after do_unpack
