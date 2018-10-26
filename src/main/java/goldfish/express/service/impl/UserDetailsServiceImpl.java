package goldfish.express.service.impl;

import goldfish.express.entity.UserEntity;
import goldfish.express.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByPhoneNumber(phoneNumber);
        if(userEntity == null){
            throw new UsernameNotFoundException("user with username '" + phoneNumber + "' not found");
        }
        return User
                .builder()
                .username(userEntity.getPhoneNumber())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole())
                .build();
    }
}
