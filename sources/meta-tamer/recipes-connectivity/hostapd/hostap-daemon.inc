HOMEPAGE = "http://hostap.epitest.fi"
SECTION = "kernel/userland"
LICENSE = "GPLv2 | BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab87f20cd7e8c0d0a6539b34d3791d0e"
DEPENDS = "libnl openssl"
DESCRIPTION = "User space daemon for extended IEEE 802.11 management"

inherit update-rc.d
INITSCRIPT_NAME = "hostapd"

SRC_URI = " \
    http://hostap.epitest.fi/releases/hostapd-${PV}.tar.gz \
    file://default \
    file://defconfig \
    file://init \
    file://tamer-hostapd.conf \
"

S = "${WORKDIR}/hostapd-${PV}"

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig ${S}/hostapd/.config
}

do_compile() {
    cd hostapd
    export CFLAGS="-MMD -O2 -Wall -I${STAGING_INCDIR}/libnl3"
    make
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/hostapd

    install -d ${D}${sysconfdir}/default/
    install -m 0644 ${WORKDIR}/default ${D}${sysconfdir}/default/hostapd
    install -m 0644 ${WORKDIR}/tamer-hostapd.conf ${D}${sysconfdir}/hostapd.conf

    install -d ${D}${sbindir}
    install -m 0755 ${S}/hostapd/hostapd ${D}${sbindir}
    install -m 0755 ${S}/hostapd/hostapd_cli ${D}${sbindir}
}

FILES_${PN} += "${sysconfdir} ${sbindir}"

