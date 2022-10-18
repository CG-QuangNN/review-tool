package vn.codegym;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CurencyController {
    public static void main(String[] args) {
        ModelAndView modelAndView = new ModelAndView("/product/list","products", "", "", "");
    }
}
