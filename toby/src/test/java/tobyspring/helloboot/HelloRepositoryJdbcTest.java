package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class HelloRepositoryJdbcTest {
	@Autowired
	HelloRepository helloRepository;

	@Test
	void findHelloFailed() {
		assertThat(helloRepository.findHello("Toby")).isNull();
	}

	@Test
	void increaseCount() {
		assertThat(helloRepository.countOf("Toby")).isZero();

		helloRepository.increaseCount("Toby");
		assertThat(helloRepository.countOf("Toby")).isEqualTo(1);

		helloRepository.increaseCount("Toby");
		assertThat(helloRepository.countOf("Toby")).isEqualTo(2);
	}
}
