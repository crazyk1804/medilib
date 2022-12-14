package own.crazyk.medilib.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Member {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="idx")
	private long idx;
	@Column(name="identity", unique = true) // 8자로 password와 맞추고 싶었다...
	private String identity;
	@Column(name="password")
	private String password;
	@Column(name="username")
	private String username;
}
