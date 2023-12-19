package lbs.lbs.config.oauth;

import lbs.lbs.config.auth.PrincipalDetails;
import lbs.lbs.config.oauth.provider.GoogleUserInfo;
import lbs.lbs.config.oauth.provider.NaverUserInfo;
import lbs.lbs.config.oauth.provider.OAuth2UserInfo;
import lbs.lbs.dto.UserRequestDto;
import lbs.lbs.entity.User;
import lbs.lbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken());
        //구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code를 리턴 받음(OAuth-client 라이브러리) ->code를 받으면 code를 이용해 access token 요청한다.
        //위에서 받은것이 userRequest 정보이다. -> loadUser 함수 호출 -> loadUser 함수가 구글로부터 회원 프로필 받아준다.

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo =  new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo =  new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }


        String provider = oAuth2UserInfo.getProvider(); //google
        String providerId = oAuth2UserInfo.getProviderId();
        String userNickName = oAuth2UserInfo.getNickName();
        String password = bCryptPasswordEncoder.encode("강인국");
        String email = oAuth2UserInfo.getEmail();
        String userName = oAuth2UserInfo.getName();
        int birth = oAuth2UserInfo.getBirth();
        String phone = oAuth2UserInfo.getPhone();

        User userEntity = userRepository.findByUserId(providerId);

        if(userEntity==null) {
            userEntity = User.builder()
                    .userId(providerId)
                    .userNickName(userNickName)
                    .userName(userName)
                    .email(email)
                    .password(password)
                    .birth(birth)
                    .phone(phone)
                    .auth("USER")
                    .joinType(provider)
                    .pwErrChk(0)
                    .signUpTime(LocalDateTime.now())
                    .userYN(true)
                    .build();

            userRepository.save(userEntity);
        }


        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
