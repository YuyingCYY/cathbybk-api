package com.yu.cathbybkapi.service;

import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.repository.PriceRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService{

  private final PriceRepository priceRepository;

  public PriceServiceImpl(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  @Override
  public double calculatePriceChange(String productId, LocalDate startDate, LocalDate endDate) {
    Price startPrice = priceRepository.findByProductIdAndDate(productId, startDate)
      .orElseThrow(() -> new RuntimeException("Start price not found"));
    Price endPrice = priceRepository.findByProductIdAndDate(productId, endDate)
      .orElseThrow(() -> new RuntimeException("End price not found"));

    return endPrice.getPrice() - startPrice.getPrice();
  }

  @Override
  public double calculatePriceChangePercentage(String productId, LocalDate startDate, LocalDate endDate) {
    double change = calculatePriceChange(productId, startDate, endDate);
    Price startPrice = priceRepository.findByProductIdAndDate(productId, startDate)
      .orElseThrow(() -> new RuntimeException("Start price not found"));

    return (change / startPrice.getPrice()) * 100;
  }
}
