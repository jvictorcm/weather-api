package br.com.victor.weatherapi.repositories;

import br.com.victor.weatherapi.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> findBySensorIdInAndTimestampBetween(List<String> sensorIds, LocalDateTime startDate, LocalDateTime endDate);
}
