package rain.mergeRequest;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hello")
public class controller {

    //并发安全的阻塞队列，积攒请求。（每隔N毫秒批量处理一次）
    LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue();

    @RequestMapping("/queryResponseById/{id}")
    public Response queryResponseById(@PathVariable String id) throws Exception {
        //异步编程，创建当前线程的任务，由其他线程异步运算，获取异步处理的结果。
        CompletableFuture<Response> future = new CompletableFuture<>();
        Request request = new Request(id, future);

        //请求参数放入队列中。定时任务去消化请求。
        queue.add(request);

        //阻塞等待获取结果。
        return future.get();
    }

    @PostConstruct
    public void init() {
        // 创建线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        // 立即执行任务，并间隔10秒重复执行。
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 捕获异常
            try {
                //1.从阻塞队列中取出queue的请求，生成一次批量查询。
                int size = queue.size();
                if (size == 0) return;

                List<Request> requests = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    // 移出队列，并返回。
                    Request poll = queue.poll();
                    requests.add(poll);
                }

                //2.组装一个批量查询请求参数。
                List<String> ids = requests.stream().map(Request::getId).collect(Collectors.toList());
                System.out.println("本次合并请求数量：" + ids.size());

                //3.请求service 得到结果list。
                List<Response> addresses = queryAllResponse(ids);
                Map<String, Response> responseMap = addresses.stream().collect(Collectors.toMap(Response::getId, o -> o));

                //4.将结果响应给每一个单独的用户请求。
                for (Request request : requests) {
                    //根据请求中携带的能表示唯一参数，去批量查询的结果中找响应。
                    Response address = responseMap.get(request.getId());

                    //将结果返回到对应的请求线程。2个线程通信，异步编程赋值。
                    //complete(),源码注释翻译：如果尚未完成，则将由方法和相关方法返回的值设置为给定值
                    request.getFuture().complete(address);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    // 模拟 service 批量查询方法
    private List<Response> queryAllResponse(List<String> ids) {
        Response address1 = new Response();
        address1.setId("1");
        address1.setName("11");

        Response address2 = new Response();
        address2.setId("2");
        address2.setName("22");
        return Lists.newArrayList(address1, address2);
    }
}
