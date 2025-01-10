package com.yu.cathbybkapi;

import com.yu.cathbybkapi.repository.ProductRepository;
import com.yu.cathbybkapi.service.ApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CathbybkApiApplication implements CommandLineRunner {

  private final ProductRepository productRepository;
  private final ApiService apiService;

  public CathbybkApiApplication(
    ProductRepository productRepository, ApiService apiService) {
    this.productRepository = productRepository;
    this.apiService = apiService;
  }

  public static void main(String[] args) {
    SpringApplication.run(CathbybkApiApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (productRepository.count() == 0) {
      apiService.fetchData();
    }
  }
}
