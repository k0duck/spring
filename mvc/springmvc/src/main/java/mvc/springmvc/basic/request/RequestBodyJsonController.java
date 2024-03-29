package mvc.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import mvc.springmvc.basic.HelloData;

@Slf4j
@Controller
public class RequestBodyJsonController {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();

		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("messageBody = {}", messageBody);

		HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

		response.getWriter().write("ok");
	}

	/**
	 * @RequestBody 메시지 바디 정보를 직접 조회
	 * @ResponseBody 메시지 바디 정보 직접 반환 (view 조회X)
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
		log.info("messageBody = {}", messageBody);
		HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

		return "ok";
	}

	/**
	 * @RequestBody 생략 불가능
	 * 생략시 @ModelAttribute 적용되어 버림
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(@RequestBody HelloData helloData) {
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
		HelloData helloData = httpEntity.getBody();
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
		return "ok";
	}

	/**
	 * 응답의 경우에도 @ResponseBody를 사용해 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
		return helloData;
	}
}
