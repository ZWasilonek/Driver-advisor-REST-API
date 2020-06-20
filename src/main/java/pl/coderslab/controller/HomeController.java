package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.dto.UserDto;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    private String addressURL = "";
    private final String loginURL = "<a href=\"" + addressURL + "login\">login</a>";
    private final String swaggerHtmlURL = "<a href=\"" + addressURL + "swagger-ui.html\">swagger</a>";
    private final String swaggerDocumentationURL = "<a href=\"" + addressURL + "v2/api-docs\">swagger documentation</a>";

//    private static final String loginURL = "<a href=\"https://driver-advisor-rest-api.herokuapp.com/login\"/>";
//    private static final String swaggerHtmlURL = "<a href=\"https://driver-advisor-rest-api.herokuapp.com/swagger-ui.html\"/>";
//    private static final String swaggerDocumentationURL = "<a href=\"https://driver-advisor-rest-api.herokuapp.com/v2/api-docs\"/>";

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        addressURL = request.getRequestURL().toString();

        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" +
                    "<h2>WELCOME TO MY FIRST REST APP !</h2>\n" +
                    "<ul>\n" +
                        "<li>first you need to " + loginURL + " and you have 2 users prepared:</li>\n" +
                        "<ul>\n" +
                            "<li>user with ROLE_USER (username: user, password: user)</li>\n" +
                            "<li>admin with ROLE_ADMIN and ROLE_USER (username: admin, password: admin)</li>\n" +
                        "</ul>\n" +
                        "<li>you can test the application on " + swaggerHtmlURL + "</li>\n" +
                        "<li>you can see the documentation on " + swaggerDocumentationURL + "</li>" +
                    "</ul>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/welcome")
    public String helloAdmin() {

        return "<html>\n" + "<header><title>Login successful</title></header>\n" +
                "<body>\n" +
                    "<h2>WELCOME " + getUserFromSession().getUsername().toUpperCase() + " !</h2>\n" +
                    "<ul>\n" +
                        "<li>you can test the application on " + swaggerHtmlURL + "</li>\n" +
                        "<li>you can see the documentation on " + swaggerDocumentationURL + "</li>" +
                    "</ul>\n" +
                "</body>\n" +
                "</html>";
    }

    @ModelAttribute("userSession")
    public UserDto getUserFromSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUserName(username);
        }
        return null;
    }

}