package the.david.tagsSystem.manager;

import org.bukkit.permissions.Permission;
import the.david.tagsSystem.TagsSystem;
import the.david.tagsSystem.enums.Tag;

import java.util.HashMap;
import java.util.Map;


public class TagsManager {
    TagsSystem plugin;
    public TagsManager(TagsSystem plugin){
        this.plugin = plugin;
    }
    static Map<Tag, String> tagSuffix = new HashMap<>(){{
        put(Tag.PARKOUR_PRO, "[ParkourPro]");
    }};
    public static Permission getTagPermission(Tag tag){
        return new Permission("tag." + tag.name());
    }
    public static String getTagSuffix(Tag tag){
        return tagSuffix.get(tag);
    }
}
