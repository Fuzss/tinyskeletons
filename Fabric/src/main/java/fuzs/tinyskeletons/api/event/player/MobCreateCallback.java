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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Callback for when an entity is created from the corresponding entity type in {@link net.minecraft.world.entity.EntityType#spawn}
 * or from {@link net.minecraft.world.level.NaturalSpawner#spawnCategoryForPosition}, but after it has been added to the world.
 *
 * Returns <code>false</code> by default, which allows vanilla mob to be spawned.
 * Returning <code>true</code> on the other hand will remove the spawned mob from the world again , allowing you to create your own mob instead.
 */
public interface MobCreateCallback {
	Event<MobCreateCallback> EVENT = EventFactory.createArrayBacked(MobCreateCallback.class,
			(listeners) -> (Level level, Mob mob, MobSpawnType spawnReason) -> {
				for (MobCreateCallback event : listeners) {
					if (event.spawn(level, mob, spawnReason)) {
						return true;
					}
				}
				return false;
			}
	);

	boolean spawn(Level level, Mob mob, MobSpawnType spawnReason);
}
