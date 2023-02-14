package br.com.victor.weatherapi.repositories;

import br.com.victor.weatherapi.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {

    @Query(value = "SELECT * FROM metric m WHERE ((COALESCE(?1) IS NULL OR m.sensor_id in (?1)) AND (cast(?2 as date) IS NULL OR m.created_at >= ?2) AND (cast(?3 as date) IS NULL OR m.created_at <= ?3))", nativeQuery = true)
    List<Metric> findByParameters(List<String> sensorIds, LocalDateTime startDate, LocalDateTime endDate);
}
