package ros.eagleoffire.roswallwalk.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import ros.eagleoffire.roswallwalk.ROSWallWalk;
import ros.eagleoffire.roswallwalk.capabilities.WallWalkingCapability;
import ros.eagleoffire.roswallwalk.networking.ModMessages;
import ros.eagleoffire.roswallwalk.networking.packet.WallWalkingDataSyncS2CPacket;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = ROSWallWalk.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof Player) {
                if(!event.getObject().getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).isPresent()) {
                    event.addCapability(new ResourceLocation(ROSWallWalk.MODID, "properties"), new WallWalkingProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if(event.isWasDeath()) {
                event.getOriginal().getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(WallWalkingCapability.class);
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if(event.side == LogicalSide.SERVER) {
                event.player.getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(WallWalking -> {
                    ModMessages.sendToPlayer(new WallWalkingDataSyncS2CPacket(WallWalking.isWallWalking()), ((ServerPlayer) event.player));
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if(!event.getLevel().isClientSide()) {
                if(event.getEntity() instanceof ServerPlayer player) {
                    player.getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(WallWalking -> {
                        ModMessages.sendToPlayer(new WallWalkingDataSyncS2CPacket(WallWalking.isWallWalking()), player);
                    });
                }
            }
        }

    }
}