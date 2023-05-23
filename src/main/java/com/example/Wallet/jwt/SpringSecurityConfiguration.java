package com.example.Wallet.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;


@Configuration// this indicates that this class is a configuration class that provides bean definitions.
@EnableWebSecurity//this enables Spring Security's web security support in the application.
//WebSecurityConfigurerAdapter, which is a convenient base class for creating a custom security configuration.
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//It sets the CustomUserDetailsService as the user details service and specifies the PasswordEncoder for password encoding.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}


	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		//It exposes the AuthenticationManager bean, which allows other parts of the application to use it.
		return super.authenticationManagerBean();
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {


		//disables CSRF (Cross-Site Request Forgery) protection.
		//starts defining the authorization rules for different endpoints.
		//sets the custom JWT authentication entry point, which handles authentication exceptions.
		//configures the session management to be stateless, meaning no session is created or used.
		//adds the CustomJwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter in the filter chain for JWT token-based authentication.

		http.csrf().disable()
		.authorizeRequests().antMatchers("/publish/{name}","/wallet","/wallet/{phone}","/wallet/all","/transaction2/{transactionid}","/transaction2/all","/transaction2/phone/{payerphone}","/transaction2","/elastictransaction","/elastictransaction/{phone}").hasRole("ADMIN")
		.antMatchers("/wallet/all").hasAnyRole("USER","ADMIN")
		.antMatchers("/authenticate", "/register").permitAll().anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
		and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}