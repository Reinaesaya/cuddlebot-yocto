DESCRIPTION = "Nvram support for Broadcom wifi chips"
SECTION = "kernel"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENCE.broadcom_bcm43xx;md5=3160c14df7228891b868060e1951dfbc"

SRC_URI = " \
    file://bcm4329* \
    file://LICENCE.broadcom_bcm43xx \
"

# Replacement for broadcom-nvram-config
PROVIDES = "broadcom-nvram-config"
RPROVIDES_${PN} += "broadcom-nvram-config"
RREPLACES_${PN} += "broadcom-nvram-config"
RCONFLICTS_${PN} += "broadcom-nvram-config"

S = "${WORKDIR}"

do_install() {
    install -d  ${D}/lib/firmware/brcm
    install -m 0644 \
        ${WORKDIR}/bcm43* \
        ${D}/lib/firmware/brcm/
    ln -sr \
        ${D}/lib/firmware/brcm/bcm4329_nvram.txt \
        ${D}/lib/firmware/brcm/brcmfmac-sdio.txt
    ln -sr \
        ${D}/lib/firmware/brcm/bcm4329_apsta.bin \
        ${D}/lib/firmware/brcm/brcmfmac-sdio.bin
    ln -sr \
        ${D}/lib/firmware/brcm/bcm4330_nvram.txt \
        ${D}/lib/firmware/brcm/brcmfmac4330-sdio.txt
    ln -sr \
        ${D}/lib/firmware/brcm/bcm4330_fw.bin \
        ${D}/lib/firmware/brcm/brcmfmac4330-sdio.bin
}

FILES_${PN} = "/lib/firmware/brcm/*"

COMPATIBLE_MACHINE = "(wandboard-dual|wandboard-quad)"
