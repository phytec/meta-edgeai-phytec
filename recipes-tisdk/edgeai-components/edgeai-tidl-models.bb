SUMMARY = "EdgeAI TIDL Models"
LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-ti/meta-ti-bsp/licenses/TI-TFL;md5=a1b59cb7ba626b9dbbcbf00f3fbc438a"

export http_proxy
export https_proxy
export no_proxy

BRANCH = "main"
SRCREV = "b563cc345a50f76baa6b4bf14677abd984cecc49"

SOC = ""
SOC:j721e = "j721e"
SOC:j721s2 = "j721s2"
SOC:j784s4 = "j784s4"
SOC:am62axx = "am62a"
SOC:am62xx = "am62x"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|am62axx-evm|am62xx"

do_fetch() {
    mkdir -p ${WORKDIR}/script
    cd ${WORKDIR}/script
    if [ "${SRCREV}" = "${AUTOREV}" ]; then
        VERSION="${BRANCH}"
    else
        VERSION="${SRCREV}"
    fi
    wget https://raw.githubusercontent.com/TexasInstruments/edgeai-gst-apps/${VERSION}/download_models.sh
    chmod +x ./download_models.sh
    SOC=${SOC} ./download_models.sh --recommended
}

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/model_zoo
    cp ${CP_ARGS} ${WORKDIR}/model_zoo ${D}/opt/
}

FILES:${PN} += "/opt/"
