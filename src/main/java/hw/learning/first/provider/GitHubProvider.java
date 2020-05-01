package hw.learning.first.provider;

import com.alibaba.fastjson.JSON;
import hw.learning.first.dto.AccessTokenDTO;
import hw.learning.first.dto.GitHubUser;
import hw.learning.first.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GitHubProvider {
    @Autowired
    private HttpRequestUtil httpRequestUtil;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        String result = httpRequestUtil.postToHttpServer("https://github.com/login/oauth/access_token", accessTokenDTO);
        return result;
    }

    public GitHubUser getGitHubUser(String accessToken) {
        String result = httpRequestUtil.getToHttpServer("https://api.github.com/user?" + accessToken);
        System.out.println(result);
        GitHubUser gitHubUser = JSON.parseObject(result, GitHubUser.class);
        return gitHubUser;
    }
}
