package rain.LearnEnum;

public class EnumUse {

    public static void inSwitch(Color color) {
        switch (color) {
            case RED:
                System.out.println("红");
                break;
            case YELLOW:
                System.out.println("黄");
                break;
            default:
                System.out.println("不认识");
                break;
        }

    }


    public static void main(String[] args) {
        inSwitch(Color.YELLOW);
    }
}
