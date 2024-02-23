package com.genea.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedInUserUtils {
    public String  getLoggedInUser (){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            return userDetails.getUsername();
    }
}


