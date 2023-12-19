package lbs.lbs.entity;

import jakarta.persistence.*;
import lbs.lbs.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID",nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userNickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private int birth;

    @Column(nullable = false)
    private String phone;

    private String auth;

    private int pwErrChk;

    private boolean userYN;

    private String joinType;
    // 게터, 세터 및 다른 필수 메서드

    private LocalDateTime signUpTime;


    public User(UserRequestDto userRequestDto){

        this.userId = userRequestDto.getUserId();
        this.userNickName = userRequestDto.getUserNickName();
        this.userName = userRequestDto.getUserName();
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
