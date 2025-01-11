package com.yu.cathbybkapi.controller;

import com.yu.cathbybkapi.dto.FetchDataRequest;
import com.yu.cathbybkapi.dto.PriceAnalysisRequest;
import com.yu.cathbybkapi.dto.PriceRequest;
import com.yu.cathbybkapi.dto.PriceResponse;
import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.service.ApiService;
import com.yu.cathbybkapi.service.FundService;
import com.yu.cathbybkapi.service.PriceService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fund")
public class FundRestController {

  private final FundService fundService;
  private final ApiService apiService;
  private final PriceService priceService;

  public FundRestController(FundService fundService, ApiService apiService, PriceService priceService) {
    this.fundService = fundService;
    this.apiService = apiService;
    this.priceService = priceService;
  }

  /**
   * 呼叫國泰 API 獲得內容並存資料庫
   */
  @GetMapping("/save")
  public ResponseEntity<?> save(@RequestBody FetchDataRequest request) {
    try {
      apiService.fetchData(request);
      return ResponseEntity.ok("Ok");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
    }
  }

  /**
   * 查詢某日價格
   * @param productId 商品 ID
   * @param date      日期
   */
  @GetMapping("/price")
  public ResponseEntity<?> getPrice(@RequestParam String productId,
                                    @RequestParam LocalDate date) {
    try {
      return ResponseEntity.ok(fundService.getPrice(productId, date));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new PriceResponse(e.getMessage(), null));
    }
  }

  /**
   * 新增價格至DB
   * @param request PriceRequest
   */
  @PostMapping("/price")
  public ResponseEntity<?> addPrice(@RequestBody PriceRequest request) {
    try {
      Price price = new Price();
      price.setProductId(request.getProductId());
      price.setDate(request.getDate());
      price.setPrice(request.getPrice());
      if (fundService.addPrice(price)) {
        // 成功新增價格
        return ResponseEntity.ok(new PriceResponse("Price saved successfully", price));
      } else {
        // 未成功新增價格
        return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new PriceResponse("Price already exists for the given product and date", null));
      }
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new PriceResponse(e.getMessage(), null));
    }
  }

  /**
   * 修改某日價格
   * @param productId 商品ID
   * @param date      日期
   * @param newPrice  新價格
   */
  @PutMapping("/price/{productId}/{date}")
  public ResponseEntity<?> updatePrice(
    @PathVariable String productId,
    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    @RequestBody double newPrice) {
    try {
      Price updatedPrice = fundService.updatePrice(productId, date, newPrice);
      return ResponseEntity.ok(new PriceResponse("Price updated successfully", updatedPrice));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new PriceResponse(e.getMessage(), null));
    }
  }

  /**
   * 刪除某日價格
   * @param productId 商品ID
   * @param date      日期
   */
  @DeleteMapping("/price/{productId}/{date}")
  public ResponseEntity<?> deletePrice(@PathVariable String productId,
                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    try {
      fundService.deletePrice(productId, date);
      return ResponseEntity.ok(new PriceResponse("Price deleted successfully", null));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new PriceResponse(e.getMessage(), null));
    }
  }

  @PostMapping("/price/analysis")
  public ResponseEntity<?> analyzePrice(
    @RequestBody PriceAnalysisRequest request) {
    try {
      BigDecimal priceChange = priceService.calculatePriceChange(
        request.getProductId(), request.getStartDate(), request.getEndDate());
      BigDecimal priceChangePercentage =
        priceService.calculatePriceChangePercentage(request.getProductId(), request.getStartDate(),
          request.getEndDate());

      Map<String, BigDecimal> result = new HashMap<>();
      result.put("priceChange", priceChange);
      result.put("priceChangePercentage", priceChangePercentage);

      return ResponseEntity.ok(result);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new PriceResponse(e.getMessage(), null));
    }
  }
}
