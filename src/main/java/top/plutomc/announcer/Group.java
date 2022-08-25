package top.plutomc.announcer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.plutomc.announcer.actions.Action;

import java.util.HashSet;
import java.util.Set;

public final class Group {
    private final Set<Action> actions;

    private String perm = null;

    public Group() {
        actions = new HashSet<>();
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void doAnnounce() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::announcePlayer);
    }

    private void announcePlayer(Player player) {
        if (perm != null) {
            if (!player.hasPermission(perm)) {
                return;
            }
        }
        actions.forEach(action -> action.on(player));
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }
}