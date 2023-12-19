package lbs.lbs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String userId;
    private String userName;
    private String userNickName;
    private String email;
    private String password;
    private String joinType;
    private int birth;
    private String phone;

    public void setBcPassword(String bcPassword) {
        this.password = bcPassword;
    }


}
