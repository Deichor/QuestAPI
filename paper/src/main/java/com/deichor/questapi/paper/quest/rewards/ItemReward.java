package com.deichor.questapi.paper.quest.rewards;

import com.deichor.questapi.core.model.QuestReward;
import com.deichor.questapi.paper.quest.BukkitPlayerOwner;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements QuestReward<ItemStack, BukkitPlayerOwner> {

    private final ItemStack itemStack;

    public ItemReward(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getReward() {
        return itemStack;
    }

    @Override
    public void giveReward(BukkitPlayerOwner owner) {

    }
}
