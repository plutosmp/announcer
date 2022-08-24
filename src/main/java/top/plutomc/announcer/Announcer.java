package top.plutomc.announcer;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Announcer {
    private final List<Group> groups;
    private final int cycle;

    public Announcer(int cycle) {
        groups = new ArrayList<>();
        this.cycle = cycle;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                groups.get(new Random().nextInt(groups.size())).doAnnounce();
            }
        }.runTaskTimerAsynchronously(AnnouncerPlugin.instance(), 0L, cycle * 20L);
    }
}