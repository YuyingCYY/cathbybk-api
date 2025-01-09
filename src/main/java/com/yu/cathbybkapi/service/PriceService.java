package com.yu.cathbybkapi.service;

import java.time.LocalDate;

public interface PriceService {
  double calculatePriceChange(String productId, LocalDate startDate, LocalDate endDate);
  double calculatePriceChangePercentage(String productId, LocalDate startDate, LocalDate endDate);
}
