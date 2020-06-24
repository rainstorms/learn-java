package rain.designpattern.strategy;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class 实际应用场景及优化 {

    /**
     * Map 的key 是业务的key; Value 是 这个业务对应的处理方法
     * <p>
     * Function 的k 是参数  V 返回值
     * private Map<String, Function<参数, 返回值>> bizDealMethods = new HashMap<>();
     * <p>
     * <p>
     * 我的判断条件有多个啊，而且很复杂，而我有多个判断逻辑该怎么办呢？
     * 很好解决：写一个判断逻辑的方法，Map的key由方法计算出
     * 只要思考怎么构建key就可以了
     */
    private Map<String, Function<String, String>> bizDealMethods = new HashMap<>();

    /**
     * 初始化 业务逻辑分派Map 其中value 存放的是 lambda表达式
     */
    @PostConstruct
    public void initBizDealMethods() {
        bizDealMethods.put("key_订单1", order -> bizOne(order));
        bizDealMethods.put("key_订单1_订单2", order -> bizTwo(order));
        bizDealMethods.put("key_订单1_订单2_订单3", order -> bizThree(order));
    }

    // 可以将这些业务方法提出去单独一个service供调用
    private String bizOne(String order) {
        return "处理" + order + "业务1";
    }

    private String bizTwo(String order) {
        return "处理业务2";
    }

    private String bizThree(String order) {
        return "处理业务3";
    }


    public String dealBiz(String order, int level) {
        //写一段生成key的逻辑：
        String ley = buildBizKey(order, level);

        Function<String, String> result = bizDealMethods.get(ley);
        if (result != null) {
            //执行这段表达式获得String类型的结果
            return result.apply(order);
        }
        return "不在处理的逻辑中返回业务错误";
    }

    /**
     * 判断条件方法
     */
    private String buildBizKey(String order, int level) {
        StringBuilder key = new StringBuilder("key");
        for (int i = 1; i <= level; i++) {
            key.append("_").append(order).append(i);
        }
        return key.toString();
    }

}
