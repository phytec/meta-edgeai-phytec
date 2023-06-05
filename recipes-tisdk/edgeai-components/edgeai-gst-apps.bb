SUMMARY = "EdgeAI Gst Apps"
DESCRIPTION = "EdgeAI Gst Apps implements gstreamer based simple deep learning demos that runs on TI platforms"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-gst-apps"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=dc68ab0305d85e56491b9a9aed2309f2"

PV = "${SRCPV}"
SRC_URI = "git://github.com/TexasInstruments/edgeai-gst-apps.git;branch=develop;protocol=https"
SRCREV = "${AUTOREV}"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:am62axx = "am62a"
PLAT_SOC:am62xx = "am62"

S = "${WORKDIR}/git/apps_cpp"

DEPENDS = "ti-vision-apps edgeai-dl-inferer yaml-cpp gstreamer1.0 opencv"
DEPENDS:remove:am62xx-evm = "ti-vision-apps"

RDEPENDS:${PN} += "edgeai-gst-plugins edgeai-dl-inferer-staticdev"

RDEPENDS:${PN}-source += "bash python3-core edgeai-dl-inferer-dev python3-yamlloader python3-numpy opencv cmake dialog"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm|am62xx-evm"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt"

inherit cmake pkgconfig

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-gst-apps
    cp ${CP_ARGS} ${WORKDIR}/git/* ${D}/opt/edgeai-gst-apps
    rm -rf ${D}/opt/edgeai-gst-apps/apps_cpp/lib

    #mkdir -p ${D}/opt/model_zoo
    #mkdir -p ${D}/opt/edgeai-test-data
    #mkdir -p ${D}/opt/oob-demo-assets
    #export SOC="${PLAT_SOC}"
    #export EDGEAI_DATA_PATH=${WORKDIR}/edgeai-test-data
    #export OOB_DEMO_ASSETS_PATH=${WORKDIR}/oob-demo-assets
    #export EDGEAI_SDK_VERSION=08_06_00

    #cd ${WORKDIR}/git/
    #./download_models.sh --recommended
    #./download_test_data.sh
    #cd $OOB_DEMO_ASSETS_PATH
    #for i in *.h264
    #do
    #  ln -sf /opt/oob-demo-assets/$i $EDGEAI_DATA_PATH/videos/$i
    #done
    #cp ${CP_ARGS} ${WORKDIR}/model_zoo/* ${D}/opt/model_zoo
    #cp ${CP_ARGS} ${WORKDIR}/edgeai-test-data/* ${D}/opt/edgeai-test-data
    #cp ${CP_ARGS} ${WORKDIR}/oob-demo-assets/* ${D}/opt/oob-demo-assets
}

INSANE_SKIP:${PN}-source += "dev-deps"

PR:append = "_edgeai_0"
