package main;

public class InTransitState implements PackageState {
    @Override
    public void enterState(Package pkg) {
        System.out.println("The package is now in transit.");
    }

    @Override
    public String toString() {
        return "In-transit";
    }
}
