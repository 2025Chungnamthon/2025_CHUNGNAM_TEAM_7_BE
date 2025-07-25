package chungnam.ton.stmp.domain.user.application;


import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.UserRole;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import chungnam.ton.stmp.domain.user.dto.request.LoginRequest;
import chungnam.ton.stmp.domain.user.dto.request.SignupRequest;
import chungnam.ton.stmp.domain.user.dto.response.JwtResponse;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public void addUser(SignupRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .name(null)
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }

    public JwtResponse signupAndLogin(SignupRequest request) {
        addUser(request);

        return loginAndGetToken(new LoginRequest(request.username(), request.email(), request.password()));
    }

    public JwtResponse loginAndGetToken(LoginRequest request) {
        System.out.println(request.username());
        System.out.println(request.password());
//        사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        jwt 생성
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다: " + request.username()));
        String jwt = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return new JwtResponse(jwt, refreshToken, "Bearer");
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_PARAMETER));
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    public boolean verifyCurrentPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
