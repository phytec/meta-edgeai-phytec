SUMMARY = "Open Source DL/ML runtime Modules"
DESCRIPTION = "Open Source DL/ML runtime Modules like TF-LITE, ONNX and TVM Runtime. Supports both Python and CPP APIs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}/src"
PR:append = "_edgeai_5"

SRC_URI = "https://software-dl.ti.com/jacinto7/esd/tidl-tools/12_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_runtime-2.12.0-cp314-cp314-linux_aarch64.whl;name=tflite;subdir=${S}/tflite\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/12_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnxruntime_tidl-1.23.0-cp314-cp314-linux_aarch64.whl;name=ort;subdir=${S}/ort\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/12_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_2.12_aragoj7.tar.gz;name=tfl_lib;subdir=${S}/tfl_lib\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/12_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnx_1.23.0_aragoj7.tar.gz;name=ort_lib;subdir=${S}/ort_lib\
           https://software-dl.ti.com/mctools/esd/tvm/c7x/am62d/12_2/ti_tvm_am62x-3.0.0-cp314-cp314-linux_aarch64.whl;name=tvm;subdir=${S}/tvm\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/12_00_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tidlruntime-0.1.0-cp314-cp314-linux_aarch64.whl;name=tidlrt;subdir=${S}/tidlrt\
           "

SRC_URI[tflite.sha256sum] = "7c60aae35b4c22ad6485fa68a9017feb549f08eba58ed62dbb074c8d98447c61"
SRC_URI[ort.sha256sum] = "719e03cffb61f56de78561fe35ba58c5fc997bf2a2aade51e8b07b71fb419e70"
SRC_URI[tfl_lib.sha256sum] = "e98bc1c793ee9e0de69cc56ad5e9098a048def1683ceda0159798827b77bd1ef"
SRC_URI[ort_lib.sha256sum] = "5edc48315cb8d69e14e315cb06dad0845c9286a3ba02e6b339b56ae62a027d5d"
SRC_URI[tvm.sha256sum] = "ed6cf253b13b9e3208c702b0802ee8e87d32d94ad29e392d877c01aaeeeb41d5"
SRC_URI[tidlrt.sha256sum] = "e328bb35c5d0016ba32d72032c1318f27c44a38cce142b47d3eeb9dd52a3c09f"

do_cp_downloaded_build_deps() {
    mv ${S}/tfl_lib/*/* ${S}/tfl_lib
    mv ${S}/ort_lib/*/* ${S}/ort_lib
}
addtask cp_downloaded_build_deps after do_unpack before do_patch

DEPENDS += "unzip-native cnpy yaml-cpp"

RDEPENDS:${PN} += " \
     python3-mldtypes \
     python3-decorator \
     python3-graphviz \
     python3-attrs \
     python3-psutil \
     python3-typing-extensions \
"

COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j722s|j742s2|am62axx|am62dxx"

inherit python3-dir

FILES:${PN}-staticdev += "${libdir}/tflite_2.12/"
FILES:${PN}-staticdev += "${libdir}/*.a"
FILES:${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/tidlruntime/lib/*.a"
FILES:${PN} += "${libdir}/*.so*"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/*"
FILES:${PN} += "${includedir}"

INSANE_SKIP:${PN} += "already-stripped"

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    unzip -d ${D}${PYTHON_SITEPACKAGES_DIR} ${S}/tflite/tflite_runtime-2.12.0-cp314-cp314-linux_aarch64.whl
    unzip -d ${D}${PYTHON_SITEPACKAGES_DIR} ${S}/ort/onnxruntime_tidl-1.23.0-cp314-cp314-linux_aarch64.whl
    unzip -d ${D}${PYTHON_SITEPACKAGES_DIR} ${S}/tidlrt/tidlruntime-0.1.0-cp314-cp314-linux_aarch64.whl
    unzip -d ${D}${PYTHON_SITEPACKAGES_DIR} ${S}/tvm/ti_tvm_am62x-3.0.0-cp314-cp314-linux_aarch64.whl

    install -d ${D}${includedir}
    install -d ${D}${libdir}

    cp -r ${S}/tfl_lib/tensorflow  ${D}${includedir}/
    cp -r ${S}/tfl_lib/tflite_2.12  ${D}${libdir}/
    cp ${S}/tfl_lib/libtensorflow-lite.a ${D}${libdir}/

    cp   ${S}/ort_lib/libonnxruntime.so.1.23.0  ${D}${libdir}/
    ln -s -r ${D}${libdir}/libonnxruntime.so.1.23.0 ${D}${libdir}/libonnxruntime.so.1
    ln -s -r ${D}${libdir}/libonnxruntime.so.1 ${D}${libdir}/libonnxruntime.so
    rm -rf  ${S}/ort_lib/onnxruntime/csharp
    cp -r  ${S}/ort_lib/onnxruntime ${D}${includedir}/

    ln -s -r ${D}${PYTHON_SITEPACKAGES_DIR}/tidlruntime/include ${D}${includedir}/tidlruntime
    ln -s -r ${D}${PYTHON_SITEPACKAGES_DIR}/tidlruntime/lib/libtidlruntime.a ${D}${libdir}/libtidlruntime.a

    install -d ${D}${includedir}/tvm/tvm
    ln -s -r ${D}${PYTHON_SITEPACKAGES_DIR}/tvm/libtvm.so ${D}${libdir}/libtvm.so
    ln -s -r ${D}${PYTHON_SITEPACKAGES_DIR}/tvm/libtvm_runtime.so ${D}${libdir}/libtvm_runtime.so
    cd ${D}${PYTHON_SITEPACKAGES_DIR}/tvm/
    cp --parents $(find . -name "*.h*") ${D}${includedir}/tvm/tvm
    cd -
}

