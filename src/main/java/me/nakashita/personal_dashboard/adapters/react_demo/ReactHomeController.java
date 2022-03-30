package me.nakashita.personal_dashboard.adapters.react_demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReactHomeController {

    @GetMapping("/react")
    public String renderMainPage() {
        return new InitialHtml("React Demo").serialize();
    }
}

