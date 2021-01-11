package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	
		
		@Autowired
		private LoginAccessDeniedHandler accessDeniedHandler;
		
		@Autowired
		private UserDetailsServiceImpl  userDetailsService;
		
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.csrf().disable();
			http.headers().frameOptions().disable();
			http.authorizeRequests()
			//Restrict HTML pages is wrong
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/guest").hasRole("GUEST")
			.antMatchers("/member").hasRole("MEMBER")
			.antMatchers(HttpMethod.POST, "/register").permitAll()
			.antMatchers("/", "/css/**", "/images/**", "/js/**", "/**").permitAll() 
			.antMatchers("/h2-console/**").permitAll()   //Remove before deployment
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout") 
			.permitAll()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler);
			
		}
		

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
//			auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//			.withUser("Yulia").password("123").roles("OWNER")
//			.and()
//			.withUser("Scott").password("123").roles("MANAGER")
//			.and()
//			.withUser("Tonie").password("123").roles("EMPLOYEE")
//			.and()
//			.withUser("Admin").password("123").roles("OWNER", "MANAGER", "EMPLOYEE");
//			
//		}
//		

}
