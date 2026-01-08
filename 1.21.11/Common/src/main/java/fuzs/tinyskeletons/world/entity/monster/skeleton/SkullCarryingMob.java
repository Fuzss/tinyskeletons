package fuzs.tinyskeletons.world.entity.monster.skeleton;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;

public interface SkullCarryingMob {

    default boolean hasSkullItem() {
        return !this.getSkullItem().isEmpty();
    }

    ItemStack getSkullItem();

    default boolean isOnlyCarryingSkull(LivingEntity livingEntity, InteractionHand interactionHand) {
        InteractionHand otherHand = interactionHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        return this.isSkullItem(livingEntity.getItemInHand(interactionHand)) && livingEntity.getItemInHand(otherHand).isEmpty();
    }

    default boolean isSkullItem(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            if (itemStack.getItem() instanceof BlockItem item) {
                return item.getBlock() instanceof AbstractSkullBlock;
            }
        }

        return false;
    }

    boolean isDancing();
}
