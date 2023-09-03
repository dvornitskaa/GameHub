package org.example.repositories;

import org.example.entities.BlackjackUser;
import org.example.entities.Casino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackjackRepository extends JpaRepository<BlackjackUser, Integer> {

}
