package chungnam.ton.stmp.domain.user.application;


import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.dto.request.LoginRequest;
import chungnam.ton.stmp.domain.user.dto.request.SignupRequest;
import chungnam.ton.stmp.domain.user.dto.response.JwtResponse;

public interface UserService {

    public void addUser(SignupRequest request);

    public JwtResponse signupAndLogin(SignupRequest request);

    public JwtResponse loginAndGetToken(LoginRequest request);

    public User getUser(Long id);

    public boolean verifyCurrentPassword(User user, String rawPassword);
}
