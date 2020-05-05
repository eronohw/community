package hw.learning.first.controller;

import hw.learning.first.dto.AccessTokenDTO;
import hw.learning.first.dto.GitHubUser;
import hw.learning.first.mapper.UserMapper;
import hw.learning.first.model.UserModel;
import hw.learning.first.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OauthController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(gitHubProvider.getAccessToken(accessTokenDTO));
        if (gitHubUser != null) {
            UserModel user = new UserModel();
            System.out.println(gitHubUser.getName());
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("gitSid", token));
            //request.getSession().setAttribute("user", gitHubUser);
            return "redirect:index";
        } else {
            return "redirect:index";
        }
    }
}
