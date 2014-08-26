DESCRIPTION = "nodeJS Evented I/O for V8 JavaScript"
HOMEPAGE = "http://nodejs.org"
LICENSE = "MIT & BSD & Artistic-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4a31e6c424761191227143b86f58a1ef"

DEPENDS = "openssl"

SRC_URI = "http://nodejs.org/dist/v${PV}/node-v${PV}.tar.gz"
SRC_URI[md5sum] = "87768be7065d2120e71619948ab4bb2d"
SRC_URI[sha256sum] = "abddc6441e0f208f6ed8a045e0293f713ea7f6dfb2d6a9a2024bf8b1b4617710"

S = "${WORKDIR}/node-v${PV}"

# v8 errors out if you have set CCACHE
CCACHE = ""

ARCHFLAGS_arm = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--with-arm-float-abi=hard', '--with-arm-float-abi=softfp', d)}"
ARCHFLAGS ?= ""

# Node is way too cool to use proper autotools, so we install two wrappers to forcefully inject proper arch cflags to workaround gypi
do_configure () {
    export LD="${CXX}"
    # $TARGET_ARCH settings don't match --dest-cpu settings
    if [ "${TARGET_ARCH}" = "arm" ]; then
        ./configure --prefix=${prefix} --without-snapshot --shared-openssl --dest-cpu=arm --dest-os=linux ${ARCHFLAGS}
    elif [ "${TARGET_ARCH}" = "x86_64" ]; then
        ./configure --prefix=${prefix} --without-snapshot --shared-openssl --dest-cpu=x64
    else
        ./configure --prefix=${prefix} --without-snapshot --shared-openssl --dest-cpu=ia32
    fi
}

do_compile () {
    export LD="${CXX}"
    make BUILDTYPE=Release
}

do_install () {
    oe_runmake install DESTDIR=${D}
}

RDEPENDS_${PN} = "curl python-shell python-datetime python-subprocess python-textutils"
RDEPENDS_${PN}_class-native = ""

FILES_${PN} += "${libdir}/node_modules ${libdir}/dtrace ${datadir_native}/systemtap"
BBCLASSEXTEND = "native"
