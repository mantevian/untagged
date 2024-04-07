package net.fabricmc.mante.untagged;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class NameTagCommand {
    public static void load() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            LiteralCommandNode<ServerCommandSource> name_tag_node = CommandManager
                    .literal("nametag")
                    .build();

            LiteralCommandNode<ServerCommandSource> hide_node = CommandManager
                    .literal("hide")
                    .executes(NameTagCommand::hide)
                    .build();

            LiteralCommandNode<ServerCommandSource> show_node = CommandManager
                    .literal("show")
                    .executes(NameTagCommand::show)
                    .build();

            dispatcher.getRoot().addChild(name_tag_node);
            name_tag_node.addChild(hide_node);
            name_tag_node.addChild(show_node);
        });
    }


    private static int hide(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFeedback(() -> Text.of("Player not found"), false);
            return 0;
        }
        Team team = Objects.requireNonNull(player.getServer()).getScoreboard().getTeam("mantevian:untagged.hidden");
        String name = ScoreHolder.fromProfile(player.getGameProfile()).getNameForScoreboard();
        Objects.requireNonNull(player.getServer()).getScoreboard().addScoreHolderToTeam(name, team);
        context.getSource().sendFeedback(() -> Text.of("Name tag of " + name + " is hidden from other players"), false);
        return 1;
    }


    private static int show(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFeedback(() -> Text.of("Player not found"), false);
            return 0;
        }
        Team team = Objects.requireNonNull(player.getServer()).getScoreboard().getTeam("mantevian:untagged.hidden");
        String name = ScoreHolder.fromProfile(player.getGameProfile()).getNameForScoreboard();
        Objects.requireNonNull(player.getServer()).getScoreboard().removeScoreHolderFromTeam(name, team);
        context.getSource().sendFeedback(() -> Text.of("Name tag of " + name + " is shown to other players"), false);

        return 1;
    }
}
