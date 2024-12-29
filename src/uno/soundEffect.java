package uno;

import uno.Audio;

public class SoundEffect {

    private final Audio audio;

    SoundEffect(String fP) {

        audio = new Audio(fP);

    }

    public Audio audio() {
        return(audio);
    }

}
