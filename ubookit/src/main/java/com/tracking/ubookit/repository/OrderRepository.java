package com.tracking.ubookit.repository;

import com.tracking.ubookit.model.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for accessing order data.
 * Provides CRUD operations on the orders_tracking table.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderDao, String> {
}
