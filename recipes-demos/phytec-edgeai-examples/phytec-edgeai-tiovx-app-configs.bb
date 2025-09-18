# Copyright (C) 2025 PHYTEC Messtechnik GmbH,
# Author: Steffen Hemer <s.hemer@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "PHYTEC example configs for edgeai tiovx app"
HOMEPAGE = "http://www.phytec.de"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

RDEPENDS:${PN} = " \
    edgeai-tiovx-apps \
    phycam-setup \
"

SRC_URI:append:j721s2 = " \
    file://object_detection_phytec_vm016_csi0.yaml \
    file://README.md \
"

EXAMPLE_TARGET_FOLDER = "${ROOT_HOME}/phytec_edgeai_examples/tiovx_apps_configs"

do_install() {
    install -d ${D}${EXAMPLE_TARGET_FOLDER}

    if [ -e ${WORKDIR}/README.md ]; then
        install -m 0755 ${WORKDIR}/README.md ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/object_detection_phytec_vm016_csi0.yaml ]; then
        install -m 0755 ${WORKDIR}/object_detection_phytec_vm016_csi0.yaml ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    install -d ${D}${ROOT_HOME}/output
}

FILES:${PN} = " \
    ${EXAMPLE_TARGET_FOLDER} \
    ${ROOT_HOME}/output \
"

