package me.nexters.liliput.api.auth.process;

import me.nexters.liliput.api.domain.dto.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class UserDetailsImpl extends User {
    public UserDetailsImpl(String id, List<GrantedAuthority> authorities) {
        super(id, id, authorities);
    }

    public UserDetailsImpl(UserModel member, List<GrantedAuthority> authorities) {
        super(member.getUserId(), new BCryptPasswordEncoder().encode(member.getUserId()), authorities);
    }
}
