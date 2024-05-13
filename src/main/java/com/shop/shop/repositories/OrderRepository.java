package com.shop.shop.repositories;

import com.shop.shop.models.Order;
import com.shop.shop.models.OrderId;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
}
