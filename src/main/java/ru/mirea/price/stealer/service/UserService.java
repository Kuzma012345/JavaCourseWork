package ru.mirea.price.stealer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.price.stealer.dto.domain.database.UserRegistrationDto;
import ru.mirea.price.stealer.model.PriceStealerUser;
import ru.mirea.price.stealer.model.Role;
import ru.mirea.price.stealer.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public PriceStealerUser save(UserRegistrationDto registrationDto) {
		PriceStealerUser user = PriceStealerUser.builder()
				.firstName(registrationDto.getFirstName())
				.lastName(registrationDto.getLastName())
				.email(registrationDto.getEmail())
				.password(passwordEncoder.encode(registrationDto.getPassword()))
				.roles(List.of(
						Role.builder()
								.name("ROLE_USER")
								.build()
					)
				)
				.build()
		;

		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PriceStealerUser user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new User(
				user.getEmail(),
				user.getPassword(),
				mapRolesToAuthorities(user.getRoles())
		);
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList())
		;
	}
}
