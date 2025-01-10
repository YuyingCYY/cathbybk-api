package com.yu.cathbybkapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PriceService {
  /**
   * 計算漲跌[後收-前收]
   * @param productId 商品ID
   * @param startDate 開始時間
   * @param endDate   結束時間
   * @return  double
   */
  BigDecimal calculatePriceChange(String productId, LocalDate startDate, LocalDate endDate);

  /**
   * 計算漲跌幅[(後收-前收)/前收]
   * @param productId 商品ID
   * @param startDate 開始時間
   * @param endDate   結束時間
   * @return  double
   */
  BigDecimal calculatePriceChangePercentage(String productId, LocalDate startDate, LocalDate endDate);
}
