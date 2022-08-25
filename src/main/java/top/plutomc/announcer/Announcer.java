package top.plutomc.announcer;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Announcer {
    private final List<Group> groups;
    private final int cycle;

    private BukkitTask task;

    public Announcer(int cycle) {
        groups = new ArrayList<>();
        this.cycle = cycle;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void start() {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                groups.get(new Random().nextInt(groups.size())).doAnnounce();
            }
        }.runTaskTimer(AnnouncerPlugin.instance(), 0L, cycle * 20L);
    }

    public BukkitTask getTask() {
        return task;
    }
}