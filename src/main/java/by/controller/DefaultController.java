package by.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Redirection to the view-item controller as the start page
 * and also redirection after logging in.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Controller
public class DefaultController {

    @RequestMapping(value = {"/", "/login**"}, method = {RequestMethod.GET})
    public String viewPage() {
        return "redirect:/view-race";
    }
}
