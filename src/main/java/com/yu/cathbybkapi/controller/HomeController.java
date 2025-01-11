package com.yu.cathbybkapi.controller;

import com.yu.cathbybkapi.entity.Price;
import com.yu.cathbybkapi.repository.PriceRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  private final PriceRepository priceRepository;

  public HomeController(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  @GetMapping("/index")
  public String home(Model model) {
    List<Price> prices = priceRepository.findAllByOrderByProductIdAscDateAsc();
    model.addAttribute("prices", prices);

    return "index";
  }
}
