package properties;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SystemPropertiesTests {
    @Test
    @Tag("property")
    void systemProperties4Test() {
        String browser = System.getProperty("browser", "opera");
        System.out.println(browser);
}

}
