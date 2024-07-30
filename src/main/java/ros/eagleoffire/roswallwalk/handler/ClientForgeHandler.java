package ros.eagleoffire.roswallwalk.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ros.eagleoffire.roswallwalk.ROSWallWalk;
import ros.eagleoffire.roswallwalk.client.KeyBindings;
import ros.eagleoffire.roswallwalk.events.WallWalkingProvider;
import ros.eagleoffire.roswallwalk.networking.ModMessages;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    private static final Component WALL_WALK_KEY_PRESSED =
            Component.translatable("message." + ROSWallWalk.MODID + ".example_key_pressed");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (KeyBindings.INSTANCE.WALL_WALK_KEY.isDown()) {
            KeyBindings.INSTANCE.WALL_WALK_KEY.consumeClick();
            //mc.player.displayClientMessage(WALL_WALK_KEY_PRESSED, true);
            mc.player.getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(cap -> {
                boolean current = cap.isWallWalking();
                cap.setWallWalking(!current);
                String message = "Wall walking toggled: " + !current;
                mc.player.displayClientMessage(WALL_WALK_KEY_PRESSED, true);
                mc.player.sendSystemMessage(Component.literal(message));
            });
        }
    }
}
