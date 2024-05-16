package main;

public class DeliveredState implements PackageState {
    @Override
    public void enterState(Package pkg) {
        System.out.println("The package has been delivered.");
        pkg.deliver();
    }

    @Override
    public String toString() {
        return "Delivered";
    }
}
