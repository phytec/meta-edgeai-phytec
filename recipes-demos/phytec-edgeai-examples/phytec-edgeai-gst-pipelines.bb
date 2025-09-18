# Copyright (C) 2025 Dominik Haller <d.haller@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "PHYTEC examples for edgeai gst pipelines"
HOMEPAGE = "http://www.phytec.de"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

RDEPENDS:${PN} = " \
    edgeai-gst-plugins \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-good-video4linux2 \
    phycam-setup \
"

SRC_URI:append:j721s2 = " \
    file://run_vm016_csi0.sh \
    file://run_vm016_isp_csi0.sh \
    file://run_vm016_isp_csi0_port0.sh \
    file://run_vm016_isp_csi0_port1.sh \
    file://run_vm016_isp_csi0_port01.sh \
    file://run_vm016_isp_csi1_port0.sh \
    file://run_vm016_isp_csi0_object_det.sh \
    file://run_vm016_isp_csi0_keypoint_det.sh \
    file://receive_rtp_stream.sh \
"

EXAMPLE_TARGET_FOLDER = "${ROOT_HOME}/phytec_edgeai_examples"

do_install() {
    install -d ${D}${EXAMPLE_TARGET_FOLDER}

    if [ -e ${WORKDIR}/run_vm016_csi0.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_csi0.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_port0.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_port0.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_port1.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_port1.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_port01.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_port01.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi1_port0.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi1_port0.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_object_det.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_object_det.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_keypoint_det.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_keypoint_det.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/receive_rtp_stream.sh ]; then
        install -m 0755 ${WORKDIR}/receive_rtp_stream.sh ${D}${EXAMPLE_TARGET_FOLDER}
    fi
}

FILES:${PN} = " \
    ${EXAMPLE_TARGET_FOLDER} \
"
