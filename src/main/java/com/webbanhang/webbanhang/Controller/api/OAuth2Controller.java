package com.webbanhang.webbanhang.Controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Service.Impl.AuthenticationServiceImpl;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {
    @Value("${GOOGLE_CLIENT_ID}")
    private String id;
    @Value("${GOOGLE_CLIENT_SECRET}")
    private String secret;
    private final IUserService userService;
    private final CheckLogin checkLogin;
    private final AuthenticationServiceImpl authenticationService;
    @GetMapping("/login/oauth2/authorization/google")
    public String home(@RequestParam Map<String,String> map, HttpServletRequest httpServletRequest) {
        try{
            log.info(map.get("code"));
            String code = map.get("code");
            String accessToken = getAccessToken(code);
            UserModel userInfo = getUserInfo(accessToken);
            UserModel userModel= null;
            AuthenticationResponse response = null;
            if (userService.existsByEmail(userInfo.getEmail())) {
                userModel = userService.findByEmail(userInfo.getEmail());
                AuthenticationRequest request = AuthenticationRequest.builder()
                        .email(userModel.getEmail())
                        .password(userModel.getPassword())
                        .build();
                response = authenticationService.login(request,"oauth2");
            }
            else{
                RegisterRequest registerRequest = RegisterRequest.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getUsername())
                        .password("loda")
                        .phone(null)
                        .build();
                response = authenticationService.register(registerRequest);
            }
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("token", response.getToken());
            session.setAttribute("userdata", response.getUserDto());
            checkLogin.checkLogin(session,response);
            return "redirect:/";
        }
        catch (Exception e){
            return "redirect:/login";
        }

    }
    public String getAccessToken(String authorizationCode) {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("client_id", id);
        body.add("client_secret", secret);
        body.add("redirect_uri", "http://localhost:8080/login/oauth2/authorization/google");
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);

        // Use ObjectMapper to parse the JSON response and extract the access token
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText(); // Extract the access token
        } catch (Exception e) {
            log.error("Error parsing access token response: ", e);
            return null; // or handle this as needed
        }
    }
    public UserModel getUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return UserModel.builder()
                    .userName(jsonNode.get("name").asText())
                    .email(jsonNode.get("email").asText())
                    .build();
        } catch (Exception e) {
            log.error("Error parsing user info response: ", e);
            return null;
        }
    }
}
