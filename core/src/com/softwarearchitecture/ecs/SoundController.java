package com.softwarearchitecture.ecs;

import com.softwarearchitecture.ecs.components.SoundComponent;

public interface SoundController {
    void playSound(SoundComponent soundComponent);
    int getVolume();
    void setVolume(int volume);
}
