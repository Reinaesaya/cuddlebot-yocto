# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DEPENDS += "lzop-native bc-native"
PROVIDES = "virtual/kernel linux-mainline"

inherit kernel

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SRCBRANCH ?= ""

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=${SRCBRANCH} \
           file://defconfig \
           file://0001-ARM-i.MX6-Wandboard-add-wifi-bt-rfkill-driver.patch \
           file://0002-ARM-dts-wandboard-add-binding-for-wand-rfkill-driver.patch \
           file://0003-Vivante-v4-driver.patch"
LOCALVERSION = "-tamer"
