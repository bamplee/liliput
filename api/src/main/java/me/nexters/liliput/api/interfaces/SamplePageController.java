package me.nexters.liliput.api.interfaces;

import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SamplePageController {
    private final UserService userService;

    public SamplePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sample/login")
    public String login() {
        return "sample-login";
    }

    @GetMapping("/sample/login/success")
    public ModelAndView success(HttpServletRequest request, ModelAndView modelAndView) {
        AccountKitAccessResponse response = userService.validateAuthorizationCode(request.getParameter("code"));
        modelAndView.addObject("accessToken", response.getAccessToken());
        modelAndView.addObject("id", response.getId());
        modelAndView.addObject("tokenRefreshInterval", response.getTokenRefreshInterval());
        modelAndView.addObject("profile", userService.getProfile(response.getAccessToken()));
        modelAndView.setViewName("sample-success");
        return modelAndView;
    }
}
