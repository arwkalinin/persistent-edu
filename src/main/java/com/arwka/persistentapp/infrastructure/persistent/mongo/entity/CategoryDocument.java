package com.arwka.persistentapp.infrastructure.persistent.mongo.entity;

import com.arwka.persistentapp.domain.model.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

// Lombok
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
// ---
@Document
public class CategoryDocument {

  private String name;

  private long code;

  public CategoryDocument(String name, long code) {
    this.name = name;
    this.code = code;
  }

  public static CategoryDocument of(Category category) {
    return new CategoryDocument(category.getName(), category.getCode());
  }

}
