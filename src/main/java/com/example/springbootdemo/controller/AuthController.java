package com.example.springbootdemo.controller;

import com.example.springbootdemo.dto.AccessTokenDTO;
import com.example.springbootdemo.dto.GithubUser;
import com.example.springbootdemo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthController {
    @Autowired
    public GithubProvider githubProvider;
    @Value("${github.client.id}") //用來讀取配置裏的這個key的值
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = null;
        try {
            user = githubProvider.getUser(accessToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(user.getName());
        return "index";
    }
}
