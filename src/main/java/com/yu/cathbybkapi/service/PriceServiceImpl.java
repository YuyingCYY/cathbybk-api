package com.yu.cathbybkapi.service;

import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.repository.PriceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService{

  private final PriceRepository priceRepository;

  public PriceServiceImpl(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  @Override
  public BigDecimal calculatePriceChange(String productId, LocalDate startDate, LocalDate endDate) {
    Price startPrice = priceRepository.findByProductIdAndDate(productId, startDate)
      .orElseThrow(() -> new RuntimeException("Start price not found"));
    Price endPrice = priceRepository.findByProductIdAndDate(productId, endDate)
      .orElseThrow(() -> new RuntimeException("End price not found"));

    BigDecimal startPriceValue = BigDecimal.valueOf(startPrice.getPrice());
    BigDecimal endPriceValue = BigDecimal.valueOf(endPrice.getPrice());

    return endPriceValue.subtract(startPriceValue);
  }

  @Override
  public BigDecimal calculatePriceChangePercentage(String productId, LocalDate startDate, LocalDate endDate) {
    BigDecimal change = calculatePriceChange(productId, startDate, endDate);
    Price startPrice = priceRepository.findByProductIdAndDate(productId, startDate)
      .orElseThrow(() -> new RuntimeException("Start price not found"));

    BigDecimal startPriceValue = BigDecimal.valueOf(startPrice.getPrice());

    return change.divide(startPriceValue, 4, BigDecimal.ROUND_HALF_UP);
  }
}
