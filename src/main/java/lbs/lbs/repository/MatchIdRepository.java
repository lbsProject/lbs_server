package lbs.lbs.repository;

import lbs.lbs.entity.MatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchIdRepository extends JpaRepository<MatchId, Long> {
}
