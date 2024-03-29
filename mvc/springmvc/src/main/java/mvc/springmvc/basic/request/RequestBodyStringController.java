package mvc.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyStringController {
	@PostMapping("/request-body-string-v1")
	public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("messageBody = {}", messageBody);
		response.getWriter().write("ok");
	}

	/**
	 * InputStream(Reader) HTTP 요청 메시지 바디의 내용을 직접 조회
	 * OutputStream(Writer) HTTP 응답 메시지의 바디에 직접 결과 출력
	 */
	@PostMapping("/request-body-string-v2")
	public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException {
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("messageBody = {}", messageBody);
		writer.write("ok");
	}

	/**
	 * HttpEntity
	 * 메시지 바디 정보를 직접 조회
	 * 응답에서도 사용 가능
	 */
	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
		String messageBody = httpEntity.getBody();
		log.info("messageBody = {}", messageBody);
		return new HttpEntity<>("ok");
	}

	/**
	 * @RequestBody 메시지 바디 정보를 직접 조회
	 * @ResponseBody 메시지 바디 정보 직접 반환 (view 조회X)
	 */
	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4(@RequestBody String messageBody) {
		log.info("messageBody = {}", messageBody);
		return "ok";
	}
}
