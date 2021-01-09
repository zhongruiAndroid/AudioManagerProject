package com.github.audiomanager;

public interface BaseAudioListener {
    void onVoiceCallVolumeChange(int preVolume, int volume);

    void onSystemVolumeChange(int preVolume, int volume);

    void onRingVolumeChange(int preVolume, int volume);

    void onMusicVolumeChange(int preVolume, int volume);

    void onAlarmVolumeChange(int preVolume, int volume);

    void onNotificationVolumeChange(int preVolume, int volume);

    void onDTMFVolumeChange(int preVolume, int volume);

    void onAccessibilityVolumeChange(int preVolume, int volume);
}
