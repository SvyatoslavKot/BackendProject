package ru.personal_coach.auth_module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.personal_coach.auth_module.repositories.UserRepository;

@Service("appUserDetailsService")
public class AppUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
        return userRepository.findByMail(eMail)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getMail(),
                        user.getPassword(),
                        user.getRole().getAuthority()
                )).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
    }
}
