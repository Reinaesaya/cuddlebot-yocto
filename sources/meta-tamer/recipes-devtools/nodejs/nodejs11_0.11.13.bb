DESCRIPTION = "nodeJS Evented I/O for V8 JavaScript"
HOMEPAGE = "http://nodejs.org"
LICENSE = "MIT & BSD & Artistic-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c61ec54d119e218dbe45d9f69a421b5f"

DEPENDS = "openssl"

PR = "r6"

SRC_URI = " \
    http://nodejs.org/dist/node-v${PV}.tar.gz \
    file://arm_version.patch \
"
SRC_URI[md5sum] = "17c8bc4653bb32e8440d352e95985d03"
SRC_URI[sha256sum] = "15d6e90c16adf907c0401cd5a77841b5264e90dfdaa1051d75184aa587fc8298"

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
