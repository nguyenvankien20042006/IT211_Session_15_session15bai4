package com.example.session15bai4.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        return "Dang nhap thanh cong: " + authentication.getName();
    }

    @GetMapping("/books")
    public String books() {
        return "USER, LIBRARIAN hoac ADMIN co the truy cap danh sach sach.";
    }

    @GetMapping("/librarian")
    public String librarian() {
        return "LIBRARIAN hoac ADMIN co the quan ly nghiep vu thu vien.";
    }

    @GetMapping("/admin")
    public String admin() {
        return "ADMIN co the quan ly he thong.";
    }
}
