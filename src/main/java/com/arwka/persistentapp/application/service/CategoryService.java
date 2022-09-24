package com.arwka.persistentapp.application.service;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final Repository repository;

  // -------

  /**
   * Save category in DB.
   *
   * @param category - category-to-save
   * @return Category (saved)
   */
  public Category create(Category category) {
    return repository.save(category);
  }

  /**
   * Get most popular category in DB.
   *
   * @return Category (most popular)
   */
  public Category getMostPopularCategory() {
    return repository.getMostPopularCategory();
  }

  /**
   * Find category by code.
   *
   * @param code - category code
   * @return Category with the given code.
   */
  public Category findByCode(long code) {
    return repository.findCategoryByCode(code);
  }

}
