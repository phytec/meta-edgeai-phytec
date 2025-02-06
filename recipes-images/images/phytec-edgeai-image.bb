require recipes-images/images/phytec-vision-image.bb

SUMMARY =  "This image is designed to show development of edgeAI applications."

LICENSE = "MIT"

IMAGE_INSTALL += "\
    packagegroup-edgeai-tisdk-addons \
    packagegroup-edgeai-tiovx \
    packagegroup-edgeai-tiovx-dev \
    packagegroup-edgeai-tiovx-src \
    packagegroup-edgeai-stack \
    packagegroup-edgeai-stack-dev \
    packagegroup-edgeai-stack-src \
    packagegroup-edgeai-apps \
    packagegroup-edgeai-apps-src \
    edgeai-tiovx-apps \   
    phytec-edgeai-firmware \
"
