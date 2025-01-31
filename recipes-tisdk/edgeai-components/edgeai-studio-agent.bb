SUMMARY = "EdgeAI Studio Agent"
DESCRIPTION = "EdgeAI Device Agent runs on TI devices to enable EdgeAI Studio tool"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-studio-agent"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=3677661f72cd03c7b3c0a35e5fb23e8d"

PV = "${SRCPV}"
BRANCH = "main"
SRC_URI = "git://github.com/TexasInstruments/edgeai-studio-agent.git;branch=${BRANCH};protocol=https"
SRCREV = "2930e59add53cc7cf3522e43e4ba4550b77e5036"

S = "${WORKDIR}/git"

RDEPENDS:${PN} += "edgeai-gst-apps-source bash python3-core python3-aiofiles python3-websocket-client python3-uvicorn python3-fastapi python3-python-multipart python3-websockets python3-psutil cors express nodejs"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx|phyboard-izar-am68x-2"

FILES:${PN} += "/opt"

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-studio-agent
    cp ${CP_ARGS} ${WORKDIR}/git/* ${D}/opt/edgeai-studio-agent
}

PR:append = "_edgeai_0"
