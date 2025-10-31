SUMMARY = "OpenVX Middleware library"
DESCRIPTION = "Builds tivision_apps user space library"

PR:append = "_edgeai_4"

LICENSE = "TI-TFL & BSD-2-Clause & BSD-3-Clause & BSD-4-Clause & MIT & Apache-2.0 & Apache-2.0-with-LLVM-exception & \
           Khronos & Hewlett-Packard & Patrick-Powell & FTL & Zlib & CC0-1.0 & OpenSSL"

LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-ti/meta-ti-bsp/licenses/TI-TFL;md5=a1b59cb7ba626b9dbbcbf00f3fbc438a \
                    file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f \
                    file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COMMON_LICENSE_DIR}/BSD-4-Clause;md5=624d9e67e8ac41a78f6b6c2c55a83a2b \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://${COMMON_LICENSE_DIR}/Apache-2.0-with-LLVM-exception;md5=0bcd48c3bdfef0c9d9fd17726e4b7dab \
                    file://repo/tiovx/include/VX/vx.h;beginline=1;endline=15;md5=37315206223081f32a5b9aaaf912f637 \
                    file://${COREBASE}/../meta-ti/meta-ti-extras/licenses/Hewlett-Packard;md5=a07676ee09f5bfec457eb5ea75921d01 \
                    file://${COREBASE}/../meta-ti/meta-ti-extras/licenses/Patrick-Powell;md5=7e10716f13cff502f3cf6ebf8fe29c1e \
                    file://${COMMON_LICENSE_DIR}/FTL;md5=f0bf6b09ee8b02121ed10709d9e49d8b \
                    file://${COMMON_LICENSE_DIR}/Zlib;md5=87f239f408daca8a157858e192597633 \
                    file://${COMMON_LICENSE_DIR}/CC0-1.0;md5=0ceb3372c9595f0a8067e55da801e4a1 \
                    file://${COMMON_LICENSE_DIR}/OpenSSL;md5=4eb1764f3e65fafa1a25057f9082f2ae \
                    "

SRC_URI = " \
    git://git.ti.com/git/processor-sdk/sdk_builder.git;branch=main;protocol=https;name=sdk_builder;destsuffix=repo/sdk_builder \
    git://git.ti.com/git/processor-sdk/app_utils.git;branch=main;protocol=https;name=app_utils;destsuffix=repo/app_utils \
    git://git.ti.com/git/processor-sdk/vision_apps.git;branch=main;protocol=https;name=vision_apps;destsuffix=repo/vision_apps \
    git://git.ti.com/git/processor-sdk/tiovx.git;branch=main;protocol=https;name=tiovx;destsuffix=repo/tiovx \
    git://git.ti.com/git/processor-sdk/imaging.git;branch=main;protocol=https;name=imaging;destsuffix=repo/imaging \
    git://git.ti.com/git/processor-sdk/video_io.git;branch=main;protocol=https;name=video_io;destsuffix=repo/video_io \
    git://git.ti.com/git/processor-sdk/ti-perception-toolkit.git;branch=main;protocol=https;name=ti-perception-toolkit;destsuffix=repo/ti-perception-toolkit \
    git://git.ti.com/git/processor-sdk/psdk_include.git;branch=main;protocol=https;name=psdk_include;destsuffix=repo/psdk_include \
    git://git.ti.com/git/processor-sdk-vision/arm-tidl.git;branch=master;protocol=https;name=arm-tidl;destsuffix=repo/psdk_include/tidl_j7/arm-tidl \
    git://git.ti.com/git/processor-sdk/concerto.git;branch=main;protocol=https;name=concerto;destsuffix=repo/sdk_builder/concerto \
    git://git.ti.com/git/processor-sdk/pdk.git;nobranch=1;protocol=https;name=pdk;destsuffix=repo/pdk_j721s2_09_02_00_30 \
"

# tag REL.PSDK.ANALYTICS.09.02.00.05
SRCREV_sdk_builder = "947b82ef1103765a865f790026723d5c2adba587"
SRCREV_app_utils = "81ef5dadbe7bbe8a69b7e1dd0bd7f1dd369c5277"
SRCREV_vision_apps = "13c4e01192db2703ef8fcd9351eb07ec6d0f8982"
SRCREV_tiovx = "74a586f84d020b2348c8feabced86658e3268220"
SRCREV_imaging = "88386be8db58bfdd568eded077d83095871e351a"
SRCREV_video_io = "31ba97979ea22d811efff995e662da61be5e19fe"
SRCREV_ti-perception-toolkit = "8d090941dd671a5e670aa8f777986be73763ab41"
SRCREV_psdk_include = "2dde83677ad4daf0d3e53bcd6d2a032a9bac53aa"
SRCREV_arm-tidl = "c3a009b2eee74284c75dd5687d766f68f4648498"
SRCREV_concerto = "b354c73de459a380b45943b1afc7e1b10bc5491d"
# tag REL.PSDK.09.02.00.30
SRCREV_pdk = "efbd4dcadd5a523956d231dd5d2f361f173b6844"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-makerules-makefile_linux_arm-Add-makefile-part-for-p.patch \
    https://download.phytec.de/Software/Linux/BSP-Yocto-AM68x/resources/phycam_vm016_dcc-calib_v0_1.tar.gz;name=vm016;subdir=repo/imaging/sensor_drv/src/ \
"

SRC_URI[vm016.sha256sum] = "aa2e7e1e7ea15de0c5f6e76b5b7da51c30f82e44a082daabc92430b450ed89e1"

FILES:${PN} += "/opt/*"

#PTK needs:
# EGL/egl.h
# glm/glm.hpp
# IL/il.h
# /usr/include/freetype2/ft2build.h
# ti_rpmsg_char.h
# dlr.h

DEPENDS = "glm devil freetype ti-rpmsg-char repo-native mesa-pvr libpam"
DEPENDS:remove:am62axx = " mesa-pvr"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm"
COMPATIBLE_MACHINE += "|phyboard-izar-am68x-2|phyboard-izar-am68x-3"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"

S = "${WORKDIR}"

EXTRA_OEMAKE += "-C ${S}/repo/sdk_builder"

do_fetch[depends] += "repo-native:do_populate_sysroot"

do_compile() {
    export CROSS_COMPILE_LINARO=aarch64-phytec-linux-
    export LINUX_SYSROOT_ARM=${STAGING_DIR_TARGET}
    export TREAT_WARNINGS_AS_ERROR=0
    export GCC_LINUX_ARM_ROOT=
    export GCC_LINUX_ARM_ROOT_A72=
    export LINUX_FS_PATH=${STAGING_DIR_TARGET}
    export SOC=${PLAT_SOC}
    oe_runmake yocto_build
}

do_install() {
    export SOC=${PLAT_SOC}
    export LINUX_FS_STAGE_PATH=${D}
    oe_runmake yocto_install
}

INSANE_SKIP:${PN} += "ldflags"
