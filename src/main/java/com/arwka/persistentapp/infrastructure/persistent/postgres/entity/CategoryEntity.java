package com.arwka.persistentapp.infrastructure.persistent.postgres.entity;

import com.arwka.persistentapp.domain.model.Category;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
// ---
@Entity
@Table(name = "category")
public class CategoryEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(max = 10, message = "Name can be max 10 chars.")
  @Column(name = "name")
  private String name;

  @Min(value = 0, message = "code should be > 0")
  @Column(name = "code")
  private long code;

  public CategoryEntity(String name, long code) {
    this.name = name;
    this.code = code;
  }

  /**
   * Get CategoryEntity from domain Category
   *
   * @param category - domain category
   * @return CategoryEntity
   */
  public static CategoryEntity of(Category category) {
    return new CategoryEntity(category.getName(), category.getCode());
  }

  /**
   * Get domain Category from this
   *
   * @return Category
   */
  public Category toCategory() {
    return Category.of(name, code);
  }

}
