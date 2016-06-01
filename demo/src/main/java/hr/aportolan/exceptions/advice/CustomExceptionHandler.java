package hr.aportolan.exceptions.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hr.aportolan.dto.ResponseObject;
import hr.aportolan.dto.VoidData;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ResponseObject<VoidData>> badRequest(HttpServletRequest req, Exception exception) {
		exception.printStackTrace();
		LOGGER.error("Controller caught error!", exception);
		ResponseObject<VoidData> transfer = new ResponseObject<>(ExceptionUtils.getRootCauseMessage(exception));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<ResponseObject<VoidData>>(transfer, headers, HttpStatus.BAD_REQUEST);
	}
}