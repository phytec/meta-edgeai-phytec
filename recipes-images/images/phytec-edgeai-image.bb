require recipes-images/images/phytec-vision-image.bb

SUMMARY =  "This image is designed to show development of camera and \
            imaging applications with openCV."

LICENSE = "MIT"

IMAGE_INSTALL += "\
    packagegroup-edgeai-tisdk-addons \
    packagegroup-edgeai-stack \
    packagegroup-edgeai-apps \
    phytec-edgeai-firmware \
"
