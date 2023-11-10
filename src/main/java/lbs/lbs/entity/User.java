package lbs.lbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Entity
@Getter @Setter
public class User {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String userId;
    @Nullable
    private String username;
    private String email;
    @Nullable
    private String password;
    private int birth;
    private String phone;
    private Authority auth;
    private int pwErrChk;
    private boolean userYN;
    private JoinType joinType;
    // 게터, 세터 및 다른 필수 메서드

}
