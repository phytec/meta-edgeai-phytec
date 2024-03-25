SUMMARY = "Open Source DL/ML runtime Modules"
DESCRIPTION = "Open Source DL/ML runtime Modules like TF-LITE and ONNX Runtime, NEO-AI-DLR. Supports both Python and CPP APIs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"
PR:append = "_edgeai_4"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"

SRC_URI = "https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_03_00/OSRT_TOOLS/ARM_LINUX/ARAGO/dlr-1.13.0-py3-none-any.whl;name=dlr;subdir=${S}/dlr\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_03_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_runtime-2.12.0-cp310-cp310-linux_aarch64.whl;name=tflite;subdir=${S}/tflite\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_03_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnxruntime_tidl-1.14.0-cp310-cp310-linux_aarch64.whl;name=ort;subdir=${S}/ort\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_03_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_2.12_aragoj7.tar.gz;name=tfl_lib;subdir=${S}/tfl_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_03_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnx_1.14.0_aragoj7.tar.gz;name=ort_lib;subdir=${S}/ort_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_03_00/OSRT_TOOLS/ARM_LINUX/ARAGO/opencv_4.2.0_aragoj7.tar.gz;name=opencv;subdir=${S}/opencv\
"
SRC_URI[dlr.sha256sum] = "ec04d394bd1e0a3e7d988f1fa86ed50311fe1a0a957daef4d05e38e821dedec6"
SRC_URI[tflite.sha256sum] = "8dc66384e2f43af55234425d7a594ebada1bb42c2d2444cef27c2b651ff9887a"
SRC_URI[ort.sha256sum] = "a9e147bf28bbd793605874ae69e855bbb2bd7825a8042b4a38337b3a0a43146d"
SRC_URI[tfl_lib.sha256sum] = "439ee74eb4e7da842709c645c96cfd5fe0c44c89a49bd1e9fd650e6e6a8d3400"
SRC_URI[ort_lib.sha256sum] = "a224a93be3e3fc75b5cd4268e6607abc05d0faa413b6d2b322ecc759244f0e24"
SRC_URI[opencv.sha256sum] = "4122073c37e3dd268fa814b6a53510325a1e6636aa3aea9d02ab79f42b4355bd"

do_cp_downloaded_build_deps() {
    mv ${S}/tfl_lib/*/* ${S}/tfl_lib
    mv ${S}/ort_lib/*/* ${S}/ort_lib
    mv ${S}/opencv/*/* ${S}/opencv
}
addtask cp_downloaded_build_deps after do_unpack before do_patch

DEPENDS += "python3-pip-native"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx"

export TARGET_FS = "${WORKDIR}/recipe-sysroot"

PY_DST_DIR="${D}${libdir}/python3.10/site-packages"
LIB_DST_DIR="${D}${libdir}"

FILES:${PN}-staticdev += "${libdir}/tflite_2.12/"
FILES:${PN} += "${libdir}/*.so*"
FILES:${PN} += "${libdir}/python3.10/*"
FILES:${PN} += "${includedir}"
FILES:${PN} += "/usr/dlr/"

do_install() {
    pip3 install  --no-deps --platform linux_aarch64 ${S}/tflite/tflite_runtime-2.12.0-cp310-cp310-linux_aarch64.whl --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/dlr/dlr-1.13.0-py3-none-any.whl  --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/ort/onnxruntime_tidl-1.14.0-cp310-cp310-linux_aarch64.whl  --target ${PY_DST_DIR} --disable-pip-version-check

    install -d ${D}${includedir}
    install -d ${LIB_DST_DIR}

    cp -r ${S}/tfl_lib/tensorflow  ${D}${includedir}/
    cp -r ${S}/tfl_lib/tflite_2.12  ${LIB_DST_DIR}/
    cp ${S}/tfl_lib/libtensorflow-lite.a ${LIB_DST_DIR}/

    cp   ${S}/ort_lib/libonnxruntime.so.1.14.0  ${LIB_DST_DIR}/libonnxruntime.so.1.14.0
    ln -s -r ${LIB_DST_DIR}/libonnxruntime.so.1.14.0 ${LIB_DST_DIR}/libonnxruntime.so
    rm -rf  ${S}/ort_lib/onnxruntime/csharp
    cp -r  ${S}/ort_lib/onnxruntime ${D}${includedir}/

    cp -r ${S}/opencv/opencv-4.2.0  ${D}${includedir}/

    mkdir -p ${D}/usr/dlr
    ln -s -r ${libdir}/python3.10/site-packages/dlr/libdlr.so ${D}/usr/dlr/libdlr.so
    ln -s -r /usr/dlr/libdlr.so ${LIB_DST_DIR}/libdlr.so
}

