public class Side extends MenuItem {
    private int weight;

    public Side (String name, int price, String desc, int weight) {
        super(name, price, desc);
        this.weight = weight;
    }

    @Override
    public String getCategory() {
        return "Side";
    }
    
    @Override
    public void displayInfo() {
        System.out.println("[Side]:" + getName() + "| [price]:" + getPrice() + "원|[weight]:" + weight + "g" );
    }
}