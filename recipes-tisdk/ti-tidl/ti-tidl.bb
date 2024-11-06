SUMMARY = "TIDL Runtime Modules"
DESCRIPTION = "TIDL Runtime Modules like TIDL-RT, TF-LITE Delegate Library and ONNXRT Execution provider Library"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/git"
PR:append = "_edgeai_2"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
LICENSE = "MIT"
PV="1.0.0"

SRCREV_FORMAT="default"
SRCREV_arm-tidl="78a9795ab5a4acb070f7ef91072114fdaf1e968e"
SRCREV_concerto="68d69c7d7623590b7f0311277708699428387709"
SRCREV_onnxruntime="b07b733888500a37064f560f3d61d5c8ab1201c7"
SRCREV_tensorflow="422156a973b23bab6b86176a245a66193dccb995"

SRC_URI = " \
    git://git.ti.com/git/processor-sdk-vision/arm-tidl.git;branch=master;protocol=https;name=arm-tidl;destsuffix=git/arm-tidl \
    git://git.ti.com/git/processor-sdk/concerto.git;branch=main;protocol=https;name=concerto;destsuffix=git/concerto \
    git://github.com/TexasInstruments/onnxruntime;branch=tidl-1.14;protocol=https;name=onnxruntime;destsuffix=git/onnxruntime  \
    git://github.com/TexasInstruments/tensorflow;branch=tidl-j7-2.12;protocol=https;name=tensorflow;destsuffix=git/tensorflow  \
    https://github.com/protocolbuffers/protobuf/releases/download/v3.20.2/protobuf-cpp-3.20.2.tar.gz;name=protobuf;subdir=git/protobuf-3.20.2 \
"
SRC_URI[protobuf.sha256sum] = "a0167e2ba24bba0a180fbc9392f3a43e749d7a26e630fe9c1a1ba32a53675ac3"

do_cp_downloaded_build_deps() {
    mv ${S}/protobuf-3.20.2/*/* ${S}/protobuf-3.20.2
}
addtask cp_downloaded_build_deps after do_unpack before do_patch

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:j742s2 = "j742s2"
PLAT_SOC:am62axx = "am62a"

CPU = "A72"
CPU:am62axx = "A53"
CPU:j722s = "A53"

DEPENDS += "ti-vision-apps"

COMPATIBLE_MACHINE = "j721e|j721s2|j784s4|j722s|j742s2|am62axx"

export TARGET_FS = "${WORKDIR}/recipe-sysroot"

FILES:${PN} += "/opt/*"
FILES:${PN} += "${libdir}/*"
FILES:${PN} += "${includedir}/*"

EXTRA_OEMAKE += "-C ${S}/arm-tidl"

do_compile() {
    ln -snf ${TARGET_FS} ${WORKDIR}/targetfs

    PSDK_INSTALL_PATH=${WORKDIR} \
    IVISION_PATH=${TARGET_FS}${includedir}/processor_sdk/ivision \
    VISION_APPS_PATH=${TARGET_FS}${includedir}/processor_sdk/vision_apps \
    APP_UTILS_PATH=${TARGET_FS}${includedir}/processor_sdk/app_utils \
    TIOVX_PATH=${TARGET_FS}${includedir}/processor_sdk/tiovx \
    LINUX_FS_PATH=${TARGET_FS} \
    CONCERTO_ROOT=${S}/concerto \
    TF_REPO_PATH=${S}/tensorflow \
    ONNX_REPO_PATH=${S}/onnxruntime \
    TIDL_PROTOBUF_PATH=${S}/protobuf-3.20.2 \
    GCC_LINUX_ARM_ROOT= \
    TARGET_SOC=${PLAT_SOC} \
    CROSS_COMPILE_LINARO=aarch64-oe-linux- \
    LINUX_SYSROOT_ARM=${STAGING_DIR_TARGET} \
    TREAT_WARNINGS_AS_ERROR=0 \
    oe_runmake
}

LIB_DST_DIR="${D}${libdir}"
INC_DST_DIR="${D}${includedir}"
OPT_DST_DIR="${D}/opt"

TIDL_SOC_NAME = ""
TIDL_SOC_NAME:j721e = "J721E"
TIDL_SOC_NAME:j721s2 = "J721S2"
TIDL_SOC_NAME:j784s4 = "J784S4"
TIDL_SOC_NAME:j722s = "J722S"
TIDL_SOC_NAME:j742s2 = "J742S2"
TIDL_SOC_NAME:am62axx = "AM62A"

do_install() {
    install -d ${LIB_DST_DIR}
    cp ${S}/arm-tidl/rt/out/${TIDL_SOC_NAME}/${CPU}/LINUX/release/libvx_tidl_rt.so.1.0 ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libvx_tidl_rt.so.1.0 ${LIB_DST_DIR}/libvx_tidl_rt.so
    cp ${S}/arm-tidl/tfl_delegate/out/${TIDL_SOC_NAME}/${CPU}/LINUX/release/libtidl_tfl_delegate.so.1.0 ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libtidl_tfl_delegate.so.1.0 ${LIB_DST_DIR}/libtidl_tfl_delegate.so
    cp ${S}/arm-tidl/onnxrt_ep/out/${TIDL_SOC_NAME}/${CPU}/LINUX/release/libtidl_onnxrt_EP.so.1.0 ${LIB_DST_DIR}/
    ln -s -r ${LIB_DST_DIR}/libtidl_onnxrt_EP.so.1.0 ${LIB_DST_DIR}/libtidl_onnxrt_EP.so

    install -d ${INC_DST_DIR}
    cp ${S}/arm-tidl/rt/inc/itidl_rt.h  ${INC_DST_DIR}/
    cp ${S}/arm-tidl/rt/inc/itvm_rt.h ${INC_DST_DIR}/

    install -d ${OPT_DST_DIR}/tidl_test
    cp ${S}/arm-tidl/rt/out/${TIDL_SOC_NAME}/${CPU}/LINUX/release/TI_DEVICE_armv8_test_dl_algo_host_rt.out ${OPT_DST_DIR}/tidl_test/

}

INSANE_SKIP:${PN} += "ldflags"
