SUMMARY = "Open Source DL/ML runtime Modules"
DESCRIPTION = "Open Source DL/ML runtime Modules like TF-LITE and ONNX Runtime, NEO-AI-DLR. Supports both Python and CPP APIs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"
PR:append = "_edgeai_2"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"

SRC_URI = "https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_01_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/dlr-1.13.0-py3-none-any.whl;name=dlr;subdir=${S}/dlr\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_01_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_runtime-2.8.2-cp310-cp310-linux_aarch64.whl;name=tflite;subdir=${S}/tflite\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_01_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnxruntime_tidl-1.14.0-cp310-cp310-linux_aarch64.whl;name=ort;subdir=${S}/ort\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_01_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_2.8_aragoj7.tar.gz;name=tfl_lib;subdir=${S}/tfl_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_01_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnx_1.14.0_aragoj7.tar.gz;name=ort_lib;subdir=${S}/ort_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_01_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/opencv_4.2.0_aragoj7.tar.gz;name=opencv;subdir=${S}/opencv\
"
SRC_URI[dlr.sha256sum] = "ec04d394bd1e0a3e7d988f1fa86ed50311fe1a0a957daef4d05e38e821dedec6"
SRC_URI[tflite.sha256sum] = "8ab3d0c00da3f80a538b53701c8cf242f32adba7702a8d18bab6fd49b2c42630"
SRC_URI[ort.sha256sum] = "648ea4efd0580d48ffb459e8a8b0c82821d6d418e2ebd4bac7917715f9eb0ad6"
SRC_URI[tfl_lib.sha256sum] = "f954709a5b1ca71e16220b697c3a6f457571d486ac05b00bb3d3e9ae85c422a5"
SRC_URI[ort_lib.sha256sum] = "26fdfe7266a0f1c63decedde1a6db3e6ed03dd4141aa965a532191f58a84c338"
SRC_URI[opencv.sha256sum] = "4122073c37e3dd268fa814b6a53510325a1e6636aa3aea9d02ab79f42b4355bd"

do_cp_downloaded_build_deps() {
    mv ${S}/tfl_lib/*/* ${S}/tfl_lib
    mv ${S}/ort_lib/*/* ${S}/ort_lib
    mv ${S}/opencv/*/* ${S}/opencv
}
addtask cp_downloaded_build_deps after do_unpack before do_patch

DEPENDS += "python3-pip-native"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm|am62xx|am62pxx"

export TARGET_FS = "${WORKDIR}/recipe-sysroot"

PY_DST_DIR="${D}${libdir}/python3.10/site-packages"
LIB_DST_DIR="${D}${libdir}"

FILES:${PN}-staticdev += "${libdir}/tflite_2.8/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/pthreadpool/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/farmhash-build/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/xnnpack-build/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/fft2d-build/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/clog-build/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/cpuinfo-build/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/ruy-build/*.a"
FILES:${PN}-staticdev += "${libdir}/tflite_2.8/flatbuffers-build/*.a"
FILES:${PN}-staticdev += "${libdir}/*.a"
FILES:${PN} += "${libdir}/*.so*"
FILES:${PN} += "${libdir}/python3.10/*"
FILES:${PN} += "${includedir}"

do_install() {
    pip3 install  --no-deps --platform linux_aarch64 ${S}/tflite/tflite_runtime-2.8.2-cp310-cp310-linux_aarch64.whl --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/dlr/dlr-1.13.0-py3-none-any.whl  --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/ort/onnxruntime_tidl-1.14.0-cp310-cp310-linux_aarch64.whl  --target ${PY_DST_DIR} --disable-pip-version-check

    install -d ${D}${includedir}
    install -d ${LIB_DST_DIR}

    cp -r ${S}/tfl_lib/tensorflow  ${D}${includedir}/
    cp -r ${S}/tfl_lib/tflite_2.8  ${LIB_DST_DIR}/
    cp ${S}/tfl_lib/libtensorflow-lite.a ${LIB_DST_DIR}/

    cp   ${S}/ort_lib/libonnxruntime.so.1.14.0  ${LIB_DST_DIR}/libonnxruntime.so.1.14.0
    ln -s -r ${LIB_DST_DIR}/libonnxruntime.so.1.14.0 ${LIB_DST_DIR}/libonnxruntime.so
    rm -rf  ${S}/ort_lib/onnxruntime/csharp
    cp -r  ${S}/ort_lib/onnxruntime ${D}${includedir}/

    cp -r ${S}/opencv/opencv-4.2.0  ${D}${includedir}/
}

