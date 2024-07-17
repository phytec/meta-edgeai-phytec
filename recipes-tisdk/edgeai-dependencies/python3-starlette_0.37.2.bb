LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=11e8c8dbfd5fa373c703de492140ff7a"

SRC_URI[md5sum] = "b0ba8178dd4bf2cdff53c8702212be52"
SRC_URI[sha256sum] = "9af890290133b79fc3db55474ade20f6220a364a0402e0b556e7cd5e1e093823"

RDEPENDS:${PN} += "python3-anyio (>=3.4.0) python3-typing-extensions (>=3.10.0)"

PYPI_PACKAGE = "starlette"
PV = "0.37.2"
inherit pypi python_hatchling
