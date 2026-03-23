package chocolate;

public class Chocolate {
    public int calculateTotalOfChocolates(double n, double c, int m){
        if (n < 0) throw new IllegalArgumentException("n cannot be negative");
        if (c < 1) throw new IllegalArgumentException("c cannot be less than one");
        if (m < 2) throw new IllegalArgumentException("m cannot be less than two");

        if (c > n) return 0;

        int total = (int) (n / c);
        int packaging = total;

        while (packaging >= m) {
            int bonus = packaging / m;
            packaging = (packaging + bonus) - (bonus * m);
            total += bonus;
        }
        return total;
    }
}
