package lbs.lbs.config.oauth.provider;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    public String getPhone() {
        String phone = (String) attributes.get("mobile");
        String delHyphenPhone = phone.replace("-","");
        return delHyphenPhone.replace("+82","0");
    }

    public String getNickName() {
        return (String) attributes.get("nickname");
    }

    public String getUserId() {
        return getProvider() + "_" + getProviderId();
    }

    public int getBirth() {
        String birthday = (String)attributes.get("birthday");
        String delHyphenBirthday = birthday.replaceAll("-", "");
        String birthYear = (String)attributes.get("birthyear");
        String birth = birthYear + "" + delHyphenBirthday;
        int integerBirth = Integer.parseInt(birth);
        return integerBirth;
    }

    public String getPassword() {
        return bCryptPasswordEncoder.encode("강인국");
    }

}