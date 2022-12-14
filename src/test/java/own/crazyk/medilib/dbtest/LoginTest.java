package own.crazyk.medilib.dbtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import own.crazyk.medilib.service.LoginService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
	@Autowired
	LoginService loginService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Test
	void loginTest() {
		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken("admin", "password"));
		System.out.println(authentication.getCredentials());
	}
}
