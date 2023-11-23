package lbs.lbs.service;

import lbs.lbs.dto.UserRequestDto;
import lbs.lbs.entity.User;
import lbs.lbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> createUser(UserRequestDto userRequestDto) {

        String phone = userRequestDto.getPhone();

        String bcPassword = bCryptPasswordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setBcPassword(bcPassword);

        if (userRequestDto.getPhone().length() < 13) {
            phone = phone.replaceFirst("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");
        }
        
        if (userRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("이미 등록된 핸드폰입니다.");
        }
        
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        if (userRepository.existsByUserId(userRequestDto.getUserId())) {
            throw new IllegalArgumentException("이미 등록된 아이디입니다.");
        }

        if (userRepository.existsByUserNickName(userRequestDto.getUserNickName())) {
            throw new IllegalArgumentException("이미 등록된 닉네임입니다.");
        }

        User user = User.builder()
                .userId(userRequestDto.getUserId())
                .userNickName(userRequestDto.getUserNickName())
                .userName(userRequestDto.getUserName())
                .email(userRequestDto.getEmail())
                .password(bcPassword)
                .birth(userRequestDto.getBirth())
                .phone(phone)
                .auth("USER")
                .joinType("LBS")
                .pwErrChk(0)
                .signUpTime(LocalDateTime.now())
                .userYN(true)
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
