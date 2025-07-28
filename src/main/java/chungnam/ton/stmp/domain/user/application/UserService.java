package chungnam.ton.stmp.domain.user.application;


import chungnam.ton.stmp.domain.market.dto.response.MarketResponse;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.dto.request.LoginRequest;
import chungnam.ton.stmp.domain.user.dto.request.SignupRequest;
import chungnam.ton.stmp.domain.user.dto.response.JwtResponse;

import java.util.List;

public interface UserService {
    void addUser(SignupRequest request);
    JwtResponse signupAndLogin(SignupRequest request);
    JwtResponse loginAndGetToken(LoginRequest request);
    User getUser(Long id);
    boolean verifyCurrentPassword(User user, String rawPassword);
    List<MarketResponse> getNotCompletedMarketByUserId(String rawToken);
    List<MarketResponse> getCompletedMarketByUserId(String rawToken);
}
