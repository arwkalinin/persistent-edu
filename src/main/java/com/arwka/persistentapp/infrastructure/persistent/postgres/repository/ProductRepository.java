package com.arwka.persistentapp.infrastructure.persistent.postgres.repository;

import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  List<ProductEntity> findByDescriptionContaining(String word);

  ProductEntity findByName(String name);

  @Modifying
  @Query(
      value = "UPDATE product SET quantity = :newQuantity WHERE name = :name",
      nativeQuery = true
  )
  void updateQuantity(
      @Param("name") String name,
      @Param("newQuantity") long newQuantity);

  @Query(
      value = """
          SELECT p.id, p.name, p.description, p.category_code, p.quantity
          FROM product p
            JOIN basket b on p.name = b.product_name
          GROUP BY p.id, p.name, p.description, p.category_code, p.quantity
          ORDER BY COUNT(p) DESC LIMIT 5""",
      nativeQuery = true
  )
  List<ProductEntity> findFiveMostOrderedProducts();

  ProductEntity findProductEntityByName(String name);

}
