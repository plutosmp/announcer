package top.plutomc.announcer;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class Command implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Arrays.stream(args).toList().isEmpty()) {
            if (args[0].equalsIgnoreCase("reload")) {
                AnnouncerPlugin.getConfigManager().load();
                sender.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<green>Done."
                ));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return List.of("reload");
        return null;
    }
}