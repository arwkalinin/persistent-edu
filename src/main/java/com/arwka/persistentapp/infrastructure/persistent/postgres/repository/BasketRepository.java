package com.arwka.persistentapp.infrastructure.persistent.postgres.repository;

import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.BasketEntity;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
  List<BasketEntity> findAllByProductName(@NotNull String productName);
}
