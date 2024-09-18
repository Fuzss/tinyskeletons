package fuzs.tinyskeletons.world.entity.monster.projectile;

import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Mushroom extends ThrowableItemProjectile {

    public Mushroom(EntityType<? extends Mushroom> entityType, Level level) {
        super(entityType, level);
    }

    public Mushroom(Level level, LivingEntity shooter) {
        super(ModRegistry.MUSHROOM_ENTITY_TYPE.value(), shooter, level);
    }

    public Mushroom(Level level, double x, double y, double z) {
        super(ModRegistry.MUSHROOM_ENTITY_TYPE.value(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        // this is not important to save, might change on entity reload therefore
        return this.random.nextBoolean() ? Items.RED_MUSHROOM : Items.BROWN_MUSHROOM;
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
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
