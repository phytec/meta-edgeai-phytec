PR:append = "_edgeai_1"

TARGET_IMAGE_TYPES:edgeai = "tar.xz"
TARGET_IMAGE_TYPES:adas = "tar.xz"
TARGET_IMAGES:edgeai = "tisdk-edgeai-image"
TARGET_IMAGES:adas = "tisdk-adas-image"

TARGET_IMAGES:am62axx:append = " tisdk-tiny-initramfs"
TARGET_IMAGE_TYPES:am62axx:append = " cpio"
