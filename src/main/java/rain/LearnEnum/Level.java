package rain.LearnEnum;

public enum Level {
    /**
     * 第一层
     */
    One(1),
    /**
     * 第二层
     */
    Two(2);

    private int value;

    Level(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static Boolean isValidateLevel(int level) {
        Level[] value = Level.values();
        boolean falg = false;
        for (Level pl : value) {
            if (pl.value == level) {
                falg = true;
            }
        }
        return falg;
    }

    /*使用*/
    public static void main(String[] args) {
        System.out.println("楼层：" + Level.Two);
    }
}
