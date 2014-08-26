DESCRIPTION = "Tamer Controller Web Service"
LICENSE  = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1e12085ad65581a4d0c5e2fa2dd49474"

SRC_URI = " \
    file://LICENSE.txt \
    file://tamer-control-0.0.1-dev.tgz;unpack=false \
    file://initd \
"

SRC_URI[md5sum] = "d62257ad991ae033036652fab5f801d4"
SRC_URI[sha256sum] = "e56c541456873402106c8f14ac49dd69391f8fbc7ec34212dd9d622d90b5eecc"

PREFIX = "/opt/tamer-control"

S = "${WORKDIR}"

# inherit systemd
inherit update-rc.d

INITSCRIPT_NAME = "tamer-control"

# SYSTEMD_PACKAGES = "${PN}"
# SYSTEMD_SERVICE_${PN} = "tamer-control.service"

FILES_${PN} = " \
    ${PREFIX} \
    ${sysconfdir} \
    ${systemd_unitdir} \
    /var/log/tamer-control \
    /var/run/tamer-control \
"

DEPENDS = "nodejs"
RDEPENDS_${PN} = "nodejs"

do_compile() {
    # Cross-compile NPM modules:
    # http://www.code-with-passion.com/2012/11/07/cross-compiling-nodejs-and-node-modules-for-armbeaglebone/

    # export AR=${TARGET_PREFIX}ar
    # export CC=${TARGET_PREFIX}gcc
    # export CXX=${TARGET_PREFIX}g++
    # export LINK=${TARGET_PREFIX}g++
    # export npm_config_arch=arm
    # export npm_config_nodedir=${S}/node-v0.10.28

    # Setup CWD
    # cd ${S}

    # Cleaning cache should help to some weird compilation errors
    #npm cache clean

    # Do a fresh start
    #rm -rf node_modules

    # Install
    npm install --production tamer-control-0.0.1-dev.tgz
    npm install --production forever

    # Remove git files
    find node_modules -name .gitignore -delete
}

do_install_append() {
    install -d ${D}${PREFIX}

    cp -fpr ${S}/node_modules ${D}${PREFIX}/

    # install -d ${D}${sysconfdir}/${PN}
    # install -m 0644 ${S}/systemd/config ${D}${sysconfdir}/${PN}

    # install -d ${D}${systemd_unitdir}/system
    # install -m 0755 ${S}/systemd/tamer-control.service ${D}${systemd_unitdir}/system

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/initd/init ${D}${sysconfdir}/init.d/tamer-control

    install -d ${D}${sysconfdir}/default/
    install -m 0644 ${S}/initd/default ${D}${sysconfdir}/default/tamer-control
}
