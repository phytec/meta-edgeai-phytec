SUMMARY = "Open Source DL/ML runtime Modules"
DESCRIPTION = "Open Source DL/ML runtime Modules like TF-LITE and ONNX Runtime, NEO-AI-DLR. Supports both Python and CPP APIs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"
PR:append = "_edgeai_4"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"

SRC_URI = "https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_02/OSRT_TOOLS/ARM_LINUX/ARAGO/dlr-1.13.0-py3-none-any.whl;name=dlr;subdir=${S}/dlr\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_02/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_runtime-2.12.0-cp312-cp312-linux_aarch64.whl;name=tflite;subdir=${S}/tflite\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_02/OSRT_TOOLS/ARM_LINUX/ARAGO/onnxruntime_tidl-1.14.0+10000000-cp312-cp312-linux_aarch64.whl;name=ort;subdir=${S}/ort\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_02/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_2.12_aragoj7.tar.gz;name=tfl_lib;subdir=${S}/tfl_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_02/OSRT_TOOLS/ARM_LINUX/ARAGO/onnx_1.14.0_aragoj7.tar.gz;name=ort_lib;subdir=${S}/ort_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/opencv_4.2.0_aragoj7.tar.gz;name=opencv;subdir=${S}/opencv\
"
SRC_URI[dlr.sha256sum] = "28683c8a5bc9e52fee09c7af6c48b9135406228f2c5eec421c34f3302180ba43"
SRC_URI[tflite.sha256sum] = "94c5f0ccbd5458cfa1327b378c7d479dc7d23979df8f26f091720f850dc02364"
SRC_URI[ort.sha256sum] = "4f889a5b78fa85139d7849b1b879ad1687a537ab655b27a1a9272cf46449de67"
SRC_URI[tfl_lib.sha256sum] = "2ff6878f51595395d84830747da6a8ddbb168eab93e84edd9e5f75cfb33b6b55"
SRC_URI[ort_lib.sha256sum] = "87c11fa0dc6c2e11badb947b7d417d6b96d10ff2421cbe234c10d2e4e3da0615"
SRC_URI[opencv.sha256sum] = "4122073c37e3dd268fa814b6a53510325a1e6636aa3aea9d02ab79f42b4355bd"

do_cp_downloaded_build_deps() {
    mv ${S}/tfl_lib/*/* ${S}/tfl_lib
    mv ${S}/ort_lib/*/* ${S}/ort_lib
    mv ${S}/opencv/*/* ${S}/opencv
}
addtask cp_downloaded_build_deps after do_unpack before do_patch

DEPENDS += "python3-pip-native"

COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j722s|am62axx|am62xx|am62pxx"

export TARGET_FS = "${WORKDIR}/recipe-sysroot"

PY_DST_DIR="${D}${libdir}/python3.12/site-packages"
LIB_DST_DIR="${D}${libdir}"

FILES:${PN}-staticdev += "${libdir}/tflite_2.12/"
FILES:${PN} += "${libdir}/*.so*"
FILES:${PN} += "${libdir}/python3.12/*"
FILES:${PN} += "${includedir}"
FILES:${PN} += "/usr/dlr/"

do_install() {
    pip3 install  --no-deps --platform linux_aarch64 ${S}/tflite/tflite_runtime-2.12.0-cp312-cp312-linux_aarch64.whl --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/dlr/dlr-1.13.0-py3-none-any.whl  --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/ort/onnxruntime_tidl-1.14.0+10000000-cp312-cp312-linux_aarch64.whl  --target ${PY_DST_DIR} --disable-pip-version-check

    install -d ${D}${includedir}
    install -d ${LIB_DST_DIR}

    cp -r ${S}/tfl_lib/tensorflow  ${D}${includedir}/
    cp -r ${S}/tfl_lib/tflite_2.12  ${LIB_DST_DIR}/
    cp ${S}/tfl_lib/libtensorflow-lite.a ${LIB_DST_DIR}/

    cp   ${S}/ort_lib/libonnxruntime.so.1.14.0*  ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libonnxruntime.so.1.14.0* ${LIB_DST_DIR}/libonnxruntime.so
    rm -rf  ${S}/ort_lib/onnxruntime/csharp
    cp -r  ${S}/ort_lib/onnxruntime ${D}${includedir}/

    cp -r ${S}/opencv/opencv-4.2.0  ${D}${includedir}/

    mkdir -p ${D}/usr/dlr
    ln -s -r ${libdir}/python3.12/site-packages/dlr/libdlr.so ${LIB_DST_DIR}/libdlr.so
}

