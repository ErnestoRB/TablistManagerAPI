import com.ernestorb.tablistmanager.utils.FakePlayerUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFakePlayerUtil {

    @Test
    public void testRandomPlayerName() {
        String generated = FakePlayerUtil.randomName();
        assertEquals(generated.length(), 10);
        assertEquals(generated.substring(0, 2), "zz");
    }
}
