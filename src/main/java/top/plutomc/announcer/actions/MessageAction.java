package top.plutomc.announcer.actions;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.List;

public final class MessageAction implements Action {
    private List<String> content;

    public MessageAction(List<String> content) {
        this.content = content;
    }

    @Override
    public void on(Player player) {
        content.forEach(s -> player.sendMessage(
                MiniMessage.miniMessage().deserialize(s)
        ));
    }
}