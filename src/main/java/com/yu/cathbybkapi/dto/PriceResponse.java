package com.yu.cathbybkapi.dto;

import com.yu.cathbybkapi.entity.Price;

public class PriceResponse {
  private String message;
  private Price price;

  public PriceResponse(String message, Price price) {
    this.message = message;
    this.price = price;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = price;
  }
}
