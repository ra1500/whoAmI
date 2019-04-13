package neural.security;

import model.UserEntityDto;
import core.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
//import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserEntityService userEntityService;  //used below

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();

        UserEntityDto foundUserEntityDto = userEntityService.getUserEntity(username,password);
        if (foundUserEntityDto != null) {
            return new UsernamePasswordAuthenticationToken
                    (username, password, new ArrayList<>()); //
                    //(username, password, Collections.emptyList());  //Collections.emptyList() issue
        } else {
            throw new
                    BadCredentialsException("authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}