package com.github.audiomanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.AudioManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AudioTools {
    /**********************************************************/
    private static AudioTools singleObj;

    private AudioTools() {
        map = new ConcurrentHashMap<Object, BaseAudioListener>();
    }

    public static AudioTools get() {
        if (singleObj == null) {
            synchronized (AudioTools.class) {
                if (singleObj == null) {
                    singleObj = new AudioTools();
                }
            }
        }
        return singleObj;
    }

    /**********************************************************/
    private Map<Object, BaseAudioListener> map;
    private volatile BroadcastReceiver audioBroadcast;
    private Context context;
    private AudioManager audioManager;

    public Context getContext(){
        if(context==null){
            throw new IllegalStateException("AudioTools.get().init(Context context) context is null");
        }
        return context;
    }
    public void init(Context context) {
        if (context == null) {
            return;
        }
        this.context = context.getApplicationContext();
    }

    public void unInit() {
        if (context == null || audioBroadcast == null) {
            return;
        }
        AudioBroadcast.unRegister(getContext(), audioBroadcast);
        map.clear();
    }

    public void addListener(Object object, AudioListener listener) {
        addListener(object,(BaseAudioListener)listener);
    }
    public void addListener(Object object, BaseAudioListener listener) {
        if (object == null || listener == null) {
            return;
        }
        if (audioBroadcast == null) {
            audioBroadcast = AudioBroadcast.register(getContext());
        }
        map.put(object, listener);
    }

    public void removeListener(Object object) {
        if (map == null || object == null) {
            return;
        }
        map.remove(object);
    }

    public void notifyListener(int type, int preValue, int value) {
        if (map == null) {
            return;
        }
        for (BaseAudioListener listener : map.values()) {
            if (listener == null) {
                continue;
            }
            switch (type) {
                case AudioManager.STREAM_VOICE_CALL:
                    listener.onVoiceCallVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_SYSTEM:
                    listener.onSystemVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_RING:
                    listener.onRingVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_MUSIC:
                    listener.onMusicVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_ALARM:
                    listener.onAlarmVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_NOTIFICATION:
                    listener.onNotificationVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_DTMF:
                    listener.onDTMFVolumeChange(value, preValue);
                    break;
                case AudioManager.STREAM_ACCESSIBILITY:
                    listener.onAccessibilityVolumeChange(value, preValue);
                    break;
            }
        }
    }

    public AudioManager getAudioManager() {
        if (audioManager == null) {
            audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        }
        return audioManager;
    }

    public void setVolume(int streamType, int volume) {
        setVolume(streamType, volume, 0);
    }

    public void setVolume(int streamType, int volume, int flags) {
        if (volume < 0) {
            volume = 0;
        }
        getAudioManager().setStreamVolume(streamType, volume, flags);
    }

    //通话音量
    public void setVoiceCallVolume(int volume) {
        setVolume(AudioManager.STREAM_VOICE_CALL, volume);
    }

    //系统音量
    public void setSystemVolume(int volume) {
        setVolume(AudioManager.STREAM_SYSTEM, volume);
    }

    //铃声音量
    public void setRingVolume(int volume) {
        setVolume(AudioManager.STREAM_RING, volume);
    }

    //音乐音量
    public void setMusicVolume(int volume) {
        setVolume(AudioManager.STREAM_MUSIC, volume);
    }

    /*提示声音音量*/
    public void setAlarmVolume(int volume) {
        setVolume(AudioManager.STREAM_ALARM, volume);
    }

    public void setNotificationVolume(int volume) {
        setVolume(AudioManager.STREAM_NOTIFICATION, volume);
    }

    /*拨号音量*/
    public void setDTMFVolume(int volume) {
        setVolume(AudioManager.STREAM_DTMF, volume);
    }

    /*辅助功能音量*/
    public void setAccessibilityVolume(int volume) {
        setVolume(AudioManager.STREAM_ACCESSIBILITY, volume);
    }

    public int getVolume(int streamType) {
        return getAudioManager().getStreamVolume(streamType);
    }

    public int getVoiceCallVolume() {
        return getVolume(AudioManager.STREAM_VOICE_CALL);
    }

    public int getSystemVolume() {
        return getVolume(AudioManager.STREAM_SYSTEM);
    }

    public int getRingVolume() {
        return getVolume(AudioManager.STREAM_RING);
    }

    public int getMusicVolume() {
        return getVolume(AudioManager.STREAM_MUSIC);
    }

    public int getAlarmVolume() {
        return getVolume(AudioManager.STREAM_ALARM);
    }

    public int getNotificationVolume() {
        return getVolume(AudioManager.STREAM_NOTIFICATION);
    }

    public int getDTMFVolume(int volume) {
        return getVolume(AudioManager.STREAM_DTMF);
    }

    public int getAccessibilityVolume() {
        return getVolume(AudioManager.STREAM_ACCESSIBILITY);
    }

    public int getMaxVolume(int streamType) {
        return getAudioManager().getStreamMaxVolume(streamType);
    }
}
