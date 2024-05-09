package poc.uni.buddy.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import poc.uni.buddy.configuration.JwtUtils;
import poc.uni.buddy.repository.AuthRepository;
import poc.uni.buddy.repository.model.AuthUser;
import poc.uni.buddy.repository.model.LoginReqRes;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public LoginReqRes signUp(LoginReqRes registrationRequest){
        LoginReqRes resp = new LoginReqRes();
        try {
            AuthUser user = new AuthUser();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            AuthUser authUserResult = authRepository.save(user);
            resp.setAuthUser(authUserResult);
            resp.setMessage("User Saved Successfully");
            resp.setStatusCode(200);
        } catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public LoginReqRes signIn(LoginReqRes signInRequest){
        LoginReqRes response = new LoginReqRes();

        try {
            String email = signInRequest.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,signInRequest.getPassword()));
            var user = authRepository.findByEmail(email).orElseThrow();
            System.out.println("AUTH_USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("1Hr");
            response.setMessage("Successfully Signed In");
        } catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public LoginReqRes refreshToken(LoginReqRes refreshTokenRequest){
        LoginReqRes response = new LoginReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        AuthUser authUser = authRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), authUser)) {
            var jwt = jwtUtils.generateToken(authUser);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("1Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

}
