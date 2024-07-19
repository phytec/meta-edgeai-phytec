SUMMARY = "Open Source DL/ML runtime Modules"
DESCRIPTION = "Open Source DL/ML runtime Modules like TF-LITE and ONNX Runtime, NEO-AI-DLR. Supports both Python and CPP APIs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"
PR:append = "_edgeai_4"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"

SRC_URI = "https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/dlr-1.13.0-py3-none-any.whl;name=dlr;subdir=${S}/dlr\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_runtime-2.12.0-cp312-cp312-linux_aarch64.whl;name=tflite;subdir=${S}/tflite\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_01/OSRT_TOOLS/ARM_LINUX/ARAGO/onnxruntime_tidl-1.14.0-cp312-cp312-linux_aarch64.whl;name=ort;subdir=${S}/ort\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_2.12_aragoj7.tar.gz;name=tfl_lib;subdir=${S}/tfl_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/10_00_00_01/OSRT_TOOLS/ARM_LINUX/ARAGO/onnx_1.14.0_aragoj7.tar.gz;name=ort_lib;subdir=${S}/ort_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/opencv_4.2.0_aragoj7.tar.gz;name=opencv;subdir=${S}/opencv\
"
SRC_URI[dlr.sha256sum] = "84ce4baa5541b8cb3705dd33388cea6c6aaf34539dd978cbff23884a2f91dfc4"
SRC_URI[tflite.sha256sum] = "feb00e834c537bc0c5195eefcacca14d03b16d697ae93138136a13cc73328c2e"
SRC_URI[ort.sha256sum] = "e0cb61cce493a9cdf87a49b783c7e9932206a3fa28114ec769538819fb06262b"
SRC_URI[tfl_lib.sha256sum] = "439ee74eb4e7da842709c645c96cfd5fe0c44c89a49bd1e9fd650e6e6a8d3400"
SRC_URI[ort_lib.sha256sum] = "0b89a43db172d2f6ed0f67a8453c93d10e390d8386d444ab4848a2aea867e075"
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
    pip3 install  --no-deps --platform linux_aarch64 ${S}/ort/onnxruntime_tidl-1.14.0-cp312-cp312-linux_aarch64.whl  --target ${PY_DST_DIR} --disable-pip-version-check

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
    ln -s -r ${libdir}/python3.12/site-packages/dlr/libdlr.so ${LIB_DST_DIR}/libdlr.so
}

