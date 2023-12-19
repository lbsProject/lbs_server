package lbs.lbs.repository;

import lbs.lbs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 쿼리 메서드 정의 가능

    User findByUserId(String userId);

    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByUserNickName(String userNickName);


}
