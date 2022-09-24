package com.arwka.persistentapp.infrastructure.controller;

import com.arwka.persistentapp.application.service.OrdersService;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.infrastructure.controller.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrdersService ordersService;

  /**
   * Path to .csv file for task.
   */
  @Value("${csv-path}")
  private String csvPath;

  /**
   * Endpoint for loading records from .csv (on server) in DB.
   *
   * @return ResponseEntity with status CREATED
   */
  @PostMapping(value = "/load")
  public ResponseEntity<?> postOrdersFromCsv() {
    ordersService.loadOrdersInDbFromCsv(csvPath);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Endpoint for loading records in DB from RequestBody Order.
   *
   * @param orderRequest - order-to-save
   * @return ResponseEntity with created Order.
   */
  @PostMapping(value = "/order", consumes={"application/json"})
  public ResponseEntity<Order> postOrder(@RequestBody OrderRequest orderRequest) {

    Order createdOrder = orderRequest.toOrderWithProductMap(
        ordersService.getMapOfProductsFromRequest(orderRequest));

    ordersService.create(createdOrder);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

}
