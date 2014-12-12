SUMMARY = "Cuddlebot package group"
LICENSE = "MIT"
PR = "r1"

inherit packagegroup

RDEPENDS_${PN} = " \
    alsa-lib \
    alsa-tools \
    alsa-utils \
    bcm4329-nvram-config \
    bcm4330-nvram-config \
    bluez5 \
    brcm-patchram-plus \
    cuddled \
    curl \
    dnsmasq \
    hostapd \
    i2c-tools \
    iw \
    kernel-module-brcmfmac \
    kernel-module-brcmutil \
    kernel-module-ftdi-sio \
    screen \
    util-linux \
    wpa-supplicant \
"
