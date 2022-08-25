package top.plutomc.announcer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import top.plutomc.announcer.actions.MessageAction;
import top.plutomc.announcer.actions.SoundAction;
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
        if (!announcers.isEmpty()) {
            announcers.forEach(announcer -> Bukkit.getScheduler().cancelTask(announcer.getTask().getTaskId()));
            announcers.clear();
        }
        for (String key : AnnouncerPlugin.instance().getConfig().getConfigurationSection("announcers").getKeys(false)) {
            int cycle = AnnouncerPlugin.instance().getConfig().getInt("announcers." + key + ".cycle");
            Announcer announcer = new Announcer(cycle);
            for (String key1 : AnnouncerPlugin.instance().getConfig().getConfigurationSection("announcers." + key + ".messageGroups").getKeys(false)) {
                Group group = new Group();
                String perm = AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroups." + key1 + ".perm");
                if (!perm.equals("*")) {
                    group.setPerm(perm);
                }
                if (AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.title")) {
                    long fadeIn;
                    long stay;
                    long fadeOut;

                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.title.fadeIn")) {
                        fadeIn = 10L;
                    }else {
                        fadeIn = AnnouncerPlugin.instance().getConfig().getLong("announcers." + key + ".messageGroups." + key1 + ".actions.title.fadeIn");
                    }
                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.title.stay")) {
                        stay = 70L;
                    }else {
                        stay = AnnouncerPlugin.instance().getConfig().getLong("announcers." + key + ".messageGroups." + key1 + ".actions.title.stay");
                    }
                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.title.fadeOut")) {
                        fadeOut = 20L;
                    }else {
                        fadeOut = AnnouncerPlugin.instance().getConfig().getLong("announcers." + key + ".messageGroups." + key1 + ".actions.title.fadeOut");
                    }

                    TitleAction titleAction = new TitleAction(
                            AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroups." + key1 + ".actions.title.main"),
                            AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroups." + key1 + ".actions.title.sub"),
                            fadeIn,
                            stay,
                            fadeOut
                    );
                    group.getActions().add(titleAction);
                }
                if (AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.message.content")) {
                    MessageAction messageAction = new MessageAction(
                            AnnouncerPlugin.instance().getConfig().getStringList("announcers." + key + ".messageGroups." + key1 + ".actions.message.content")
                    );
                    group.getActions().add(messageAction);
                }
                if (AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.sound")) {
                    float volume;
                    float pitch;

                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.sound.volume")) {
                        volume = 1F;
                    }else {
                        volume = (float) AnnouncerPlugin.instance().getConfig().getDouble("announcers." + key + ".messageGroups." + key1 + ".actions.sound.volume");
                    }
                    if (!AnnouncerPlugin.instance().getConfig().contains("announcers." + key + ".messageGroups." + key1 + ".actions.sound.pitch")) {
                        pitch = 0F;
                    }else {
                        pitch = (float) AnnouncerPlugin.instance().getConfig().getDouble("announcers." + key + ".messageGroups." + key1 + ".actions.sound.pitch");
                    }

                    SoundAction soundAction = new SoundAction(
                            AnnouncerPlugin.instance().getConfig().getString("announcers." + key + ".messageGroups." + key1 + ".actions.sound.sound"),
                            volume,
                            pitch
                    );
                    group.getActions().add(soundAction);
                }
                announcer.getGroups().add(group);
            }
            announcers.add(announcer);
        }
        announcers.forEach(Announcer::start);
    }
}