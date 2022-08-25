package top.plutomc.announcer.actions;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;

import java.time.Duration;

public final class TitleAction implements Action {

    private final String main;
    private final String sub;
    private final long fadeIn;
    private final long stay;
    private final long fadeOut;

    public TitleAction(String main, String sub, long fadeIn, long stay, long fadeOut) {
        this.main = main;
        this.sub = sub;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public TitleAction(String main, String sub) {
        this.main = main;
        this.sub = sub;
        this.fadeIn = 10L;
        this.stay = 70L;
        this.fadeOut = 20L;
    }

    @Override
    public void on(Player player) {
        player.sendTitlePart(TitlePart.TITLE, MiniMessage.miniMessage().deserialize(
                main
        ));
        player.sendTitlePart(TitlePart.SUBTITLE, MiniMessage.miniMessage().deserialize(
                sub
        ));
        player.sendTitlePart(TitlePart.TIMES, Title.Times.times(
                Duration.ofMillis(fadeIn / 20L * 1000L),
                Duration.ofMillis(stay / 20L * 1000L),
                Duration.ofMillis(fadeOut / 20L * 1000L)
        ));
    }

}