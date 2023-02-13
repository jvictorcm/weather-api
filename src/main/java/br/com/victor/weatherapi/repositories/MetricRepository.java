package br.com.victor.weatherapi.repositories;

import br.com.victor.weatherapi.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> findAllBySensorIdInAndCreatedAtBetween(List<String> sensorIds, LocalDateTime startDate, LocalDateTime endDate);

    @Query(
            value = "SELECT * FROM Metric m WHERE m.status in ?1",
            nativeQuery = true)
    List<Metric> findAllBySensorIdIn(List<String> sensorIds);
}
