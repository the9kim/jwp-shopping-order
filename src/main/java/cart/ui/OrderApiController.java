package cart.ui;

import cart.application.OrderService;
import cart.domain.Member;
import cart.dto.request.OrderRequest;
import cart.dto.response.OrderResponse;
import cart.dto.response.OrdersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderApiController {

    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(Member member, @RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.add(member, orderRequest);
        return ResponseEntity.created(URI.create("/order/" + orderResponse.getOrderId())).body(orderResponse);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> showOrder(Member member, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findById(member, orderId));
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> showAllOrders(Member member) {
        return ResponseEntity.ok(orderService.findAll(member));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> removeOrder(Member member, @PathVariable Long orderId) {
        orderService.remove(member, orderId);
        return ResponseEntity.noContent().build();
    }
}
