package com.nes.tireso.boundedContext.home.controller;

import com.nes.tireso.base.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final Rq rq;

    @GetMapping("/")
    public String showMain() {
        if (!rq.isLogout()) return "redirect:/usr/member/me"; // 로그아웃이 아닌 경우에 리다이렉트
        return "redirect:/usr/member/login"; // 로그아웃인 경우에 리다이렉트
    }


    //@GetMapping("/hello")
    //public ResponseEntity<String> hello() {
    //	return new ResponseEntity<String>("hello", HttpStatus.OK);
    //}
}
