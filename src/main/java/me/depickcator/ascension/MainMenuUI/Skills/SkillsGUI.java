package me.depickcator.ascension.MainMenuUI.Skills;

import me.depickcator.ascension.Skills.Skills;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.MainMenuUI.MainMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SkillsGUI extends AscensionMenuGUI {
    private final PlayerSkills playerSkills;
    public SkillsGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Skills", TextUtil.AQUA), true);
        playerSkills = playerData.getPlayerSkills();
        initSkillColumns();
        inventory.setItem(45, goBackItem());
        playerHeadButton(49);
    }

    private void initSkillColumns() {
        makeSkillColumns(playerSkills.getMining(), new ArrayList<>(List.of(
                TextUtil.makeText("Gain Mining Exp by: ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Mining Ores", TextUtil.DARK_PURPLE)
        )), 1, Material.IRON_PICKAXE);
        makeSkillColumns(playerSkills.getCombat(), new ArrayList<>(List.of(
                TextUtil.makeText("Gain Combat Exp by: ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Killing Mobs", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Killing Players", TextUtil.DARK_PURPLE)
        )), 3, Material.IRON_SWORD);
        makeSkillColumns(playerSkills.getForaging(), new ArrayList<>(List.of(
                TextUtil.makeText("Gain Foraging Exp by: ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Killing Farm Animals", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Gathering Wood & Crops", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Fishing", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  -Looting Chests", TextUtil.DARK_PURPLE)
        )), 5, Material.IRON_AXE);
        makeSkillColumns(playerSkills.getGlobal(), new ArrayList<>(List.of(
                TextUtil.makeText("Gain Global Levels by: ", TextUtil.DARK_PURPLE),
                TextUtil.makeText(" Leveling up all yours skills", TextUtil.DARK_PURPLE),
                TextUtil.makeText("to the same level", TextUtil.DARK_PURPLE),
                TextUtil.makeText("Ex: Having All Level IIs ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("will grant you Global II", TextUtil.DARK_PURPLE)
        )), 7, Material.GRASS_BLOCK);
    }

    private void makeSkillColumns(Skills skill, List<Component> lore, int column, Material icon) {
        ItemStack explainer = initExplainerItem(icon, lore, TextUtil.makeText(skill.getSkillName(), TextUtil.DARK_GREEN));
        int glasses = 5;
        int level = Integer.parseInt(skill.getExpLevel());
        for (int i = column; i < 54; i+=9) {
            if (glasses > 0) {
                inventory.setItem(i, makeTierItem(skill, glasses, level));
                glasses--;
                continue;
            }
            inventory.setItem(i, explainer);
        }
    }

    private ItemStack makeTierItem(Skills skill, int level, int achievedLevel) {
        Material mat = (level <= achievedLevel) ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        ItemStack item = new ItemStack(mat, level);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Level " + TextUtil.toRomanNumeral(level), TextUtil.DARK_PURPLE));
        List<Component> lore = new ArrayList<>(List.of(TextUtil.makeText("Rewards:", TextUtil.DARK_PURPLE)));
        lore.addAll(skill.getRewardText(level));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(goBackItem())) {
            new MainMenuGUI(playerData);
        }
    }
}
