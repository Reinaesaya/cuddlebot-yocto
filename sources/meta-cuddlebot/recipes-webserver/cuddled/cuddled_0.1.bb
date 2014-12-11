DESCRIPTION = "Cuddle daemon"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
  file://cuddled \
  file://cuddlespeak \
  file://init \
"

inherit update-rc.d

INITSCRIPT_NAME = "cuddled"
INITSCRIPT_PARAMS = "defaults 90 20"

S = "${WORKDIR}"

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${S}/cuddled" "${D}/${bindir}"
  install -m 0755 "${S}/cuddlespeak" "${D}/${bindir}"

  install -d "${D}${sysconfdir}/init.d"
  install -m 0755 "${S}/init" "${D}${sysconfdir}/init.d/cuddled"
}
