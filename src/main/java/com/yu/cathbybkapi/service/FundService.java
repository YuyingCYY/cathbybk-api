package com.yu.cathbybkapi.service;

import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.repository.PriceRepository;
import com.yu.cathbybkapi.repository.ProductRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FundService {

  private final PriceRepository priceRepository;
  private final ProductRepository productRepository;

  public FundService(PriceRepository priceRepository, ProductRepository productRepository) {
    this.priceRepository = priceRepository;
    this.productRepository = productRepository;
  }

  public Price getPrice(String productId, LocalDate date) {
    return priceRepository.findByProductIdAndDate(productId, date)
      .orElseThrow(() -> new RuntimeException("Price not found"));
  }

  public boolean addPrice(Price price) {
    if (priceRepository.findByProductIdAndDate(price.getProductId(), price.getDate()).isPresent()) {
      // 已經存在同產品 ID 和日期的價格記錄,不新增
      return false;
    }
    productRepository.findById(price.getProductId())
      .orElseThrow(() -> new RuntimeException("Product is not exist"));
    priceRepository.save(price);
    return true;
  }

  public Price updatePrice(String productId, LocalDate date, Double newPrice) {
    Price price = getPrice(productId, date);
    price.setPrice(newPrice);
    return priceRepository.save(price);
  }

  public void deletePrice(String productId, LocalDate date) {
    Price price = getPrice(productId, date);
    priceRepository.deleteById(price.getId());
  }
}