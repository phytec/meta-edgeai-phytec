# Copyright (C) 2025 PHYTEC Messtechnik GmbH,
# Author: Steffen Hemer <s.hemer@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Adding some environment variables to the SDK for cross-compiling edgeAI stuff"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit nativesdk

# add make to SDK as the edgeAI (this is a buildhost dependency for Yocto and therefore not
# added to SDK, even when switching default cmake build from ninja to make)
DEPENDS:append = " nativesdk-make"

ENV_SCRIPT = "${WORKDIR}/environment.d-edgeai.sh"

do_install:append () {
	mkdir -p ${D}${SDKPATHNATIVE}/environment-setup.d
	cat <<- 'EOF' > ${ENV_SCRIPT}
	export CROSS_COMPILER_PATH="$OECORE_NATIVE_SYSROOT/usr"
	export CROSS_COMPILER_PREFIX="${TARGET_PREFIX%-}/$TARGET_PREFIX"
	export TARGET_FS="$OECORE_TARGET_SYSROOT"
	export SOC="j721s2" #SOC can only be hardcoded since nativesdk class has empty MACHINEOVERRIDES!
	EOF
	install -m 644 ${ENV_SCRIPT} ${D}${SDKPATHNATIVE}/environment-setup.d/edgeai.sh
}

FILES:${PN}:append = " ${SDKPATHNATIVE}/environment-setup.d/edgeai.sh"
