SUMMARY = "EdgeAI TVM model artifacts"
DESCRIPTION = "Precompiled TVM model artifacts for the yamnet, vggish11, and gcrn EVM models"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}/src"
PR = "_edgeai_0"

MODELZOO_URI = "https://software-dl.ti.com/jacinto7/esd/modelzoo/12_00_00_03/modelartifacts/AM62D"

SRC_URI = " \
    ${MODELZOO_URI}/ac-0201_tvmrt_audio_classification_urbansound8k_yamnet_onnx.tar.gz;name=yamnet;subdir=${S}/yamnet \
    ${MODELZOO_URI}/ac-0101_tvmrt_audio_classification_urbansound8k_vggish11_onnx.tar.gz;name=vggish;subdir=${S}/vggish \
    ${MODELZOO_URI}/ase-0212_tvmrt_speech_enhancement_voicebank_demand_16k_gcrn_mod_g16_fixed_4sec_onnx.tar.gz;name=gcrn;subdir=${S}/gcrn \
"

SRC_URI[yamnet.sha256sum] = "9e690cac5aab0891b8807b9eb0003c568c98f814498d7b3bd7b393c31ff2720c"
SRC_URI[vggish.sha256sum] = "862bf567e9502ce3b13475d4a87eef8121dae36d67b3540bdcbac150f3ba0f48"
SRC_URI[gcrn.sha256sum] = "3903319042ca04507cf2d872eebabf1a7fab50673a48e66ea419f5d71d0bb10e"

COMPATIBLE_MACHINE = "am62dxx"

FILES:${PN} += "${datadir}/tvm_inference/"

do_install() {
    # Install only the EVM-specific TVM artifacts for each model, keyed by
    # short model name, dropping the .evm suffix on install
    for model in yamnet vggish gcrn; do
        artifacts_dir=${D}${datadir}/tvm_inference/artifacts/${model}
        install -d ${artifacts_dir}
        install -m 0644 ${S}/${model}/*/artifacts/deploy_graph.json.evm ${artifacts_dir}/deploy_graph.json
        install -m 0755 ${S}/${model}/*/artifacts/deploy_lib.so.evm ${artifacts_dir}/deploy_lib.so
        install -m 0644 ${S}/${model}/*/artifacts/deploy_param.params.evm ${artifacts_dir}/deploy_param.params
    done
}
