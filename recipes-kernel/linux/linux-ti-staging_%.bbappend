FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/${MACHINE}:"

PR_append = "_edgeai_0"

require rtos-mem-map.inc
