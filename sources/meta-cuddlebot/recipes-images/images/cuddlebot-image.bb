include recipes-core/images/core-image-minimal.bb

SUMMARY = "The Cuddlebot Linux image."

IMAGE_INSTALL += " \
  packagegroup-cuddlebot \
"
