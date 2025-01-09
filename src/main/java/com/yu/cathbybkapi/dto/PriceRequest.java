package com.yu.cathbybkapi.dto;

import java.time.LocalDate;

public class PriceRequest {

  private String productId;
  private LocalDate date;
  private Double price;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
