/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fuzs.tinyskeletons.api.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Callback for right-clicking ("using") an entity on the server side.
 * Fabric API already has an event like this one ({@link net.fabricmc.fabric.api.event.player.UseEntityCallback}), but for some reason it only works client side
 *
 * Returns null by default, which allows vanilla behavior to continue. Returning an {@link InteractionResult} will cancel the interaction and return the result directly
 */
public interface EntityInteractCallback {
	Event<EntityInteractCallback> EVENT = EventFactory.createArrayBacked(EntityInteractCallback.class,
			(listeners) -> (player, world, hand, entity) -> {
				for (EntityInteractCallback event : listeners) {
					InteractionResult result = event.interact(player, world, hand, entity);
					if (result != null) {
						return result;
					}
				}
				return null;
			}
	);

	@Nullable
	InteractionResult interact(Player player, Level world, InteractionHand hand, Entity entity);
}
