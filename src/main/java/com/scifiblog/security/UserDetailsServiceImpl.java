package com.scifiblog.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.scifiblog.model.Leitor;
import com.scifiblog.repository.LeitorRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private LeitorRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Leitor> user = userRepository.findByEmail(userName);
		user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));
		return user.map(UserDetailsImpl::new).get();
	}
	
}
