package com.tacker.commands;

import com.tacker.util.PriceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class CommandSetPrice extends CommandBase {

    @Override
    public String getCommandName() {
        return "setprice";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/setprice <amount>";
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
        Minecraft mc = Minecraft.getMinecraft();
        if (args.length < 1) {
            mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7cUsage: /setprice <110k | 1.5m | 25000>"));
            return;
        }
        ItemStack held = mc.thePlayer.getHeldItem();
        if (held == null) {
            mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7cHold an item first."));
            return;
        }
        long price = PriceManager.parsePrice(args[0]);
        if (price <= 0) {
            mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7cInvalid price. Examples: 110k, 1.5m, 50000"));
            return;
        }
        String itemName = held.getDisplayName();
        PriceManager.setPrice(itemName, price);
        mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7aSet " + itemName + " \u00a7ato \u00a7e" + PriceManager.format(price)));
    }
}
