package com.alfie20101.calendar;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CalendarPlugin extends JavaPlugin implements Listener {

    private BossBar calendarBar;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        calendarBar = BossBar.bossBar(
                Component.text("Loading Date...", NamedTextColor.GOLD),
                1.0f,
                BossBar.Color.BLUE,
                BossBar.Overlay.PROGRESS
        );

        Bukkit.getScheduler().runTaskTimer(this, this::updateCalendar, 0L, 20L);
    }

    private void updateCalendar() {
        if (Bukkit.getWorlds().isEmpty()) return;

        World world = Bukkit.getWorlds().get(0);

        long mcDays = world.getFullTime() / 24000L;
        int day = (int) (mcDays % 8) + 1;
        int month = (int) ((mcDays / 8) % 12) + 1;
        long year = (mcDays / 96) + 1;

        String dateText = String.format("Date: Day %d | Month %d | Year %d", day, month, year);
        calendarBar.name(Component.text(dateText, NamedTextColor.GOLD));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().showBossBar(calendarBar);
    }
}