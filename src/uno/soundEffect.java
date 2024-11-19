package unoGameProject;

import unoGameProject.Audio;

public enum SoundEffect {

    private final Audio audio;

    SoundEffect(String fP) {

        audio = new Audio(fP);

    }

    public Audio audio() {
        return(audio)
    }

}
