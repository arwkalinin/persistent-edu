package com.arwka.persistentapp.application.service;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.infrastructure.controller.dto.OrderRequest;
import com.arwka.persistentapp.infrastructure.controller.dto.ProductRequest;
import com.arwka.persistentapp.infrastructure.controller.dto.mapper.ProductMapper;
import com.arwka.persistentapp.infrastructure.csv.OrdersCsvReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrdersService {

  private final Repository repository;
  private final CategoryService categoryService;
  private final OrdersCsvReader ordersCsvReader;

  // -------

  /**
   * Create and save new Order in DB.
   *
   * @param order - order-to-save
   * @return Order (saved)
   */
  @Transactional
  public Order create(Order order) {
    repository.createNewOrder(order);
    repository.createBasketsForOrder(order);
    repository.decreaseProducts(order);
    return order;
  }

  /**
   * Get average order payment.
   *
   * @return long average payment
   */
  public long getAverageOrderPayment() {
    return repository.getAverageOrderPayment();
  }

  /**
   * Load to DB orders from .csv file on server.
   *
   * @param path - path to .csv
   */
  public void loadOrdersInDbFromCsv(String path) {
    List<Order> orderList = ordersCsvReader.readCsvAndGetListOfOrders(path);
    orderList.forEach(this::create);
  }

  /**
   * Get product list from OrderRequest.
   *
   * @param orderRequest - order (DTO) with products
   * @return Map of products with amount
   */
  public Map<Product, Long> getMapOfProductsFromRequest(OrderRequest orderRequest) {
    return orderRequest
        .getProductList().stream()
        .collect(Collectors.toMap(productRequest -> ProductMapper.mapToProduct(
                    productRequest, categoryService.findByCode(productRequest.getCategoryCode())
                ),
                ProductRequest::getQuantity)
        );
  }

}


