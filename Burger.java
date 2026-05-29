public class Burger extends MenuItem{
    private boolean isSpicy;

    public Burger(String name, int price, String desc, boolean isSpicy) {
        super(name, price, desc);
        this.isSpicy = isSpicy;
    }

    @Override
    public String getCategory() {
        return "Burger";
    }
    
    @Override
    public void displayInfo() {
        System.out.println("[Burger]:" + getName() + "| [price]:" + getPrice() + "원|[spicy]:" + isSpicy);
    }
}