FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "file://tamer-hostapd.conf"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/tamer-hostapd.conf ${D}${sysconfdir}/hostapd.conf
}
