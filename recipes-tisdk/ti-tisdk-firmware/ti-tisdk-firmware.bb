SUMMARY = "OpenVX Middleware library and compatible PSDK RTOS Firmware"
DESCRIPTION = "Builds tivision_apps user space library and installs prebuilt PSDK RTOS Firmware"

PR:append = "_edgeai_0"

LICENSE = "TI-TFL & BSD-2-Clause & BSD-3-Clause & BSD-4-Clause & MIT & Apache-2.0 & Apache-2.0-with-LLVM-exception & \
           Khronos & Hewlett-Packard & Patrick-Powell & FreeType & Zlib & CC0-1.0 & OpenSSL"

LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-ti/licenses/TI-TFL;md5=a1b59cb7ba626b9dbbcbf00f3fbc438a \
                    file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f \
                    file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COMMON_LICENSE_DIR}/BSD-4-Clause;md5=624d9e67e8ac41a78f6b6c2c55a83a2b \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://${COMMON_LICENSE_DIR}/Apache-2.0-with-LLVM-exception;md5=0bcd48c3bdfef0c9d9fd17726e4b7dab \
                    file://repo/tiovx/include/VX/vx.h;beginline=1;endline=15;md5=37315206223081f32a5b9aaaf912f637 \
                    file://${COREBASE}/../meta-ti/licenses/Hewlett-Packard;md5=a07676ee09f5bfec457eb5ea75921d01 \
                    file://${COREBASE}/../meta-ti/licenses/Patrick-Powell;md5=7e10716f13cff502f3cf6ebf8fe29c1e \
                    file://${COMMON_LICENSE_DIR}/FreeType;md5=b4eb496da445cddb5cbec10203ee4964 \
                    file://${COMMON_LICENSE_DIR}/Zlib;md5=87f239f408daca8a157858e192597633 \
                    file://${COMMON_LICENSE_DIR}/CC0-1.0;md5=0ceb3372c9595f0a8067e55da801e4a1 \
                    file://${COREBASE}/meta/files/common-licenses/OpenSSL;md5=4eb1764f3e65fafa1a25057f9082f2ae \
                    "

SRC_URI = "repo://git.ti.com/processor-sdk/psdk_repo_manifests.git;protocol=git;branch=refs/tags/REL.PSDK.JACINTO.08.06.00.08;manifest=vision_apps_yocto.xml"

FILES:${PN} += "/opt/*"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

#PTK needs:
# EGL/egl.h
# glm/glm.hpp
# IL/il.h
# /usr/include/freetype2/ft2build.h
# ti_rpmsg_char.h
# dlr.h

DEPENDS += "glm devil freetype ti-rpmsg-char repo-native"
DEPENDS:append:j721e-evm = " ti-img-rogue-umlibs"
DEPENDS:append:j721e-hs-evm = " ti-img-rogue-umlibs"
DEPENDS:append:j721s2-evm = " ti-img-rogue-umlibs"
DEPENDS:append:j721s2-hs-evm = " ti-img-rogue-umlibs"
DEPENDS:append:j784s4-evm = " ti-img-rogue-umlibs"
DEPENDS:append:j784s4-hs-evm = " ti-img-rogue-umlibs"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm"

inherit update-alternatives

PLAT_SOC = ""
PLAT_SOC:j721e-evm = "j721e"
PLAT_SOC:j721e-hs-evm = "j721e"
PLAT_SOC:j721s2-evm = "j721s2"
PLAT_SOC:j721s2-hs-evm = "j721s2"
PLAT_SOC:j784s4-evm = "j784s4"
PLAT_SOC:j784s4-hs-evm = "j784s4"
PLAT_SOC:am62axx-evm = "am62a"

S = "${WORKDIR}"

EXTRA_OEMAKE += "-C ${S}/repo/vision_apps"

do_fetch[depends] += "repo-native:do_populate_sysroot"

do_compile() {
    GCC_LINUX_ARM_ROOT= \
    GCC_LINUX_ARM_ROOT_A72= \
    LINUX_FS_PATH=${STAGING_DIR_TARGET} \
    SOC=${PLAT_SOC} \
    oe_runmake yocto_build
}

FW_SRC_DIR="${S}/repo/psdk_fw/${PLAT_SOC}"
FW_DST_DIR="${D}${nonarch_base_libdir}/firmware"

require recipes-ti/includes/ti-paths.inc

DEPENDS:append = " ${@ '' if d.getVar('TI_SECURE_DEV_PKG_K3') else 'ti-k3-secdev-native' }"
TI_SECURE_DEV_PKG = "${@ d.getVar('TI_SECURE_DEV_PKG_K3') or '${TI_K3_SECDEV_INSTALL_DIR}' }"
export TI_SECURE_DEV_PKG

sign_fw() {
    suffix=.signed
    files_list=$(find ${FW_BIN_DIR} -type f ! -name "*${suffix}")
    for name in $(echo $files_list); do
        if [ -s $name ]; then
            ${TI_SECURE_DEV_PKG}/scripts/secure-binary-image.sh $name $name$suffix        
        else
            touch $name$suffix
        fi
    done
}

do_install:prepend() {
    if [ ${MACHINE} != "am62axx-evm" ]; then
        # Sign prebuilt firmware binaries for HS platforms
        FW_BIN_DIR=${FW_SRC_DIR}/vision_apps_eaik sign_fw
        FW_BIN_DIR=${FW_SRC_DIR}/vision_apps_evm sign_fw
    fi
}

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    SOC=${PLAT_SOC} LINUX_FS_STAGE_PATH=${D} oe_runmake yocto_install

    if [ ${MACHINE} != "am62axx-evm" ]; then
        # Copy prebuilt firmware from repo to filesystem
        install -d ${FW_DST_DIR}
        cp ${CP_ARGS} ${FW_SRC_DIR}/* ${FW_DST_DIR}/.
    fi
}

# Set up names for the firmwares
ALTERNATIVE:${PN}:j7 = "\
                    j7-main-r5f0_0-fw \
                    j7-main-r5f0_1-fw \
                    j7-c66_0-fw \
                    j7-c66_1-fw \
                    j7-c71_0-fw\
                    j7-mcu-r5f0_1-fw \
                    j7-main-r5f1_0-fw \
                    j7-main-r5f1_1-fw \
                    j7-main-r5f0_0-fw-sec \
                    j7-main-r5f0_1-fw-sec \
                    j7-main-r5f1_0-fw-sec \
                    j7-main-r5f1_1-fw-sec \
                    j7-c66_0-fw-sec \
                    j7-c66_1-fw-sec \
                    j7-c71_0-fw-sec \
                    "

# These commented out are not overwritten by this recipe at this time
#ALTERNATIVE:${PN}:j7 += "\
#                    j7-mcu-r5f0_0-fw \
#                    "

ALTERNATIVE:${PN}:j721s2-evm = "\
                    j721s2-main-r5f0_0-fw \
                    j721s2-main-r5f0_1-fw \
                    j721s2-c71_0-fw \
                    j721s2-c71_1-fw \
                    j721s2-mcu-r5f0_1-fw \
                    j721s2-main-r5f1_0-fw \
                    j721s2-main-r5f1_1-fw \
                    j721s2-main-r5f0_0-fw-sec \
                    j721s2-main-r5f0_1-fw-sec \
                    j721s2-c71_0-fw-sec \
                    j721s2-c71_1-fw-sec \
                    j721s2-main-r5f1_0-fw-sec \
                    j721s2-main-r5f1_1-fw-sec \
                    "

# These commented out are not overwritten by this recipe at this time
#ALTERNATIVE:${PN}:j721s2-evm += "\
#                    j721s2-mcu-r5f0_0-fw \
#                    "

ALTERNATIVE:${PN}:j784s4-evm = "\
                    j784s4-main-r5f0_0-fw \
                    j784s4-main-r5f0_1-fw \
                    j784s4-main-r5f1_0-fw \
                    j784s4-main-r5f1_1-fw \
                    j784s4-main-r5f2_0-fw \
                    j784s4-main-r5f2_1-fw \
                    j784s4-c71_0-fw \
                    j784s4-c71_1-fw \
                    j784s4-c71_2-fw \
                    j784s4-c71_3-fw \
                    j784s4-mcu-r5f0_1-fw \
                    j784s4-main-r5f0_0-fw-sec \
                    j784s4-main-r5f0_1-fw-sec \
                    j784s4-main-r5f1_0-fw-sec \
                    j784s4-main-r5f1_1-fw-sec \
                    j784s4-main-r5f2_0-fw-sec \
                    j784s4-main-r5f2_1-fw-sec \
                    j784s4-c71_0-fw-sec \
                    j784s4-c71_1-fw-sec \
                    j784s4-c71_2-fw-sec \
                    j784s4-c71_3-fw-sec \
                    "

# These commented out are not overwritten by this recipe at this time
#ALTERNATIVE:${PN}:j784s4-evm += "\
#                    j784s4-mcu-r5f0_0-fw \
#                    "

# Set up link names for the firmwares

ALTERNATIVE_LINK_NAME[j7-mcu-r5f0_0-fw] = "${base_libdir}/firmware/j7-mcu-r5f0_0-fw"
ALTERNATIVE_LINK_NAME[j7-mcu-r5f0_1-fw] = "${base_libdir}/firmware/j7-mcu-r5f0_1-fw"
ALTERNATIVE_LINK_NAME[j7-main-r5f0_0-fw] = "${base_libdir}/firmware/j7-main-r5f0_0-fw"
ALTERNATIVE_LINK_NAME[j7-main-r5f0_1-fw] = "${base_libdir}/firmware/j7-main-r5f0_1-fw"
ALTERNATIVE_LINK_NAME[j7-main-r5f1_0-fw] = "${base_libdir}/firmware/j7-main-r5f1_0-fw"
ALTERNATIVE_LINK_NAME[j7-main-r5f1_1-fw] = "${base_libdir}/firmware/j7-main-r5f1_1-fw"
ALTERNATIVE_LINK_NAME[j7-c66_0-fw] = "${base_libdir}/firmware/j7-c66_0-fw"
ALTERNATIVE_LINK_NAME[j7-c66_1-fw] = "${base_libdir}/firmware/j7-c66_1-fw"
ALTERNATIVE_LINK_NAME[j7-c71_0-fw] = "${base_libdir}/firmware/j7-c71_0-fw"

ALTERNATIVE_LINK_NAME[j7-main-r5f0_0-fw-sec] = "${base_libdir}/firmware/j7-main-r5f0_0-fw-sec"
ALTERNATIVE_LINK_NAME[j7-main-r5f0_1-fw-sec] = "${base_libdir}/firmware/j7-main-r5f0_1-fw-sec"
ALTERNATIVE_LINK_NAME[j7-main-r5f1_0-fw-sec] = "${base_libdir}/firmware/j7-main-r5f1_0-fw-sec"
ALTERNATIVE_LINK_NAME[j7-main-r5f1_1-fw-sec] = "${base_libdir}/firmware/j7-main-r5f1_1-fw-sec"
ALTERNATIVE_LINK_NAME[j7-c66_0-fw-sec] = "${base_libdir}/firmware/j7-c66_0-fw-sec"
ALTERNATIVE_LINK_NAME[j7-c66_1-fw-sec] = "${base_libdir}/firmware/j7-c66_1-fw-sec"
ALTERNATIVE_LINK_NAME[j7-c71_0-fw-sec] = "${base_libdir}/firmware/j7-c71_0-fw-sec"

ALTERNATIVE_LINK_NAME[j721s2-mcu-r5f0_0-fw] = "${base_libdir}/firmware/j721s2-mcu-r5f0_0-fw"
ALTERNATIVE_LINK_NAME[j721s2-mcu-r5f0_1-fw] = "${base_libdir}/firmware/j721s2-mcu-r5f0_1-fw"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f0_0-fw] = "${base_libdir}/firmware/j721s2-main-r5f0_0-fw"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f0_1-fw] = "${base_libdir}/firmware/j721s2-main-r5f0_1-fw"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f1_0-fw] = "${base_libdir}/firmware/j721s2-main-r5f1_0-fw"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f1_1-fw] = "${base_libdir}/firmware/j721s2-main-r5f1_1-fw"
ALTERNATIVE_LINK_NAME[j721s2-c71_0-fw] = "${base_libdir}/firmware/j721s2-c71_0-fw"
ALTERNATIVE_LINK_NAME[j721s2-c71_1-fw] = "${base_libdir}/firmware/j721s2-c71_1-fw"

ALTERNATIVE_LINK_NAME[j721s2-main-r5f0_0-fw-sec] = "${base_libdir}/firmware/j721s2-main-r5f0_0-fw-sec"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f0_1-fw-sec] = "${base_libdir}/firmware/j721s2-main-r5f0_1-fw-sec"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f1_0-fw-sec] = "${base_libdir}/firmware/j721s2-main-r5f1_0-fw-sec"
ALTERNATIVE_LINK_NAME[j721s2-main-r5f1_1-fw-sec] = "${base_libdir}/firmware/j721s2-main-r5f1_1-fw-sec"
ALTERNATIVE_LINK_NAME[j721s2-c71_0-fw-sec] = "${base_libdir}/firmware/j721s2-c71_0-fw-sec"
ALTERNATIVE_LINK_NAME[j721s2-c71_1-fw-sec] = "${base_libdir}/firmware/j721s2-c71_1-fw-sec"

ALTERNATIVE_LINK_NAME[j784s4-mcu-r5f0_0-fw] = "${base_libdir}/firmware/j784s4-mcu-r5f0_0-fw"
ALTERNATIVE_LINK_NAME[j784s4-mcu-r5f0_1-fw] = "${base_libdir}/firmware/j784s4-mcu-r5f0_1-fw"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f0_0-fw] = "${base_libdir}/firmware/j784s4-main-r5f0_0-fw"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f0_1-fw] = "${base_libdir}/firmware/j784s4-main-r5f0_1-fw"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f1_0-fw] = "${base_libdir}/firmware/j784s4-main-r5f1_0-fw"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f1_1-fw] = "${base_libdir}/firmware/j784s4-main-r5f1_1-fw"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f2_0-fw] = "${base_libdir}/firmware/j784s4-main-r5f2_0-fw"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f2_1-fw] = "${base_libdir}/firmware/j784s4-main-r5f2_1-fw"
ALTERNATIVE_LINK_NAME[j784s4-c71_0-fw] = "${base_libdir}/firmware/j784s4-c71_0-fw"
ALTERNATIVE_LINK_NAME[j784s4-c71_1-fw] = "${base_libdir}/firmware/j784s4-c71_1-fw"
ALTERNATIVE_LINK_NAME[j784s4-c71_2-fw] = "${base_libdir}/firmware/j784s4-c71_2-fw"
ALTERNATIVE_LINK_NAME[j784s4-c71_3-fw] = "${base_libdir}/firmware/j784s4-c71_3-fw"

ALTERNATIVE_LINK_NAME[j784s4-main-r5f0_0-fw-sec] = "${base_libdir}/firmware/j784s4-main-r5f0_0-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f0_1-fw-sec] = "${base_libdir}/firmware/j784s4-main-r5f0_1-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f1_0-fw-sec] = "${base_libdir}/firmware/j784s4-main-r5f1_0-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f1_1-fw-sec] = "${base_libdir}/firmware/j784s4-main-r5f1_1-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f2_0-fw-sec] = "${base_libdir}/firmware/j784s4-main-r5f2_0-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-main-r5f2_1-fw-sec] = "${base_libdir}/firmware/j784s4-main-r5f2_1-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-c71_0-fw-sec] = "${base_libdir}/firmware/j784s4-c71_0-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-c71_1-fw-sec] = "${base_libdir}/firmware/j784s4-c71_1-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-c71_2-fw-sec] = "${base_libdir}/firmware/j784s4-c71_2-fw-sec"
ALTERNATIVE_LINK_NAME[j784s4-c71_3-fw-sec] = "${base_libdir}/firmware/j784s4-c71_3-fw-sec"

# Create the firmware alternatives

ALTERNATIVE_TARGET[j7-mcu-r5f0_0-fw] = "${base_libdir}/firmware/pdk-ipc/ipc_echo_testb_mcu1_0_release_strip.xer5f"
ALTERNATIVE_TARGET[j7-mcu-r5f0_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu1_1.out"
ALTERNATIVE_TARGET[j7-main-r5f0_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_0.out"
ALTERNATIVE_TARGET[j7-main-r5f0_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_1.out"
ALTERNATIVE_TARGET[j7-main-r5f1_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_0.out"
ALTERNATIVE_TARGET[j7-main-r5f1_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_1.out"
ALTERNATIVE_TARGET[j7-c66_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c6x_1.out"
ALTERNATIVE_TARGET[j7-c66_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c6x_2.out"
ALTERNATIVE_TARGET[j7-c71_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_1.out"

ALTERNATIVE_TARGET[j7-main-r5f0_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_0.out.signed"
ALTERNATIVE_TARGET[j7-main-r5f0_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_1.out.signed"
ALTERNATIVE_TARGET[j7-main-r5f1_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_0.out.signed"
ALTERNATIVE_TARGET[j7-main-r5f1_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_1.out.signed"
ALTERNATIVE_TARGET[j7-c66_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c6x_1.out.signed"
ALTERNATIVE_TARGET[j7-c66_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c6x_2.out.signed"
ALTERNATIVE_TARGET[j7-c71_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_1.out.signed"

ALTERNATIVE_TARGET[j721s2-mcu-r5f0_0-fw] = "${base_libdir}/firmware/pdk-ipc/ipc_echo_testb_mcu1_0_release_strip.xer5f"
ALTERNATIVE_TARGET[j721s2-mcu-r5f0_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu1_1.out"
ALTERNATIVE_TARGET[j721s2-main-r5f0_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_0.out"
ALTERNATIVE_TARGET[j721s2-main-r5f0_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_1.out"
ALTERNATIVE_TARGET[j721s2-main-r5f1_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_0.out"
ALTERNATIVE_TARGET[j721s2-main-r5f1_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_1.out"
ALTERNATIVE_TARGET[j721s2-c71_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_1.out"
ALTERNATIVE_TARGET[j721s2-c71_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_2.out"

ALTERNATIVE_TARGET[j721s2-main-r5f0_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_0.out.signed"
ALTERNATIVE_TARGET[j721s2-main-r5f0_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_1.out.signed"
ALTERNATIVE_TARGET[j721s2-main-r5f1_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_0.out.signed"
ALTERNATIVE_TARGET[j721s2-main-r5f1_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_1.out.signed"
ALTERNATIVE_TARGET[j721s2-c71_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_1.out.signed"
ALTERNATIVE_TARGET[j721s2-c71_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_2.out.signed"

ALTERNATIVE_TARGET[j784s4-mcu-r5f0_0-fw] = "${base_libdir}/firmware/pdk-ipc/ipc_echo_testb_mcu1_0_release_strip.xer5f"
ALTERNATIVE_TARGET[j784s4-mcu-r5f0_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu1_1.out"
ALTERNATIVE_TARGET[j784s4-main-r5f0_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_0.out"
ALTERNATIVE_TARGET[j784s4-main-r5f0_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_1.out"
ALTERNATIVE_TARGET[j784s4-main-r5f1_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_0.out"
ALTERNATIVE_TARGET[j784s4-main-r5f1_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_1.out"
ALTERNATIVE_TARGET[j784s4-main-r5f2_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu4_0.out"
ALTERNATIVE_TARGET[j784s4-main-r5f2_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu4_1.out"
ALTERNATIVE_TARGET[j784s4-c71_0-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_1.out"
ALTERNATIVE_TARGET[j784s4-c71_1-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_2.out"
ALTERNATIVE_TARGET[j784s4-c71_2-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_3.out"
ALTERNATIVE_TARGET[j784s4-c71_3-fw] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_4.out"

ALTERNATIVE_TARGET[j784s4-main-r5f0_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_0.out.signed"
ALTERNATIVE_TARGET[j784s4-main-r5f0_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu2_1.out.signed"
ALTERNATIVE_TARGET[j784s4-main-r5f1_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_0.out.signed"
ALTERNATIVE_TARGET[j784s4-main-r5f1_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu3_1.out.signed"
ALTERNATIVE_TARGET[j784s4-main-r5f2_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu4_0.out.signed"
ALTERNATIVE_TARGET[j784s4-main-r5f2_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_mcu4_1.out.signed"
ALTERNATIVE_TARGET[j784s4-c71_0-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_1.out.signed"
ALTERNATIVE_TARGET[j784s4-c71_1-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_2.out.signed"
ALTERNATIVE_TARGET[j784s4-c71_2-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_3.out.signed"
ALTERNATIVE_TARGET[j784s4-c71_3-fw-sec] = "${base_libdir}/firmware/vision_apps_eaik/vx_app_rtos_linux_c7x_4.out.signed"


ALTERNATIVE_PRIORITY = "20"

# This is used to prevent the build system to_strip the executables
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
# This is used to prevent the build system to split the debug info in a separate file
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
# As it likely to be a different arch from the Yocto build, disable checking by adding "arch" to INSANE_SKIP
INSANE_SKIP:${PN} += "arch"

