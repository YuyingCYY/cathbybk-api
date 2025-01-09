package com.yu.cathbybkapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {

  @Id
  private String productId;

  private String productName;

  private String productShortName;

  private boolean isGrouped;

  public Product() {
  }

  public Product(String productId, String productName, String productShortName, boolean isGrouped) {
    this.productId = productId;
    this.productName = productName;
    this.productShortName = productShortName;
    this.isGrouped = isGrouped;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductShortName() {
    return productShortName;
  }

  public void setProductShortName(String productShortName) {
    this.productShortName = productShortName;
  }

  public boolean isGrouped() {
    return isGrouped;
  }

  public void setGrouped(boolean grouped) {
    isGrouped = grouped;
  }
}
