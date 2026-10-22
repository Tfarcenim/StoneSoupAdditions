package tfar.stonesoupadditions.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import tfar.stonesoupadditions.attachments.DataAttachmentUtil;
import tfar.stonesoupadditions.init.SSAItems;

public class DuelingShieldItem extends ShieldItem {

    public DuelingShieldItem(Properties properties) {
        super(properties);
    }//Unbreaking, Mending, Sharpness, Knockback

    /**
     * Neo: Method overload to allow giving a float for damage instead of an int.
     */
    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, attackDamage + 0, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public static void doDashAttack(Player player, Entity entity) {
        Vec3 force = player.getDeltaMovement();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.knockback(force.length(), -force.x, -force.z);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            DataAttachmentUtil.setHasClickedRightRecently(serverPlayer);
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return super.canAttackBlock(state, level, pos, player);
    }

    public static void checkForDash(ServerPlayer player) {
        boolean hasClickedLeftRecently = DataAttachmentUtil.hasClickedLeftRecently(player);
        boolean hasClickedRightRecently = DataAttachmentUtil.hasClickedRightRecently(player);
        int shields = getShieldCount(player);
        if (player.isShiftKeyDown() && hasClickedRightRecently && hasClickedLeftRecently && shields > 0) {
            double force = shields * 2;
            player.addDeltaMovement(player.getLookAngle().scale(force));
            player.hurtMarked = true;
            DataAttachmentUtil.setDashing(player);

        }
    }

    public static int getShieldCount(Player player) {
        int shields = 0;
        if (player.getMainHandItem().is(SSAItems.DUELING_SHIELD)) {
            shields++;
        }
        if (player.getOffhandItem().is(SSAItems.DUELING_SHIELD)) {
            shields++;
        }
        return shields;
    }
}
