package the.david.tagsSystem.handler;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import the.david.tagsSystem.TagsSystem;
import the.david.tagsSystem.enums.Tag;
import the.david.tagsSystem.manager.TagsManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GuiHandler {
    TagsSystem plugin;
    LuckPerms luckpermsAPI;
    public GuiHandler(TagsSystem plugin){
        this.plugin = plugin;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
        }
    }
    Map<Player, Pane> playerPanes = new HashMap<>();
    void setupGui(Player player){
        StaticPane pane = new StaticPane(0, 0, 9, 6);
        refreshItems(pane, player);
        playerPanes.put(player, pane);
    }
    void refreshItems(StaticPane pane, Player player){
        pane.clear();
        Tag[] tags = Tag.values();
        for(int i = 0; i < tags.length; i++) {
            Tag tag = tags[i];
            boolean hasPermission = player.hasPermission(TagsManager.getTagPermission(tag));
            User user;
            try {
                user = luckpermsAPI.getUserManager().loadUser(player.getUniqueId()).get();
            } catch (InterruptedException | ExecutionException e) {
                return;
            }
            ItemStack itemStack = new ItemStack(Material.BARRIER);
            if(hasPermission){
                itemStack = new ItemStack(Material.NAME_TAG);
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text(tag.name()));
            itemStack.setItemMeta(itemMeta);
            GuiItem item = new GuiItem(itemStack, event ->{
                boolean equipped = false;
                Node suffixNode = null;
                if(hasPermission){
                    for(Node node : user.getNodes(NodeType.SUFFIX)){
                        if(node.getKey().equals(TagsManager.getTagSuffix(tag))){
                            equipped = true;
                            suffixNode = node;
                        }
                    }
                }
                if(equipped){
                    user.data().remove(suffixNode);
                }else{
                    suffixNode = SuffixNode.builder(TagsManager.getTagSuffix(tag), 1).build();
                    user.data().add(suffixNode);
                }
                luckpermsAPI.getUserManager().saveUser(user);
            });
            pane.addItem(item, i/9, i%9);
        }
    }
}
