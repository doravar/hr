package hu.webuni.hr.doravar.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.EmployeeRepository;

@Service
public class HrUserDetailsService implements UserDetailsService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee user = employeeRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
		return new HrUser(username, user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")), user);		// itt egy szerepet adok meg, mert mindenképpen kell szerepet kapnia
	// HrUser: hogy a UserDetails a teljes entitást megkapja, ne csak username, pw legyen benne
	}

}
