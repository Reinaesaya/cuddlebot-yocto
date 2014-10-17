HOMEPAGE = "http://hostap.epitest.fi"
SECTION = "kernel/userland"
LICENSE = "GPLv2 | BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab87f20cd7e8c0d0a6539b34d3791d0e"
DEPENDS = "libnl openssl"
SUMMARY = "User space daemon for extended IEEE 802.11 management"

inherit update-rc.d
INITSCRIPT_NAME = "hostapd"

SRC_URI = " \
    http://hostap.epitest.fi/releases/hostapd-${PV}.tar.gz \
    file://default \
    file://defconfig \
    file://init \
    file://hostapd.conf \
"

S = "${WORKDIR}/hostapd-${PV}/hostapd-${PV}"

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig ${S}/.config
}

do_compile() {
    export CFLAGS="-MMD -O2 -Wall -I${STAGING_INCDIR}/libnl3"
    make
}

do_install() {
    install -d ${D}${sbindir} ${D}${sysconfdir}/init.d ${D}${sysconfdir}/default/

    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/hostapd
    install -m 0644 ${WORKDIR}/default ${D}${sysconfdir}/default/hostapd
    install -m 0644 ${S}/hostapd.conf ${D}${sysconfdir}

    install -m 0755 ${S}/hostapd ${D}${sbindir}
    install -m 0755 ${S}/hostapd_cli ${D}${sbindir}
}

FILES_${PN} += "${sysconfdir} ${sbindir}"

SRC_URI[md5sum] = "23c1f78a693c3288802d516adb7fd289"
SRC_URI[sha256sum] = "f15b6bcb434378860ea5b88dffed7f54d8cb71fff2146de0f006977a5e25a882"