LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=5c778842f66a649636561c423c0eec2e"

SRC_URI[md5sum] = "a000939f41470e55bad5cec475489b4c"
SRC_URI[sha256sum] = "d46cd8e0fd80240baffbcd9ec1012a712938754afcf81bce56c024c1656aece8"

RDEPENDS:${PN} += "python3-click (>=7.0) python3-h11 (>=0.8)"

PYPI_PACKAGE = "uvicorn"
PV = "0.30.1"
inherit pypi python_hatchling

