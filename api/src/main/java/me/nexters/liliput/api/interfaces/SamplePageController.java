package me.nexters.liliput.api.interfaces;

import me.nexters.liliput.api.domain.service.AccountKitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SamplePageController {
    private final AccountKitService accountKitService;

    public SamplePageController(AccountKitService accountKitService) {
        this.accountKitService = accountKitService;
    }

    @GetMapping("/sample/page")
    public String login() {
        return "sample-login";
    }
}
