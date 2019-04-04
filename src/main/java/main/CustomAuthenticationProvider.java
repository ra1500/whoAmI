package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired //ryan
    UserEntityService userEntityService;  //ryan

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();

        System.out.println("before");

        UserEntityDto foundUserEntityDto = userEntityService.getUserEntity(username,password);  //ryan
        //System.out.println(foundUserEntityDto.getUserName());

        System.out.println("after");

        System.out.println(username);
        System.out.println(password);


        String user = "rr";  //ryan
        String pass = "tt";  //ryan

        System.out.println(user);
        System.out.println(pass);

        if (user.equals(username) && pass.equals(password)) {
            return new UsernamePasswordAuthenticationToken
                    (username, password, new ArrayList<>()); //
                    //(username, password, Collections.emptyList());
        } else {
            System.out.println("throwing exception in authenticate");


            throw new
                    BadCredentialsException("authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}