SUMMARY = "TIDL Runtime Modules"
DESCRIPTION = "TIDL Runtime Modules like TIDL-RT, TF-LITE Delegate Library and ONNXRT Execution provider Library"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/git"
PR:append = "_edgeai_0"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"
PV="${SRCPV}"

SRCREV_FORMAT="default"
SRCREV_arm-tidl="${AUTOREV}"
SRCREV_concerto="${AUTOREV}"
SRCREV_onnxruntime="134edd824c834936690c23dde585c4eeaa74bdd4"
SRCREV_tensorflow="233657497d2735cae9e840df9e650e268149070d"

SRC_URI = " \
    git://git.ti.com/processor-sdk-vision/arm-tidl.git;branch=master;protocol=git;name=arm-tidl;destsuffix=git/arm-tidl \
    git://git.ti.com/processor-sdk/concerto.git;branch=main;protocol=git;name=concerto;destsuffix=git/concerto \
    git://github.com/TexasInstruments/onnxruntime;branch=tidl-j7;protocol=https;name=onnxruntime;destsuffix=git/onnxruntime  \
    git://github.com/TexasInstruments/tensorflow;branch=tidl-j7-2.8;protocol=https;name=tensorflow;destsuffix=git/tensorflow  \
    https://github.com/protocolbuffers/protobuf/releases/download/v3.11.3/protobuf-cpp-3.11.3.tar.gz;name=protobuf;subdir=git/protobuf-3.11.3 \
"
SRC_URI[protobuf.sha256sum] = "9ffb1fe6091240f2e7bcaca5b65fb19cb7b951ffc9a60d8b2c9d73f89f0e0a51"

do_cp_downloaded_build_deps() {
    mv ${S}/protobuf-3.11.3/*/* ${S}/protobuf-3.11.3
}
addtask cp_downloaded_build_deps after do_unpack before do_patch

PLAT_SOC = ""
PLAT_SOC:j721e-evm = "j721e"
PLAT_SOC:j721e-hs-evm = "j721e"
PLAT_SOC:j721s2-evm = "j721s2"
PLAT_SOC:j721s2-hs-evm = "j721s2"
PLAT_SOC:j784s4-evm = "j784s4"
PLAT_SOC:j784s4-hs-evm = "j784s4"
PLAT_SOC:am62axx-evm = "am62a"

DEPENDS += "ti-vision-apps"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

export TARGET_FS = "${WORKDIR}/recipe-sysroot"

FILES:${PN} += "/opt/*"
FILES:${PN} += "${libdir}/*"
FILES:${PN} += "${includedir}/*"

EXTRA_OEMAKE += "-C ${S}/arm-tidl"

do_compile() {
    IVISION_PATH=${TARGET_FS}${includedir}/processor_sdk/ivision \
    VISION_APPS_PATH=${TARGET_FS}${includedir}/processor_sdk/vision_apps \
    APP_UTILS_PATH=${TARGET_FS}${includedir}/processor_sdk/app_utils \
    TIOVX_PATH=${TARGET_FS}${includedir}/processor_sdk/tiovx \
    LINUX_FS_PATH=${TARGET_FS} \
    CONCERTO_ROOT=${S}/concerto \
    TF_REPO_PATH=${S}/tensorflow \
    ONNX_REPO_PATH=${S}/onnxruntime \
    TIDL_PROTOBUF_PATH=${S}/protobuf-3.11.3 \
    GCC_LINUX_ARM_ROOT= \
    TARGET_SOC=${PLAT_SOC} \
    CROSS_COMPILE_LINARO=aarch64-oe-linux- \
    LINUX_SYSROOT=${STAGING_DIR_TARGET} \
    TREAT_WARNINGS_AS_ERROR=0 \
    oe_runmake
}

LIB_DST_DIR="${D}${libdir}"
INC_DST_DIR="${D}${includedir}"
OPT_DST_DIR="${D}/opt"

TIDL_SOC_NAME = ""
TIDL_SOC_NAME:j721e-evm = "J7"
TIDL_SOC_NAME:j721e-hs-evm = "J7"
TIDL_SOC_NAME:j721s2-evm = "J721S2"
TIDL_SOC_NAME:j721s2-hs-evm = "J721S2"
TIDL_SOC_NAME:j784s4-evm = "J784S4"
TIDL_SOC_NAME:j784s4-hs-evm = "J784S4"
TIDL_SOC_NAME:am62axx-evm = "AM62A"

do_install() {
    install -d ${LIB_DST_DIR}
    cp ${S}/arm-tidl/rt/out/${TIDL_SOC_NAME}/A72/LINUX/release/libvx_tidl_rt.so.1.0 ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libvx_tidl_rt.so.1.0 ${LIB_DST_DIR}/libvx_tidl_rt.so
    cp ${S}/arm-tidl/tfl_delegate/out/${TIDL_SOC_NAME}/A72/LINUX/release/libtidl_tfl_delegate.so.1.0 ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libtidl_tfl_delegate.so.1.0 ${LIB_DST_DIR}/libtidl_tfl_delegate.so
    cp ${S}/arm-tidl/onnxrt_ep/out/${TIDL_SOC_NAME}/A72/LINUX/release/libtidl_onnxrt_EP.so.1.0 ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libtidl_onnxrt_EP.so.1.0 ${LIB_DST_DIR}/libtidl_onnxrt_EP.so

    install -d ${INC_DST_DIR}
    cp ${S}/arm-tidl/rt/inc/itidl_rt.h  ${INC_DST_DIR}/
    cp ${S}/arm-tidl/rt/inc/itvm_rt.h ${INC_DST_DIR}/

    install -d ${OPT_DST_DIR}/tidl_test
    cp ${S}/arm-tidl/rt/out/${TIDL_SOC_NAME}/A72/LINUX/release/TI_DEVICE_armv8_test_dl_algo_host_rt.out ${OPT_DST_DIR}/tidl_test/

}

INSANE_SKIP:${PN} += "ldflags"
