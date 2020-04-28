package rain.designpattern.decorator;

public class zhenzhu implements jisuanqi {
    jisuanqi jisuanqi;

    public zhenzhu(jisuanqi tiaoliao) {
        this.jisuanqi = tiaoliao;
    }

    @Override public double cost() {
        return 2.5 + jisuanqi.cost();
    }

    @Override public String getDesc() {
        return "珍珠" + jisuanqi.getDesc();
    }
}
