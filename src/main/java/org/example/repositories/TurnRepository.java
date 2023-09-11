package org.example.repositories;

import org.example.dto.TurnDto;
import org.example.entities.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Integer> {
    @Query(value = "select bet_Size, result from turns", nativeQuery = true)
    List<TurnDto>getAllBetsAndResults();
    @Query(value = "select (COUNT(CASE WHEN result = 'win' THEN 1 ELSE NULL END) * 1.0) / COUNT(*) AS win_coefficient from turns", nativeQuery = true)
    double getWinCoefficient();
}