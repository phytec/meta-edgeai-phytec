# Copyright (C) 2025 Dominik Haller <d.haller@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "PHYTEC examples for edgeai gst pipelines"
HOMEPAGE = "http://www.phytec.de"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

inherit systemd

SYSTEMD_SERVICE:${PN} = "phytec-ew25-gst-pipeline.service"

FILES:${PN} = " \
    ${ROOT_HOME} \
    ${systemd_unitdir}/system/phytec-ew25-gst-pipeline.service \
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
    file://receive_rtp_stream.sh \
    file://ew25_dual-vm016_multi-inference.sh \
    file://phytec-ew25-gst-pipeline.service \
"

EXAMPLE_TARGET_FOLDER = "${D}${ROOT_HOME}/phytec_edgeai_examples"

do_install() {
    install -d ${EXAMPLE_TARGET_FOLDER}

    if [ -e ${WORKDIR}/run_vm016_csi0.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_csi0.sh ${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0.sh ${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_object_det.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_object_det.sh ${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/run_vm016_isp_csi0_keypoint_det.sh ]; then
        install -m 0755 ${WORKDIR}/run_vm016_isp_csi0_keypoint_det.sh ${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/receive_rtp_stream.sh ]; then
        install -m 0755 ${WORKDIR}/receive_rtp_stream.sh ${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/ew25_dual-vm016_multi-inference.sh ]; then
        install -m 0755 ${WORKDIR}/ew25_dual-vm016_multi-inference.sh ${EXAMPLE_TARGET_FOLDER}
    fi

    if [ -e ${WORKDIR}/phytec-ew25-gst-pipeline.service ]; then
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/phytec-ew25-gst-pipeline.service ${D}${systemd_unitdir}/system/
    fi
}
