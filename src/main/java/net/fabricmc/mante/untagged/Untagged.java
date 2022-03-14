package net.fabricmc.mante.untagged;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;

@Environment(EnvType.SERVER)
public class Untagged implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        NameTagCommand.load();

        ServerLifecycleEvents.SERVER_STARTED.register(s -> {
            if (s.getScoreboard().getTeam("mantevian:untagged.hidden") != null)
                return;

            Team team = s.getScoreboard().addTeam("mantevian:untagged.hidden");
            team.setNameTagVisibilityRule(AbstractTeam.VisibilityRule.NEVER);
            s.getScoreboard().updateScoreboardTeamAndPlayers(team);
        });
    }
}
