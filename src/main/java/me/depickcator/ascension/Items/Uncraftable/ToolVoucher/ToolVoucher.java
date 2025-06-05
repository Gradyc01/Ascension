package me.depickcator.ascension.Items.Uncraftable.ToolVoucher;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ToolVoucher extends CustomItem implements ItemClick {
    private static ToolVoucher instance;
    private List<VouchableTool> tools;
    private ToolVoucher() {
        super("Tool Voucher", "tool_voucher");
        registerItem();
    }

    private List<VouchableTool> getTools() {
        if (tools == null) {
            tools = new ArrayList<>(List.of(
                    buildVouchableTool(QuickPick.getInstance(), 0, 11),
                    buildVouchableTool(FlintShovel.getInstance(), 0, 13),
                    buildVouchableTool(LumberjackAxe.getInstance(), 0, 15),
                    buildVouchableTool(VorpalSword.getInstance(), 150, 21),
                    buildVouchableTool(PhilosopherPickaxe.getInstance(), 300, 23),
                    buildVouchableTool(AdvancedQuickPick.getInstance(), 400, 29),
                    buildVouchableTool(MinerBlessing.getInstance(), 500, 31),
                    buildVouchableTool(WeaverSilk.getInstance(), 400, 33)
            ));
        }
        return tools;
    }

    private VouchableTool buildVouchableTool(CustomItem item, int cost, int index) {
        return new VouchableTool(item.getResult(), cost, item.getDisplayName(), index);
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.GOLD).append(TextUtil.rightClickText()));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Exchanges souls for a Tool", TextUtil.DARK_PURPLE),
                TextUtil.makeText(""),
                TextUtil.makeText("Tools:", TextUtil.DARK_PURPLE)
        ));
        for (VouchableTool tool : getTools()) {
            lore.add(TextUtil.makeText(" " + tool.displayName() ,tool.item().displayName().color()));
        }
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
            new ToolVoucherGUI(pD, getTools());
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

    public static ToolVoucher getInstance() {
        if (instance == null) {
            instance = new ToolVoucher();
        }
        return instance;
    }
}
