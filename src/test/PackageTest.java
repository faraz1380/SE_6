package test;
import main.*;
import main.Package;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PackageTest {

    @Test
    public void testCreateClass() {
        double weight = 10.0;
        Package pkg = new Package(weight);
        assertNotNull(pkg, "Package object should be created successfully.");
        assertEquals(weight, pkg.getWeight(), "Package weight should be set correctly.");
        assertEquals(0.0, pkg.getCost(), "Initial cost should be 0.0.");
    }

    @Test
    public void testSetShipping() {
        double weight = 10.0;
        Package pkg = new Package(weight);
        ShippingStrategy standardShipping = new StandardShipping();
        ShippingStrategy expressShipping = new ExpressShipping();

        pkg.setShippingStrategy(standardShipping);
        pkg.calculateCost();
        assertEquals(weight * 2.5, pkg.getCost(), 0.01, "Standard shipping cost should be calculated correctly.");

        pkg.setShippingStrategy(expressShipping);
        pkg.calculateCost();
        assertEquals(weight * 3.5, pkg.getCost(), 0.01, "Express shipping cost should be calculated correctly.");
    }

    @Test
    public void testSendPackage() {
        double weight = 10.0;
        Package pkg = new Package(weight);
        PackageState inTransitState = new InTransitState();
        PackageState deliveredState = new DeliveredState();

        pkg.setState(inTransitState);
        assertTrue(pkg.getState() instanceof InTransitState, "Package state should be InTransitState.");

        pkg.setState(deliveredState);
        assertTrue(pkg.getState() instanceof DeliveredState, "Package state should be DeliveredState.");
    }
}
