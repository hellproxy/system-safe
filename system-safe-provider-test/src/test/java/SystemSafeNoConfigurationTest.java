import com.github.twofour.PropertiesAdapter;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author harrydent
 */
@TestMethodOrder(OrderAnnotation.class)
class SystemSafeNoConfigurationTest {

    private static final String PROPERTY_NAME = "foo";
    private static final String PROPERTY_VALUE = "bar";

    @Test
    @Order(1)
    void test_systemProperties_isStubbed() {
        assertThat(System.getProperties()).isInstanceOf(PropertiesAdapter.class);
    }

    @Test
    @Order(2)
    void test_systemProperty_isUnset() {
        assertThat(System.getProperty(PROPERTY_NAME)).isNull();

        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Test
    @Order(3)
    void test_systemProperty_isUnsetAfterBeingPreviouslySet() {
        assertThat(System.getProperty(PROPERTY_NAME)).isNull();
    }
}
