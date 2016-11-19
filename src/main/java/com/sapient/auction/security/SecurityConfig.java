package com.sapient.auction.security;

//@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

	//@Autowired
	//private UserDetailsService userDetailsService;

	//@Override
	/*protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/user/register*//**")
			.permitAll()
		.antMatchers("/user/login*//**")
			.permitAll()
		.antMatchers("/sale")
			.authenticated()
		.antMatchers("/bid")
			.authenticated()
		.and()
			.csrf()
				.disable()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.addLogoutHandler(new CustomLogoutHandler());

	}*/

	//@Override
	/*public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
			//.passwordEncoder(new BCryptPasswordEncoder());
	}*/
}