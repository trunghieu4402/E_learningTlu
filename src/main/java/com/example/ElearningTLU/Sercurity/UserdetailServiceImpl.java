package com.example.ElearningTLU.Sercurity;

import com.example.ElearningTLU.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserdetailServiceImpl implements UserDetailsService {
    @Autowired
    private PersonRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserNameOrPersonId(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
