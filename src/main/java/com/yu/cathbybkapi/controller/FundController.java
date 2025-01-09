package com.yu.cathbybkapi.controller;

import com.yu.cathbybkapi.dto.PriceRequest;
import com.yu.cathbybkapi.dto.PriceResponse;
import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.service.ApiService;
import com.yu.cathbybkapi.service.FundService;
import java.time.LocalDate;
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
public class FundController {

  private final FundService fundService;
  private final ApiService apiService;

  public FundController(FundService fundService, ApiService apiService) {
    this.fundService = fundService;
    this.apiService = apiService;
  }

  @GetMapping("/save")
  public ResponseEntity<?> save() {
    try {
      apiService.fetchData();
      return ResponseEntity.ok("Ok");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

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
}
