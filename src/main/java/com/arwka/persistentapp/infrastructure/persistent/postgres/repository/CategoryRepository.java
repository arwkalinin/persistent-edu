package com.arwka.persistentapp.infrastructure.persistent.postgres.repository;

import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  @Query(
      value = """
          SELECT c.id, c.name, c.code
          FROM product p
            JOIN basket b on p.name = b.product_name
            JOIN category c on p.category_code = c.code
          GROUP BY c.id, c.name, c.code
          ORDER BY COUNT(b.product_name) DESC LIMIT 1""",
      nativeQuery = true
  )
  CategoryEntity findMostPopularCategory();

  CategoryEntity findByCode(long code);

}
