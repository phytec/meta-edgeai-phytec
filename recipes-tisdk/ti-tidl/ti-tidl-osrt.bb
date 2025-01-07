SUMMARY = "Open Source DL/ML runtime Modules"
DESCRIPTION = "Open Source DL/ML runtime Modules like TF-LITE and ONNX Runtime, NEO-AI-DLR. Supports both Python and CPP APIs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"
PR:append = "_edgeai_4"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"

SRC_URI = "https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/dlr-1.13.0-py3-none-any.whl;name=dlr;subdir=${S}/dlr\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnxruntime_tidl-1.14.0-cp310-cp310-linux_aarch64.whl;name=ort;subdir=${S}/ort\
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/tflite_2.12_aragoj7.tar.gz;name=tfl_lib;subdir=${S} \
           https://software-dl.ti.com/jacinto7/esd/tidl-tools/09_02_00_00/OSRT_TOOLS/ARM_LINUX/ARAGO/onnx_1.14.0_aragoj7.tar.gz;name=ort_lib;subdir=${S} \
"
SRC_URI[dlr.sha256sum] = "334321201e8f30daecf18d562f83732dced0bb2efc21e556f45565ba6c3e21ee"
SRC_URI[ort.sha256sum] = "1ffec602551015062dc51557ca406252ec781d7115b4f15de7b7ef8130c7c60f"
SRC_URI[tfl_lib.sha256sum] = "439ee74eb4e7da842709c645c96cfd5fe0c44c89a49bd1e9fd650e6e6a8d3400"
SRC_URI[ort_lib.sha256sum] = "7efbad0cb6d0793ba4db843055774b7a2bb746d8265058983482581305de3cd8"

DEPENDS += "python3-pip-native"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx"
COMPATIBLE_MACHINE += "|phyboard-izar-am68x-2"

export TARGET_FS = "${WORKDIR}/recipe-sysroot"

PY_DST_DIR="${D}${libdir}/python3.10/site-packages"
LIB_DST_DIR="${D}${libdir}"
TFL_LIB_SRC="${S}/tflite_2.12_aragoj7"
ORT_LIB_SRC="${S}/onnx_1.14.0_aragoj7"

FILES:${PN}-staticdev += "${libdir}/tflite_2.12/"
FILES:${PN} += "${libdir}/*.so*"
FILES:${PN} += "${libdir}/python3.10/*"
FILES:${PN} += "${includedir}"

do_install() {
    pip3 install  --no-deps --platform linux_aarch64 ${TFL_LIB_SRC}/tflite_runtime-2.12.0-cp310-cp310-linux_aarch64.whl --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/dlr/dlr-1.13.0-py3-none-any.whl  --target ${PY_DST_DIR} --disable-pip-version-check
    pip3 install  --no-deps --platform linux_aarch64 ${S}/ort/onnxruntime_tidl-1.14.0-cp310-cp310-linux_aarch64.whl  --target ${PY_DST_DIR} --disable-pip-version-check

    install -d ${D}${includedir}
    install -d ${LIB_DST_DIR}

    cp -r ${TFL_LIB_SRC}/tensorflow  ${D}${includedir}/
    cp -r ${TFL_LIB_SRC}/tflite_2.12  ${LIB_DST_DIR}/
    cp ${TFL_LIB_SRC}/libtensorflow-lite.a ${LIB_DST_DIR}/

    cp   ${ORT_LIB_SRC}/libonnxruntime.so.1.14.0  ${LIB_DST_DIR}/libonnxruntime.so.1.14.0
    ln -s -r ${LIB_DST_DIR}/libonnxruntime.so.1.14.0 ${LIB_DST_DIR}/libonnxruntime.so
    rm -rf  ${ORT_LIB_SRC}/onnxruntime/csharp
    cp -r  ${ORT_LIB_SRC}/onnxruntime ${D}${includedir}/

    ln -s -r ${libdir}/python3.10/site-packages/dlr/libdlr.so ${LIB_DST_DIR}/libdlr.so
}

