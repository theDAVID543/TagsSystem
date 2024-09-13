package the.david.tagsSystem.handler;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import the.david.tagsSystem.TagsSystem;
import the.david.tagsSystem.enums.Tag;

public class GuiHandler {
    TagsSystem plugin;
    public GuiHandler(TagsSystem plugin){
        this.plugin = plugin;
    }
    void setupGui(Player player){
        StaticPane pane = new StaticPane(0, 0, 9, 6);
        addItems(pane);
    }
    void addItems(StaticPane pane){
        Tag[] tags = Tag.values();
        for(int i = 0; i < tags.length; i++) {
            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text(tags[i].name()));
            itemStack.setItemMeta(itemMeta);
            GuiItem item = new GuiItem(itemStack);
            pane.addItem(item, i/9, i%9);
        }
    }
}
