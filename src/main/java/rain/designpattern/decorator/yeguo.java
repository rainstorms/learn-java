package rain.designpattern.decorator;

public class yeguo implements jisuanqi {
    jisuanqi jisuanqi;

    public yeguo(jisuanqi tiaoliao) {
        this.jisuanqi = tiaoliao;
    }

    @Override public double cost() {
        return 3.5 + jisuanqi.cost();
    }

    @Override public String getDesc() {
        return "椰果" + jisuanqi.getDesc();
    }
}
