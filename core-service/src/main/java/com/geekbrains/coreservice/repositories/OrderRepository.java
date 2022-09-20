package com.geekbrains.coreservice.repositories;

import com.geekbrains.coreservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.username like :username")
    List<Order> findByUsername(String username);
}
