package lbs.lbs.repository;

import lbs.lbs.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    boolean existsById(String id);
    Optional<Summoner> findById(String id);
}
