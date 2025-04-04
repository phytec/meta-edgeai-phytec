SUMMARY = "EdgeAI Gst Apps"
DESCRIPTION = "EdgeAI Gst Apps implements gstreamer based simple deep learning demos that runs on TI platforms"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-gst-apps"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=dc68ab0305d85e56491b9a9aed2309f2"

PV = "${SRCPV}"
BRANCH = "main"
SRC_URI = "git://github.com/TexasInstruments/edgeai-gst-apps.git;branch=${BRANCH};protocol=https"
SRCREV = "21dce9bd8daa40722a3483adf586f8499f52262c"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "\
    file://0001-apps_cpp-edgeai_utils-Fix-build-failure-due-to-warni.patch;patchdir=.. \
"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"
PLAT_SOC:am62xx = "am62x"
PLAT_SOC:am62pxx = "am62p"

S = "${WORKDIR}/git/apps_cpp"

DEPENDS = "ti-vision-apps edgeai-dl-inferer yaml-cpp gstreamer1.0 opencv"
DEPENDS:remove:am62xx = "ti-vision-apps"
DEPENDS:remove:am62pxx = "ti-vision-apps"

RDEPENDS:${PN} += "edgeai-gst-plugins edgeai-dl-inferer-staticdev python3-pygobject python3-core bash"

#FIXME why source? there is a -src package already (and -dev)?!
RDEPENDS:${PN}-source += "bash python3-core edgeai-dl-inferer-dev python3-yamlloader python3-numpy opencv cmake dialog"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx"
COMPATIBLE_MACHINE += "|phyboard-izar-am68x-2|phyboard-izar-am68x-3"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

PACKAGES += "${PN}-source"
FILES:${PN} += "/opt/edgeai-gst-apps/apps_cpp/ \
                /opt/edgeai-gst-apps/configs/ \
                /opt/edgeai-gst-apps/apps_python/ \
                /opt/edgeai-gst-apps/scripts/ \
"
FILES:${PN}-source += "/opt/edgeai-gst-apps/sources"
# alternatively use separate config package (to share between cpp and python)
# FILES:${PN}-configs += "/opt/edgeai-gst-apps/configs"


inherit cmake pkgconfig

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-gst-apps/sources
    cp ${CP_ARGS} ${WORKDIR}/git/* ${D}/opt/edgeai-gst-apps/sources/


    cp ${CP_ARGS} ${WORKDIR}/git/configs ${D}/opt/edgeai-gst-apps/
    cp ${CP_ARGS} ${WORKDIR}/out/bin/Release ${D}/opt/edgeai-gst-apps/apps_cpp
    cp ${CP_ARGS} ${WORKDIR}/git/scripts ${D}/opt/edgeai-gst-apps/
    cp ${CP_ARGS} ${WORKDIR}/git/apps_python ${D}/opt/edgeai-gst-apps/
    # cpp app needs /opt/edgeai-gst-apps/configs/gst_plugins_map.yaml
}

INSANE_SKIP:${PN}-source += "dev-deps"

PR:append = "_edgeai_2"
