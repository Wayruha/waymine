/*
package az.mecid.hiberdemo;

import az.mecid.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdsDao adsDao;


    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        az.mecid.models.User domainUser = (az.mecid.models.User) adsDao.getUserByLogin(login);
        System.out.println("Почало шукати логін");
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User (
                domainUser.getLogin(),
                domainUser.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(domainUser.getRole())
        );
    }

    public Collection getAuthorities(Role role) {
        List authList = getGrantedAuthorities(getRoles(role));

        return authList;
    }

    public List getRoles(Role  role) {

        List<String> roles = new ArrayList<String>();
        if (role.equals(Role.Admin)) {
            roles.add("ROLE_MODERATOR");
            roles.add("ROLE_ADMIN");
        } else if (role.equals(Role.Manager)) {
            roles.add("ROLE_MODERATOR");
        }    else if (role.equals(Role.Employee)) {
            roles.add("ROLE_USER");
        }
        return roles;
    }

    public static List getGrantedAuthorities(List roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        for (Object role : roles) {
            System.out.println("має вивести ролі."+role.toString());
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }

}*/
