package own.crazyk.medilib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import own.crazyk.medilib.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByIdentity(String identity);
}
