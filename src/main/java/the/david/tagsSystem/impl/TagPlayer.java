package the.david.tagsSystem.impl;

import org.bukkit.entity.Player;
import the.david.tagsSystem.enums.Tag;

import java.util.HashSet;
import java.util.Set;

public class TagPlayer {
    Player player;
    public TagPlayer(Player player){
        this.player = player;
    }
    Set<Tag> tags = new HashSet<>();

}
