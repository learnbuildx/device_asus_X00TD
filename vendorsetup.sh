rm -rf vendor/qcom/opensource/power
git clone https://github.com/pkm774/vendor_qcom_opensource_power -b power.legacyopensource.lnx.1.0.r15-rel vendor/qcom/opensource/power

rm -rf external/tinyalsa
git clone https://source.codeaurora.org/quic/la/platform/external/tinyalsa -b ks-aosp.lnx.3.0.r15-rel external/tinyalsa
rm -rf external/tinycompress
git clone https://source.codeaurora.org/quic/la/platform/external/tinycompress -b ks-aosp.lnx.3.0.r15-rel external/tinycompress

rm -rf hardware/qcom-caf/msm8998/audio
git clone https://github.com/pkm774/hardware_qcom-caf_msm8998_audio -b audio-hal.lnx.8.0.r14-rel hardware/qcom-caf/msm8998/audio
rm -rf hardware/qcom-caf/msm8998/media
git clone https://github.com/pkm774/hardware_qcom-caf_msm8998_media -b video-userspace.lnx.2.10.r10-rel hardware/qcom-caf/msm8998/media
rm -rf hardware/qcom-caf/msm8998/display
git clone https://github.com/pkm774/hardware_qcom-caf_msm8998_display -b display.lnx.3.4.r3-rel hardware/qcom-caf/msm8998/display
rm -rf vendor/qcom/opensource/commonsys-intf/display
git clone https://source.codeaurora.org/quic/la/platform/vendor/qcom-opensource/display-commonsys-intf -b display-sysintf.lnx.1.2.r12-rel vendor/qcom/opensource/commonsys-intf/display

rm -rf hardware/qcom-caf/thermal
git clone https://github.com/pkm774/android_hardware_qcom_thermal -b aosp-new/android11-qpr3-s1-release hardware/qcom-caf/thermal

rm -rf frameworks/base/data/sounds/google
git clone https://github.com/pkm774/aosp_sounds frameworks/base/data/sounds/google

# Remove pixel-charger resources from vendor
rm -rf vendor/corvus/charger
