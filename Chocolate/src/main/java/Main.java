import chocolate.Chocolate;

public class Main {
    public static void main(String[] args) {
        Chocolate c = new Chocolate();
        System.out.println(c.calculateTotalOfChocolates(2, 1, 2)); //R1
        System.out.println(c.calculateTotalOfChocolates(4, 1, 2)); //R2 - resultado n bate
        System.out.println(c.calculateTotalOfChocolates(2, 1, 3)); //R3
        System.out.println(c.calculateTotalOfChocolates(3, 1, 2)); //R4 - resultado n bate
        System.out.println(c.calculateTotalOfChocolates(3, 1, 3)); //R5
        System.out.println(c.calculateTotalOfChocolates(2, 2, 2)); //R6
        System.out.println(c.calculateTotalOfChocolates(2, 3, 2)); //R7
//        System.out.println(c.calculateTotalOfChocolates(-1, 2, 3)); //R8
//        System.out.println(c.calculateTotalOfChocolates(1, 0, 3)); //R9
//        System.out.println(c.calculateTotalOfChocolates(1, 2, -1)); //R10
    }
}
