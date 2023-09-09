package org.example.repositories;

import org.example.entities.BlackjackUser;
import org.example.entities.Casino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackjackRepository extends JpaRepository<BlackjackUser, Integer> {
    @Query(value = "select deposit from blackjack_users", nativeQuery = true)
    List<Integer> getAllDeposits();
    @Query(value = "select max(deposit) from blackjack_users", nativeQuery = true)
    Integer getMaxDeposit();
}
