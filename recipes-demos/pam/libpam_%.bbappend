FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://custom_limits.conf"

do_install:append:j721e-evm(){
    install -m 0644 ${WORKDIR}/custom_limits.conf ${D}${sysconfdir}/security/limits.conf
}

do_install:append:j721s2-evm(){
    install -m 0644 ${WORKDIR}/custom_limits.conf ${D}${sysconfdir}/security/limits.conf
}

do_install:append:j784s4-evm(){
    install -m 0644 ${WORKDIR}/custom_limits.conf ${D}${sysconfdir}/security/limits.conf
}

PR:append = "_edgeai_0"
