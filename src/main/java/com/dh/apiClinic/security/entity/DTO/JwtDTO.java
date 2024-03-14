package com.dh.apiClinic.security.entity.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtDTO {
    private String token;
    private String bearer = "Bearer";
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean changedPassword;

    public JwtDTO(String token, String userName, Collection<? extends GrantedAuthority> authorities, Boolean changedPassword) {
        this.token = token;
        this.userName = userName;
        this.authorities = authorities;
        this.changedPassword = changedPassword;
    }


}
