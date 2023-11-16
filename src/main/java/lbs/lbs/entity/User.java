package lbs.lbs.entity;

import jakarta.persistence.*;
import lbs.lbs.dto.UserRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", unique = true, nullable = false)
    private String userId;

    @Column(unique = true, nullable = false)
    private String userNickName;

    private String email;

    @Column(nullable = false)
    private String password;

    private int birth;
    private String phone;

    private String auth;

    private int pwErrChk;
    private boolean userYN;

    private String joinType;
    // 게터, 세터 및 다른 필수 메서드

    private LocalDateTime signUpTime;

    public User() {
    }
    public User(UserRequestDto userRequestDto){

        this.userId = userRequestDto.getUserId();
        this.userNickName = userRequestDto.getUserNickName();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.birth = userRequestDto.getBirth();
        this.phone = userRequestDto.getPhone();
        this.auth = "USER";
        this.pwErrChk = 0;
        this.userYN = true;
        this.joinType = userRequestDto.getJoinType();
        this.signUpTime = LocalDateTime.now();

    }

}
