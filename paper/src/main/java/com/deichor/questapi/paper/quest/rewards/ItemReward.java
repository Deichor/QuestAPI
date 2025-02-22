package com.deichor.questapi.paper.quest.rewards;

import com.deichor.questapi.core.model.QuestReward;
import com.deichor.questapi.paper.quest.owners.PlayerOwner;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemReward implements QuestReward<ItemStack, PlayerOwner> {

    private final ItemStack itemStack;

    public ItemReward(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getReward() {
        return itemStack;
    }

    @Override
    public void giveReward(PlayerOwner owner) {
        HashMap<Integer, ItemStack> leftOver = owner.getOwner().getInventory().addItem(itemStack);
        // inventory checker
        if(!leftOver.isEmpty()) {
            owner.getOwner().getWorld().dropItem(owner.getOwner().getLocation(), itemStack);
        }
    }
}
