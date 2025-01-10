package com.yu.cathbybkapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.cathbybkapi.dto.FetchDataRequest;
import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.entity.Product;
import com.yu.cathbybkapi.repository.PriceRepository;
import com.yu.cathbybkapi.repository.ProductRepository;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final ProductRepository productRepository;
  private final PriceRepository priceRepository;

  public ApiService(ProductRepository productRepository, PriceRepository priceRepository) {
    this.productRepository = productRepository;
    this.priceRepository = priceRepository;
  }

  public void fetchData(FetchDataRequest request) throws Exception {
    String productId = request.getProductId();
    Optional<Product> productOpt = productRepository.findById(productId);
    if (productOpt.isPresent()) {
      throw new RuntimeException("product 已存在");
    }

    String url =
      "https://www.cathaybk.com.tw/cathaybk/service/newwealth/fund/chartservice.asmx/GetFundNavChart";
    Map<String, Object> requestBody = new HashMap<>();
    Map<String, Object> req = new HashMap<>();
    req.put("Keys", new String[]{productId});
    req.put("From", request.getFrom());
    req.put("To", request.getTo());
    requestBody.put("req", req);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
    String response = responseEntity.getBody();
    System.out.println("response: " + response);
    JsonNode root = objectMapper.readTree(response);

    JsonNode data = root.path("Data");
    for (JsonNode fund : data) {
      String id = fund.get("id").asText();
      String name = fund.get("name").asText();
      String shortName = fund.path("ShortName").asText(null);
      boolean isGrouped = fund.get("dataGrouping").asBoolean();

      // 保存 product data
      saveProduct(id, name, shortName, isGrouped);

      // 解析價格 data
      JsonNode prices = fund.get("data");
      for (JsonNode price : prices) {
        long timestamp = price.get(0).asLong();
        double value = price.get(1).asDouble();

        // timestamp 轉換成 Date
        Date date = new Date(timestamp);

        // 保存 price data
        savePrice(id, date, value);
      }
    }
  }

  private void saveProduct(String id, String name, String shortName, boolean isGrouped) {
    Product product = new Product();
    product.setProductId(id);
    product.setProductName(name);
    product.setProductShortName(shortName);
    product.setGrouped(isGrouped);
    productRepository.save(product);
  }

  private void savePrice(String id, Date date, double value) {
    Price price = new Price();
    price.setProductId(id);
    price.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    price.setPrice(value);
    priceRepository.save(price);
  }
}
