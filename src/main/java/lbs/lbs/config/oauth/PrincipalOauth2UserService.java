package lbs.lbs.config.oauth;

import lbs.lbs.config.auth.PrincipalDetails;
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

        String provider = userRequest.getClientRegistration().getClientName(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String userId = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("강인국");
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(userId);
        userRequestDto.setUserNickName(username);
        userRequestDto.setBirth(0);
        userRequestDto.setPhone("");
        userRequestDto.setEmail(email);
        userRequestDto.setBcPassword(password);
        userRequestDto.setJoinType(provider);

        User user = new User(userRequestDto);
        userRepository.save(user);

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
