package ros.eagleoffire.roswallwalk.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ros.eagleoffire.roswallwalk.ROSWallWalk;
import ros.eagleoffire.roswallwalk.client.KeyBindings;
import ros.eagleoffire.roswallwalk.events.WallWalkingProvider;
import ros.eagleoffire.roswallwalk.networking.ModMessages;
import ros.eagleoffire.roswallwalk.networking.packet.WallWalkingDataSyncC2SPacket;
import ros.eagleoffire.roswallwalk.networking.packet.WallWalkingDataSyncS2CPacket;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    private static final Component WALL_WALK_KEY_PRESSED =
            Component.translatable("message." + ROSWallWalk.MODID + ".example_key_pressed");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if(KeyBindings.INSTANCE.WALL_WALK_KEY.consumeClick()) {
            ModMessages.sendToServer(new WallWalkingDataSyncC2SPacket());
        }
    }
}
