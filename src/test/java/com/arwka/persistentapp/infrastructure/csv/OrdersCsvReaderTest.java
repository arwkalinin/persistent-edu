package com.arwka.persistentapp.infrastructure.csv;

import com.arwka.persistentapp.domain.model.Order;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrdersCsvReaderTest {

  @Autowired
  OrdersCsvReader ordersCsvReader;

  @Test
  @DisplayName("readCsvAndGetListOfOrdersTest should return excepted list")
  void readCsvAndGetListOfOrdersTest() {
    List<Order> orders = ordersCsvReader.readCsvAndGetListOfOrders("csv/testOrders.csv");

    Assertions.assertEquals(14, orders.get(0).getUidLength());
    Assertions.assertEquals("1234567890asdf", orders.get(1).getUid());
    Assertions.assertEquals(250L, orders.get(3).getPayment());
  }

}
