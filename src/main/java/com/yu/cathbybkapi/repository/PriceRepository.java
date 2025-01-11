package com.yu.cathbybkapi.repository;

import com.yu.cathbybkapi.entity.Price;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
  Optional<Price> findByProductIdAndDate(String productId, LocalDate date);

  List<Price> findAllByOrderByProductIdAscDateAsc();
}
