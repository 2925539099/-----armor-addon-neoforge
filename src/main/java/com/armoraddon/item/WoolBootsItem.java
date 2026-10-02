package com.armoraddon.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 羊毛靴子。
 *
 * <p>属性与其它羊毛盔甲部件一致，另外拥有两个额外特性：</p>
 * <ul>
 *   <li>可以像原版皮革靴子一样在细雪上行走 —— 覆盖 NeoForge 在 {@code Item} 上
 *       提供的 {@code canWalkOnPowderedSnow} 扩展方法，其默认实现只对皮革靴子返回 true；</li>
 *   <li>移动时不会向幽匿感测体 / 坚守者发出振动 —— 见 {@link WoolBootsEvents}。</li>
 * </ul>
 */
public class WoolBootsItem extends ArmorItem {
	public WoolBootsItem(Holder<ArmorMaterial> material, Item.Properties properties) {
		super(material, ArmorItem.Type.BOOTS, properties);
	}

	@Override
	public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
		return true;
	}
}
