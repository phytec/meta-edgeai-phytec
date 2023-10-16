FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-gstkmssink-Add-support-to-use-multiple-kmssinks.patch \
"
