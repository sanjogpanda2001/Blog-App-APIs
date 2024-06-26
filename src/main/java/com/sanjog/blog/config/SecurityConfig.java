package com.sanjog.blog.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sanjog.blog.security.CustomUserDetailService;
import com.sanjog.blog.security.JwtAuthenticationEntryPoint;
import com.sanjog.blog.security.JwtAuthenticationFilter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

//@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableWebMvc
//@EnableSwagger2

//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig {
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public static final String []urls= {"/api/v1/auth/login","/v3/api-docs","/v2/api-docs","/api/v1/auth/register"
			,"/swagger-resources/**","/swagger-ui/**","/swagger-ui.html","/swagger-ui/index.html","/webjars/**"};

	//@Override
	/*protected void configure(HttpSecurity http)throws Exception{
		http.csrf().disable()
		.authorizeHttpRequests().antMatchers("/api/v1/auth/login").permitAll().anyRequest().authenticated().and()
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
	}
	*/
	//@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests().requestMatchers(urls).permitAll()
		.requestMatchers(HttpMethod.GET).permitAll()
//		.requestMatchers("/api/v1/auth/register").permitAll()
		.anyRequest().authenticated().and()
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
	    http.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain dfc=http.build();
	    return dfc;
	}
	//@Override
	/*protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}*/
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configure) throws Exception {
//		return configure.getAuthenticationManager();
//	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configure) throws Exception {
		return configure.getAuthenticationManager();
	}
}
