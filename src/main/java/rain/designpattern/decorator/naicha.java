package rain.designpattern.decorator;

public class naicha implements jisuanqi {
    @Override public double cost() {
        return 1.2;
    }

    @Override public String getDesc() {
        return "奶茶";
    }

    public static void main(String[] args) {
        jisuanqi naicha = new naicha();

        naicha = new zhenzhu(naicha);
        naicha = new yeguo(naicha);

        System.out.println(naicha.cost());
        System.out.println(naicha.getDesc());
    }
}
