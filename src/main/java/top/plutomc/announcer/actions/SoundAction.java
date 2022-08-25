package top.plutomc.announcer.actions;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class SoundAction implements Action {

    private Sound sound;
    private float volume;
    private float pitch;

    public SoundAction(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundAction(String sound, float volume, float pitch) {
        this.sound = Sound.valueOf(sound);
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void on(Player player) {
        player.playSound(player, sound, volume, pitch);
    }
}
