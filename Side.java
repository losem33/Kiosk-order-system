public class Side extends MenuItem {
    private int weight;

    public Side(String name, int price, String desc, int weight) {
        super(name, price, desc);
        this.weight = weight;
    }

    @Override
    public String getCategory() { return "side"; }

    @Override
    public void displayInfo() {
        System.out.println("[사이드] " + getName() + " | " + getPrice() + "원 | " + weight + "g");
    }
}
