FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
    install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}
}
