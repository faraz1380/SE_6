package main;

public class Package {
    private double weight;
    private ShippingStrategy shippingStrategy;
    private PackageState state;
    private double cost;

    public Package(double weight) {
        this.weight = weight;
        this.shippingStrategy = null;
        this.state = null;
        this.cost = 0;
    }

    public void printState() {
        if (this.state != null) {
            System.out.println("Current state: " + this.state.toString());
        } else {
            System.out.println("No state set.");
        }
    }

    public void setShippingStrategy(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }

    public void setState(PackageState state) {
        this.state = state;
        this.state.enterState(this);
    }

    public void calculateCost() {
        if (this.shippingStrategy != null) {
            this.cost = this.shippingStrategy.calculateCost(this.weight);
        }
    }


    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return this.cost;
    }


    public void deliver() {
        System.out.println("Ending the program as the package has been delivered.");
        System.exit(0);
    }

    public PackageState getState() {
        return state;
    }
}
