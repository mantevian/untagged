package net.fabricmc.mante.untagged;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Objects;

public class NameTagCommand {
    public static void load() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
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
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            Team team = Objects.requireNonNull(player.getServer()).getScoreboard().getTeam("mantevian:untagged.hidden");
            Objects.requireNonNull(player.getServer()).getScoreboard().addPlayerToTeam(player.getEntityName(), team);
            context.getSource().sendFeedback(new LiteralText("Your nametag has been hidden from other players"), false);
        }
        catch (CommandSyntaxException e) {
            context.getSource().sendFeedback(new LiteralText("Something went wrong when hiding your nametag"), false);
            return 0;
        }

        return 0;
    }

    private static int show(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            Team team = Objects.requireNonNull(player.getServer()).getScoreboard().getTeam("mantevian:untagged.hidden");
            Objects.requireNonNull(player.getServer()).getScoreboard().removePlayerFromTeam(player.getEntityName(), team);
            context.getSource().sendFeedback(new LiteralText("Your nametag is now shown to other players"), false);
        }
        catch (CommandSyntaxException e) {
            context.getSource().sendFeedback(new LiteralText("Something went wrong when showing your nametag"), false);
            return 0;
        }

        return 0;
    }
}
