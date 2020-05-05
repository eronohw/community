package hw.learning.first.controller;

import hw.learning.first.mapper.UserMapper;
import hw.learning.first.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public String greeting(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if ("gitSid".equals(cookie.getName())) {
                token = cookie.getValue();
                UserModel userModel = userMapper.findByToken(token);
                if(userModel!=null){
                    request.getSession().setAttribute("user", userModel);
                }
                break;
            }
        }

        return "test";
    }
}