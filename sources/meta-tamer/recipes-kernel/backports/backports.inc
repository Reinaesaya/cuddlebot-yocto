DESCRIPTION = "Linux kernel backports"
HOMEPAGE = "https://backports.wiki.kernel.org"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
RDEPENDS_${PN} = "wireless-tools"
INC_PR = "r1"

# depends on config options which are not enabled in qemu* MACHINEs by default
# config.mk:25: *** "ERROR: your kernel has CONFIG_CFG80211=y, you should have it CONFIG_CFG80211=m if you want to use this thing.".  Stop.
EXCLUDE_FROM_WORLD = "1"

SRC_URI = "http://www.kernel.org/pub/linux/kernel/projects/backports/stable/v${PV}/backports-${PV}-1.tar.xz"

S = "${WORKDIR}/backports-${PV}-1"

inherit module

EXTRA_OEMAKE = "KLIB_BUILD=${STAGING_KERNEL_DIR} KLIB=${D}"

do_configure() {
    make defconfig-brcmfmac
}

do_install() {
    oe_runmake DEPMOD=echo DESTDIR="${D}" INSTALL_MOD_PATH="${D}" LDFLAGS="" install-modules
}
