package tfar.stonesoupadditions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;import tfar.stonesoupadditions.init.SSABlockEntityTypes;
import tfar.stonesoupadditions.init.SSAMobEffects;

import java.util.List;

public class HeartOfFireBlockEntity extends BlockEntity {

    int lastChecked;

    public HeartOfFireBlockEntity(BlockPos pos, BlockState blockState) {
        super(SSABlockEntityTypes.HEART_OF_FIRE, pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HeartOfFireBlockEntity heartOfFireBlockEntity) {
        List<ServerPlayer> players = ((ServerLevel)level).getPlayers(Entity::isAlive);

        for (ServerPlayer player : players) {
            player.addEffect(new MobEffectInstance(SSAMobEffects.FLIGHT,21,1,true,true));
        }
    }
}
