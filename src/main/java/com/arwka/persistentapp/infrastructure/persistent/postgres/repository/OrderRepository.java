package com.arwka.persistentapp.infrastructure.persistent.postgres.repository;

import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

  @Query(
      value = "SELECT avg(payment) FROM \"order\"",
      nativeQuery = true
  )
  long getAveragePayment();

}
