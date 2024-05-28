package com.shop.shop.controllers.admin;

import com.shop.shop.annotations.AdminRequired;
import com.shop.shop.application.GetAdminOrderDetailService;
import com.shop.shop.application.GetOrderListService;
import com.shop.shop.application.UpdateOrderService;
import com.shop.shop.dtos.AdminOrderDetailDto;
import com.shop.shop.dtos.AdminOrderListDto;
import com.shop.shop.dtos.AdminUpdateOrderDto;
import com.shop.shop.models.OrderId;
import com.shop.shop.models.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@AdminRequired
@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final GetOrderListService getOrderListService;
    private final GetAdminOrderDetailService getAdminOrderDetailService;
    private final UpdateOrderService updateOrderService;

    public AdminOrderController(GetOrderListService getOrderListService,
                                GetAdminOrderDetailService getAdminOrderDetailService,
                                UpdateOrderService updateOrderService) {
        this.getOrderListService = getOrderListService;
        this.getAdminOrderDetailService = getAdminOrderDetailService;
        this.updateOrderService = updateOrderService;
    }

    @GetMapping("/{id}")
    public AdminOrderDetailDto detail(@PathVariable String id) {
        OrderId orderId = new OrderId(id);
        return getAdminOrderDetailService.getOrderDetail(orderId);
    }

    @GetMapping
    public AdminOrderListDto list() {
        return getOrderListService.getAdminOrderList();
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateOrderDto orderDto) {
        OrderId orderId = new OrderId(id);
        OrderStatus status = OrderStatus.of(orderDto.status());
        updateOrderService.updateOrderStatus(orderId, status);
        return "Updated";
    }
}