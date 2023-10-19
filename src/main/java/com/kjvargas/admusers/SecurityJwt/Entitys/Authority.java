package com.kjvargas.admusers.SecurityJwt.Entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    private String authority;

}
