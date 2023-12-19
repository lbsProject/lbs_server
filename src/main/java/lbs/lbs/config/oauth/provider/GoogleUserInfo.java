package lbs.lbs.config.oauth.provider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
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
        return "google";
    }

    public String getUserId() {
        return getProvider() + "_" + getProviderId();
    }

    public String getPassword() {
        return bCryptPasswordEncoder.encode("강인국");
    }

    public int getBirth() {
        return 0;
    }

    public String getPhone() {
        return "0";
    }

    public String getNickName() {
        return (String) attributes.get("name");
    }

}
