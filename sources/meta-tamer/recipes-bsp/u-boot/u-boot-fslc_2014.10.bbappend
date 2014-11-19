FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append := " file://set-bootdelay.patch"
