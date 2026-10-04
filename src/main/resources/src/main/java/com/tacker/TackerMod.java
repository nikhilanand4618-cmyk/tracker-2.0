package com.tacker;

import com.tacker.commands.CommandSetPrice;
import com.tacker.commands.CommandTackerGui;
import com.tacker.gui.GuiTacker;
import com.tacker.hud.HudRenderer;
import com.tacker.tracker.DropTracker;
import com.tacker.util.PriceManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@Mod(modid = TackerMod.MODID, name = "Tacker", version = "1.0.0", clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
@SideOnly(Side.CLIENT)
public class TackerMod {

    public static final String MODID = "tacker";
    public static TackerMod instance;
    public static boolean hudEnabled = true;
    public static boolean dragging = false;
    public static int hudX = 5;
    public static int hudY = 5;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        instance = this;
        PriceManager.load();
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new CommandTackerGui());
        ClientCommandHandler.instance.registerCommand(new CommandSetPrice());
    }

    @SubscribeEvent
    public void onClientChat(ClientChatReceivedEvent event) {
        if (event.message == null) return;
        String text = event.message.getUnformattedText();
        if (text == null) return;
        DropTracker.process(text);
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        if (Minecraft.getMinecraft().currentScreen instanceof GuiTacker) return;
        HudRenderer.render(event.resolution);
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (!dragging) return;
        if (event.button == 0 && event.buttonstate) {
            dragging = false;
            PriceManager.save();
        }
    }

    public static void updateDragPosition() {
        if (!dragging) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null) return;
        net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(mc);
        hudX = Mouse.getX() * sr.getScaledWidth() / mc.displayWidth;
        hudY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / mc.displayHeight - 1;
    }
}
