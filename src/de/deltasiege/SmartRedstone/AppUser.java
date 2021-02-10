package de.deltasiege.SmartRedstone;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class AppUser implements HumanEntity {

	private UUID tempUUID;
	public AppUser(UUID uuid) {
		tempUUID = uuid;
	}
	
	@Override
	public boolean addPotionEffect(PotionEffect arg0) {
		
		return false;
	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0, boolean arg1) {
		
		return false;
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> arg0) {
		
		return false;
	}

	@Override
	public void attack(Entity arg0) {
		
		
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		
		return null;
	}

	@Override
	public int getArrowCooldown() {
		
		return 0;
	}

	@Override
	public int getArrowsInBody() {
		
		return 0;
	}

	@Override
	public boolean getCanPickupItems() {
		
		return false;
	}

	@Override
	public EntityCategory getCategory() {
		
		return null;
	}

	@Override
	public Set<UUID> getCollidableExemptions() {
		
		return null;
	}

	@Override
	public EntityEquipment getEquipment() {
		
		return null;
	}

	@Override
	public double getEyeHeight() {
		
		return 0;
	}

	@Override
	public double getEyeHeight(boolean arg0) {
		
		return 0;
	}

	@Override
	public Location getEyeLocation() {
		
		return null;
	}

	@Override
	public Player getKiller() {
		
		return null;
	}

	@Override
	public double getLastDamage() {
		
		return 0;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> arg0, int arg1) {
		
		return null;
	}

	@Override
	public Entity getLeashHolder() throws IllegalStateException {
		
		return null;
	}

	@Override
	public List<Block> getLineOfSight(Set<Material> arg0, int arg1) {
		
		return null;
	}

	@Override
	public int getMaximumAir() {
		
		return 0;
	}

	@Override
	public int getMaximumNoDamageTicks() {
		
		return 0;
	}

	@Override
	public <T> T getMemory(MemoryKey<T> arg0) {
		
		return null;
	}

	@Override
	public int getNoDamageTicks() {
		
		return 0;
	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType arg0) {
		
		return null;
	}

	@Override
	public int getRemainingAir() {
		
		return 0;
	}

	@Override
	public boolean getRemoveWhenFarAway() {
		
		return false;
	}

	@Override
	public Block getTargetBlock(Set<Material> arg0, int arg1) {
		
		return null;
	}

	@Override
	public Block getTargetBlockExact(int arg0) {
		
		return null;
	}

	@Override
	public Block getTargetBlockExact(int arg0, FluidCollisionMode arg1) {
		
		return null;
	}

	@Override
	public boolean hasAI() {
		
		return false;
	}

	@Override
	public boolean hasLineOfSight(Entity arg0) {
		
		return false;
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType arg0) {
		
		return false;
	}

	@Override
	public boolean isCollidable() {
		
		return false;
	}

	@Override
	public boolean isGliding() {
		
		return false;
	}

	@Override
	public boolean isInvisible() {
		
		return false;
	}

	@Override
	public boolean isLeashed() {
		
		return false;
	}

	@Override
	public boolean isRiptiding() {
		
		return false;
	}

	@Override
	public boolean isSleeping() {
		
		return false;
	}

	@Override
	public boolean isSwimming() {
		
		return false;
	}

	@Override
	public RayTraceResult rayTraceBlocks(double arg0) {
		
		return null;
	}

	@Override
	public RayTraceResult rayTraceBlocks(double arg0, FluidCollisionMode arg1) {
		
		return null;
	}

	@Override
	public void removePotionEffect(PotionEffectType arg0) {
		
		
	}

	@Override
	public void setAI(boolean arg0) {
		
		
	}

	@Override
	public void setArrowCooldown(int arg0) {
		
		
	}

	@Override
	public void setArrowsInBody(int arg0) {
		
		
	}

	@Override
	public void setCanPickupItems(boolean arg0) {
		
		
	}

	@Override
	public void setCollidable(boolean arg0) {
		
		
	}

	@Override
	public void setGliding(boolean arg0) {
		
		
	}

	@Override
	public void setInvisible(boolean arg0) {
		
		
	}

	@Override
	public void setLastDamage(double arg0) {
		
		
	}

	@Override
	public boolean setLeashHolder(Entity arg0) {
		
		return false;
	}

	@Override
	public void setMaximumAir(int arg0) {
		
		
	}

	@Override
	public void setMaximumNoDamageTicks(int arg0) {
		
		
	}

	@Override
	public <T> void setMemory(MemoryKey<T> arg0, T arg1) {
		
		
	}

	@Override
	public void setNoDamageTicks(int arg0) {
		
		
	}

	@Override
	public void setRemainingAir(int arg0) {
		
		
	}

	@Override
	public void setRemoveWhenFarAway(boolean arg0) {
		
		
	}

	@Override
	public void setSwimming(boolean arg0) {
		
		
	}

	@Override
	public void swingMainHand() {
		
		
	}

	@Override
	public void swingOffHand() {
		
		
	}

	@Override
	public AttributeInstance getAttribute(Attribute arg0) {
		
		return null;
	}

	@Override
	public void damage(double arg0) {
		
		
	}

	@Override
	public void damage(double arg0, Entity arg1) {
		
		
	}

	@Override
	public double getAbsorptionAmount() {
		
		return 0;
	}

	@Override
	public double getHealth() {
		
		return 0;
	}

	@Override
	public double getMaxHealth() {
		
		return 0;
	}

	@Override
	public void resetMaxHealth() {
		
		
	}

	@Override
	public void setAbsorptionAmount(double arg0) {
		
		
	}

	@Override
	public void setHealth(double arg0) {
		
		
	}

	@Override
	public void setMaxHealth(double arg0) {
		
		
	}

	@Override
	public boolean addPassenger(Entity arg0) {
		
		return false;
	}

	@Override
	public boolean addScoreboardTag(String arg0) {
		
		return false;
	}

	@Override
	public boolean eject() {
		
		return false;
	}

	@Override
	public BoundingBox getBoundingBox() {
		
		return null;
	}

	@Override
	public int getEntityId() {
		
		return 0;
	}

	@Override
	public BlockFace getFacing() {
		
		return null;
	}

	@Override
	public float getFallDistance() {
		
		return 0;
	}

	@Override
	public int getFireTicks() {
		
		return 0;
	}

	@Override
	public double getHeight() {
		
		return 0;
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		
		return null;
	}

	@Override
	public Location getLocation() {
		
		return null;
	}

	@Override
	public Location getLocation(Location arg0) {
		
		return null;
	}

	@Override
	public int getMaxFireTicks() {
		
		return 0;
	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
		
		return null;
	}

	@Override
	public Entity getPassenger() {
		
		return null;
	}

	@Override
	public List<Entity> getPassengers() {
		
		return null;
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		
		return null;
	}

	@Override
	public int getPortalCooldown() {
		
		return 0;
	}

	@Override
	public Pose getPose() {
		
		return null;
	}

	@Override
	public Set<String> getScoreboardTags() {
		
		return null;
	}

	@Override
	public Server getServer() {
		
		return null;
	}

	@Override
	public int getTicksLived() {
		
		return 0;
	}

	@Override
	public EntityType getType() {
		
		return null;
	}

	@Override
	public UUID getUniqueId() {
		return tempUUID;
	}

	@Override
	public Entity getVehicle() {
		
		return null;
	}

	@Override
	public Vector getVelocity() {
		
		return null;
	}

	@Override
	public double getWidth() {
		
		return 0;
	}

	@Override
	public World getWorld() {
		
		return null;
	}

	@Override
	public boolean hasGravity() {
		
		return false;
	}

	@Override
	public boolean isCustomNameVisible() {
		
		return false;
	}

	@Override
	public boolean isDead() {
		
		return false;
	}

	@Override
	public boolean isEmpty() {
		
		return false;
	}

	@Override
	public boolean isGlowing() {
		
		return false;
	}

	@Override
	public boolean isInWater() {
		
		return false;
	}

	@Override
	public boolean isInsideVehicle() {
		
		return false;
	}

	@Override
	public boolean isInvulnerable() {
		
		return false;
	}

	@Override
	public boolean isOnGround() {
		
		return false;
	}

	@Override
	public boolean isPersistent() {
		
		return false;
	}

	@Override
	public boolean isSilent() {
		
		return false;
	}

	@Override
	public boolean isValid() {
		
		return false;
	}

	@Override
	public boolean leaveVehicle() {
		
		return false;
	}

	@Override
	public void playEffect(EntityEffect arg0) {
		
		
	}

	@Override
	public void remove() {
		
		
	}

	@Override
	public boolean removePassenger(Entity arg0) {
		
		return false;
	}

	@Override
	public boolean removeScoreboardTag(String arg0) {
		
		return false;
	}

	@Override
	public void setCustomNameVisible(boolean arg0) {
		
		
	}

	@Override
	public void setFallDistance(float arg0) {
		
		
	}

	@Override
	public void setFireTicks(int arg0) {
		
		
	}

	@Override
	public void setGlowing(boolean arg0) {
		
		
	}

	@Override
	public void setGravity(boolean arg0) {
		
		
	}

	@Override
	public void setInvulnerable(boolean arg0) {
		
		
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0) {
		
		
	}

	@Override
	public boolean setPassenger(Entity arg0) {
		
		return false;
	}

	@Override
	public void setPersistent(boolean arg0) {
		
		
	}

	@Override
	public void setPortalCooldown(int arg0) {
		
		
	}

	@Override
	public void setRotation(float arg0, float arg1) {
		
		
	}

	@Override
	public void setSilent(boolean arg0) {
		
		
	}

	@Override
	public void setTicksLived(int arg0) {
		
		
	}

	@Override
	public void setVelocity(Vector arg0) {
		
		
	}

	@Override
	public Spigot spigot() {
		
		return null;
	}

	@Override
	public boolean teleport(Location arg0) {
		
		return false;
	}

	@Override
	public boolean teleport(Entity arg0) {
		
		return false;
	}

	@Override
	public boolean teleport(Location arg0, TeleportCause arg1) {
		
		return false;
	}

	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1) {
		
		return false;
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		
		return null;
	}

	@Override
	public boolean hasMetadata(String arg0) {
		
		return false;
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		
		
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		
		
	}

	@Override
	public void sendMessage(String arg0) {
		
		
	}

	@Override
	public void sendMessage(String[] arg0) {
		
		
	}

	@Override
	public void sendMessage(UUID arg0, String arg1) {
		
		
	}

	@Override
	public void sendMessage(UUID arg0, String[] arg1) {
		
		
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		
		return null;
	}

	@Override
	public boolean hasPermission(String arg0) {
		
		return false;
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		
		return false;
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		
		return false;
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		
		return false;
	}

	@Override
	public void recalculatePermissions() {
		
		
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		
		
	}

	@Override
	public boolean isOp() {
		
		return false;
	}

	@Override
	public void setOp(boolean arg0) {
		
		
	}

	@Override
	public String getCustomName() {
		
		return null;
	}

	@Override
	public void setCustomName(String arg0) {
		
		
	}

	@Override
	public PersistentDataContainer getPersistentDataContainer() {
		
		return null;
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0) {
		
		return null;
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0, Vector arg1) {
		
		return null;
	}

	@Override
	public void closeInventory() {
		
		
	}

	@Override
	public boolean discoverRecipe(NamespacedKey arg0) {
		
		return false;
	}

	@Override
	public int discoverRecipes(Collection<NamespacedKey> arg0) {
		
		return 0;
	}

	@Override
	public boolean dropItem(boolean arg0) {
		
		return false;
	}

	@Override
	public float getAttackCooldown() {
		
		return 0;
	}

	@Override
	public Location getBedLocation() {
		
		return null;
	}

	@Override
	public int getCooldown(Material arg0) {
		
		return 0;
	}

	@Override
	public Set<NamespacedKey> getDiscoveredRecipes() {
		
		return null;
	}

	@Override
	public Inventory getEnderChest() {
		
		return null;
	}

	@Override
	public int getExpToLevel() {
		
		return 0;
	}

	@Override
	public GameMode getGameMode() {
		
		return null;
	}

	@Override
	public PlayerInventory getInventory() {
		
		return null;
	}

	@Override
	public ItemStack getItemInHand() {
		
		return null;
	}

	@Override
	public ItemStack getItemOnCursor() {
		
		return null;
	}

	@Override
	public MainHand getMainHand() {
		
		return null;
	}

	@Override
	public String getName() {
		
		return null;
	}

	@Override
	public InventoryView getOpenInventory() {
		
		return null;
	}

	@Override
	public Entity getShoulderEntityLeft() {
		
		return null;
	}

	@Override
	public Entity getShoulderEntityRight() {
		
		return null;
	}

	@Override
	public int getSleepTicks() {
		
		return 0;
	}

	@Override
	public boolean hasCooldown(Material arg0) {
		
		return false;
	}

	@Override
	public boolean hasDiscoveredRecipe(NamespacedKey arg0) {
		
		return false;
	}

	@Override
	public boolean isBlocking() {
		
		return false;
	}

	@Override
	public boolean isHandRaised() {
		
		return false;
	}

	@Override
	public InventoryView openEnchanting(Location arg0, boolean arg1) {
		
		return null;
	}

	@Override
	public InventoryView openInventory(Inventory arg0) {
		
		return null;
	}

	@Override
	public void openInventory(InventoryView arg0) {
		
		
	}

	@Override
	public InventoryView openMerchant(Villager arg0, boolean arg1) {
		
		return null;
	}

	@Override
	public InventoryView openMerchant(Merchant arg0, boolean arg1) {
		
		return null;
	}

	@Override
	public InventoryView openWorkbench(Location arg0, boolean arg1) {
		
		return null;
	}

	@Override
	public void setCooldown(Material arg0, int arg1) {
		
		
	}

	@Override
	public void setGameMode(GameMode arg0) {
		
		
	}

	@Override
	public void setItemInHand(ItemStack arg0) {
		
		
	}

	@Override
	public void setItemOnCursor(ItemStack arg0) {
		
		
	}

	@Override
	public void setShoulderEntityLeft(Entity arg0) {
		
		
	}

	@Override
	public void setShoulderEntityRight(Entity arg0) {
		
		
	}

	@Override
	public boolean setWindowProperty(Property arg0, int arg1) {
		
		return false;
	}

	@Override
	public boolean sleep(Location arg0, boolean arg1) {
		
		return false;
	}

	@Override
	public boolean undiscoverRecipe(NamespacedKey arg0) {
		
		return false;
	}

	@Override
	public int undiscoverRecipes(Collection<NamespacedKey> arg0) {
		
		return 0;
	}

	@Override
	public void wakeup(boolean arg0) {
		
		
	}

}