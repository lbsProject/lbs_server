package lbs.lbs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String userId;
    private String userNickName;

    private String email;

    private String password;

    private int birth;
    private String phone;

    public void bcPassword(String bcPassword) {
        this.password = bcPassword;
    }


}
