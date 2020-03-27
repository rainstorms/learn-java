package rain.mergeRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CompletableFuture;

@Data @NoArgsConstructor @AllArgsConstructor
public class Request {
    String id;
    CompletableFuture<Response> future;
}
