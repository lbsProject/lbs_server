package lbs.lbs.config.auth;

import lbs.lbs.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
//로그인이 진행이 완료가 되면 session을 만들어 준다. (Security ContextHolder라는 키값에 세션정보를 저장시킨다. )
// Security ContextHolder에 들어갈 수 있는 객체는 Authentication 타입의 객체만 들어갈 수 있다.
//Authentication 안에는 user 정보가 있어야 한다.
//user 오브젝트 타입은 UserDetails 타입의 객체 여야 한다.
//Security Session > Authentication > User Details
// PrincipalDetails 클래스에 UserDetails를 implements하면 Authentication에 PrincipalDetails를 넣을 수 있게 된다.
// PrincipalDetails이 UserDetails가 되기 때문


public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //getAuthorities 에는 user의 auth가 들어가야 하는데
        //리턴 타입이 Collection<? extends GrantedAuthority>이기 때문에
        //Collection<? extends GrantedAuthority> 객체를 생성하고 거기에 user.getAuth()를 넣어주었다.

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getAuth();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //일년 동안 로그인을 안하면 휴면 계정으로 하기로 함
        // 현재시간 - 마지막 로그인 시간
        //해서 1년이 지났다면 false 이런식으로 사용할 수 있다.
        return true;
    }
}
