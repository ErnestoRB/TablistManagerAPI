import com.ernestorb.tablistmanager.packets.TablistTemplate;
import com.ernestorb.tablistmanager.utils.PlaceholdersUtil;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPlaceholders {

    @Test
    public void placeHoldersGetsReplaced() {
        var placeholders = PlaceholdersUtil.compose((tablistTemplate, player) -> tablistTemplate.replace("%algo%", "Ernesto"), (tablistTemplate, player) -> {

        });
        Player player = Mockito.mock(Player.class);
        var tablist = TablistTemplate.empty();
        tablist.setHeader("%algo%");
        assertNotNull(placeholders);
        placeholders.callback(tablist, player);
        assertEquals("Ernesto", tablist.getHeader());
    }

    @Test
    public void placeHoldersGetsReplaced2() {
        var placeholders = PlaceholdersUtil.compose((tablistTemplate, player) -> tablistTemplate.setHeader("Hola %algo%"), (tablistTemplate, player) ->
                tablistTemplate.replace("%algo%", "Ernesto"));
        Player player = Mockito.mock(Player.class);
        var tablist = TablistTemplate.empty();
        assertNotNull(placeholders);
        placeholders.callback(tablist, player);
        assertEquals("Hola %algo%", tablist.getHeader());
    }
}
