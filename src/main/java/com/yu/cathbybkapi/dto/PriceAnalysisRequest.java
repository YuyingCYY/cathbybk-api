package com.yu.cathbybkapi.dto;

import java.time.LocalDate;

public class PriceAnalysisRequest {

  private String productId;
  private LocalDate startDate;
  private LocalDate endDate;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
