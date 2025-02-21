# Copyright (C) 2025 Dominik Haller <d.haller@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "PHYTEC examples for edgeai gst pipelines"
HOMEPAGE = "http://www.phytec.de"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

FILES:${PN} = " \
    ${ROOT_HOME} \
"

RDEPENDS:${PN} = " \
    v4l-utils \
    media-ctl \
    phycam-setup \
    edgeai-gst-plugins \
"

SRC_URI:append:j721s2 = " \
    file://run_vm016_csi0.sh \
    file://run_vm016_isp_csi0.sh \
    file://run_vm016_isp_csi0_object_det.sh \
    file://run_vm016_isp_csi0_keypoint_det.sh \
"

do_install() {
    if [ -e ${WORKDIR}/run_vm016_csi0.sh ]; then
        install -d ${D}${ROOT_HOME}
        install -m 0755 ${WORKDIR}/run_vm016_csi0.sh ${D}${ROOT_HOME}/
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0.sh ]; then
        install -d ${D}${ROOT_HOME}
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0.sh ${D}${ROOT_HOME}/
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_object_det.sh ]; then
        install -d ${D}${ROOT_HOME}
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_object_det.sh ${D}${ROOT_HOME}/
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_keypoint_det.sh ]; then
        install -d ${D}${ROOT_HOME}
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_keypoint_det.sh ${D}${ROOT_HOME}/
    fi
}
