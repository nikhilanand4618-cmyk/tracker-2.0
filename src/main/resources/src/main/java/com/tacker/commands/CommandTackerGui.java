package com.tacker.commands;

import com.tacker.gui.GuiTacker;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandTackerGui extends CommandBase {

    @Override
    public String getCommandName() {
        return "tackergui";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/tackergui";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft.getMinecraft().displayGuiScreen(new GuiTacker());
    }
}
