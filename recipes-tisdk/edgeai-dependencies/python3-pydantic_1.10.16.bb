LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c02ea30650b91528657db64baea1757"

SRC_URI[md5sum] = "5cf912a6ded81186c57c802251f5a4b8"
SRC_URI[sha256sum] = "8bb388f6244809af69ee384900b10b677a69f1980fdc655ea419710cffcb5610"

RDEPENDS:${PN} += "python3-typing-extensions (>=4.2.0)"

PYPI_PACKAGE = "pydantic"
PV = "1.10.16"
inherit pypi setuptools3
