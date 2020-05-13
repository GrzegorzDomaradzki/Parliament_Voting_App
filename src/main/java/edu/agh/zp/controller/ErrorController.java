package edu.agh.zp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( value = { "/error" } )
public class ErrorController  {

    @GetMapping(value = {"accessdenied"})
    public String accessDenied() {
        return "403_Access_Denied";
    }

    @GetMapping(value = {"notfound"})
    public String notFound() {
        return "404 NOT_FOUND";
    }
}