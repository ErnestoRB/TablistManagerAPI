import com.ernestorb.tablistmanager.plugin.loaders.ConfigLoader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class TestConfigLoader {

    private static File pathToFile;

    static {
        try {
            pathToFile = new File(TestConfigLoader.class.getResource("configExample.yml").toURI());
        } catch (URISyntaxException e) {
            System.out.println("File could not be loaded");
        }
    }

    @Test
    public void testFooter() {
        var footers = new ConfigLoader(pathToFile).getFooters().get();
        assertNotNull(footers);
        assertTrue(footers.containsKey("hola"));
        assertEquals(footers.get("hola").get(0), "hola");
    }

    @Test
    public void testHeader() {
        var headers = new ConfigLoader(pathToFile).getHeaders().get();
        assertNotNull(headers);
        assertTrue(headers.containsKey("world"));
        assertEquals(headers.get("world").get(0), "1");
    }

    @Test
    public void testGetWorldHeader() {
        var loader = new ConfigLoader(pathToFile);
        var worldHeaderOptional = loader.getWorldHeader("world");
        assertTrue(
                worldHeaderOptional.isPresent()
        );
        assertEquals(worldHeaderOptional.get().get(0), "1");
        var notConfiguredHeaderOptional = loader.getWorldHeader("worldNotIncluded");
        assertTrue(
                notConfiguredHeaderOptional.isEmpty()
        );
    }

    @Test
    public void testGetWorldFooter() {
        var loader = new ConfigLoader(pathToFile);
        var worldFooterOptional = loader.getWorldFooter("hola");
        assertTrue(
                worldFooterOptional.isPresent()
        );
        assertEquals(worldFooterOptional.get().size(), 2);
        assertEquals(worldFooterOptional.get().get(0), "hola");
        var notConfiguredFooterOptional = loader.getWorldHeader("worldNotIncluded");
        assertTrue(
                notConfiguredFooterOptional.isEmpty()
        );
    }

    @Test
    public void testDefaultHeader() {
        var loader = new ConfigLoader(pathToFile);
        assertEquals(loader.getDefaultHeader().size(), 1);
        assertEquals(loader.getDefaultHeader().get(0), "default");
    }

    @Test
    public void testDefaultFooter() {
        var loader = new ConfigLoader(pathToFile);
        assertEquals(loader.getDefaultFooter().size(), 1);
        assertEquals(loader.getDefaultFooter().get(0), "tluafed");
    }
}
