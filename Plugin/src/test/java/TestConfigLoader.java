import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ernestorb.tablistmanager.plugin.loaders.ConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

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
        Assertions.assertEquals(footers.get("hola").get(0), "hola");
    }

    @Test
    public void testHeader() {
        var headers = new ConfigLoader(pathToFile).getHeaders().get();
        assertNotNull(headers);
        assertTrue(headers.containsKey("world"));
        Assertions.assertEquals(headers.get("world").get(0), "1");
    }

    @Test
    public void testGetWorldHeader() {
        var loader = new ConfigLoader(pathToFile);
        var worldHeaderOptional = loader.getWorldHeader("world");
        assertTrue(
                worldHeaderOptional.isPresent()
        );
        Assertions.assertEquals(worldHeaderOptional.get().get(0), "1");
        var notConfiguredHeaderOptional = loader.getWorldHeader("worldNotIncluded");
        assertTrue(
                notConfiguredHeaderOptional.isPresent()
        );
        Assertions.assertEquals(notConfiguredHeaderOptional.get().size(), 0);
    }

    @Test
    public void testGetWorldFooter() {
        var loader = new ConfigLoader(pathToFile);
        var worldFooterOptional = loader.getWorldFooter("world");
        assertTrue(
                worldFooterOptional.isPresent()
        );
        Assertions.assertEquals(worldFooterOptional.get().size(), 0);
    }
}
