package fuzs.tinyskeletons.world.entity.projectile.throwableitemprojectile;

import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class HurtingItemProjectile extends ThrowableItemProjectile {

    public HurtingItemProjectile(EntityType<? extends HurtingItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public HurtingItemProjectile(Level level, LivingEntity shooter, ItemStack itemStack) {
        super(ModRegistry.THROWN_ITEM_ENTITY_TYPE.value(), shooter, level, itemStack);
    }

    public HurtingItemProjectile(Level level, double x, double y, double z, ItemStack itemStack) {
        super(ModRegistry.THROWN_ITEM_ENTITY_TYPE.value(), x, y, z, level, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public void tick() {
        if (this.getItem().isEmpty()) {
            this.discard();
        } else {
            super.tick();
        }
    }

    protected ParticleOptions getParticle() {
        return new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleOptions = this.getParticle();
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (this.level() instanceof ServerLevel) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
