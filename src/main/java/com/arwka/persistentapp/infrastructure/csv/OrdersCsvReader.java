package com.arwka.persistentapp.infrastructure.csv;

import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.StateEnum;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class OrdersCsvReader {

  /**
   * Read .csv file and get list of orders.
   *
   * @param csvPath - path to .csv on server
   * @return List of orders
   */
  public List<Order> readCsvAndGetListOfOrders(String csvPath){

    List<Order> orderList = new ArrayList<>();

    try {
      Reader reader = new FileReader(csvPath);
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
      System.out.println("PARSED ORDERS: -------------");

      for (CSVRecord record : records) {
        if (record.stream().toList().size() > 2) {

          Order currentOrder = Order.of(
              Long.parseLong(record.get(0)),
              record.get(1),
              ZonedDateTime.parse(record.get(2)),
              StateEnum.valueOf(record.get(3))
          );

          System.out.println(currentOrder);
          orderList.add(currentOrder);

        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return orderList;
  }

}
