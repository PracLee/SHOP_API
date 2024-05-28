package com.shop.shop.repositories;

import com.shop.shop.models.Order;
import com.shop.shop.models.OrderId;
import com.shop.shop.models.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
    List<Order> findAllByUserIdOrderByIdDesc(UserId userId);

    List<Order> findAllByOrderByIdDesc();

    Optional<Order> findByIdAndUserId(OrderId orderId, UserId userId);

}
