# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP Linux mainline based kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on mainline kernel used by FSL Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

include linux-tamer.inc

PV = "3.17+git${SRCPV}"

SRCBRANCH = "linux-3.17.y"
SRCREV = "30c80cbd7c775cb480248b08673cf5e9a57342fb"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
