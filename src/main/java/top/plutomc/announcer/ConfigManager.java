package top.plutomc.announcer;

import org.bukkit.Bukkit;
import top.plutomc.announcer.actions.MessageAction;
import top.plutomc.announcer.actions.TitleAction;

import java.util.HashSet;
import java.util.Set;

public final class ConfigManager {
    private final Set<Announcer> announcers;

    public ConfigManager() {
        announcers = new HashSet<>();
    }

    public void load() {
        AnnouncerPlugin.instance().reloadConfig();
        Bukkit.getServer().getScheduler().cancelTasks(AnnouncerPlugin.instance());
        for (String key : AnnouncerPlugin.instance().getConfig().getConfigurationSection("announcers").getKeys(false)) {
            int cycle = AnnouncerPlugin.instance().getConfig().getInt("announcers." + key + ".cycle");
            Announcer announcer = new Announcer(cycle);
            for (String key1 : AnnouncerPlugin.instance().getConfig().getConfigurationSection("announcers." + key + ".messageGroup").getKeys(false)) {
                Group group = new Group();
                String perm = AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroup." + key1 + ".perm");
                if (perm != "*") {
                    group.setPerm(perm);
                }
                if (AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroup." + key1 + ".actions.title")) {
                    long fadeIn = -1L;
                    long stay = -1L;
                    long fadeOut = -1L;

                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroup." + key1 + ".actions.title.fadeIn")) {
                        fadeIn = 500L;
                    }
                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroup." + key1 + ".actions.title.stay")) {
                        stay = 3500L;
                    }
                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroup." + key1 + ".actions.title.fadeOut")) {
                        fadeOut = 1000L;
                    }

                    TitleAction titleAction = new TitleAction(
                            AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroup." + key1 + ".actions.title.main"),
                            AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroup." + key1 + ".actions.title.sub"),
                            fadeIn,
                            stay,
                            fadeOut
                    );
                    group.getActions().add(titleAction);
                }
                if (AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroup." + key1 + ".actions.message.content")) {
                    MessageAction messageAction = new MessageAction(
                            AnnouncerPlugin.instance().getConfig().getStringList("announcers." + key + ".messageGroup." + key1 + ".actions.message.content")
                    );
                    group.getActions().add(messageAction);
                }
                announcer.getGroups().add(group);
            }
            announcers.add(announcer);
        }
        announcers.forEach(Announcer::start);
    }
}