package lbs.lbs.dao;

import lbs.lbs.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDAO_jpa extends JpaRepository<Board, Integer> {

}
