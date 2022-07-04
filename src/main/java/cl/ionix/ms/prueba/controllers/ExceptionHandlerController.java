package cl.ionix.ms.prueba.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.ionix.ms.prueba.dtos.MessageDTO;
import cl.ionix.ms.prueba.enums.InternalCodes;
import cl.ionix.ms.prueba.exceptions.IonixException;
import cl.ionix.ms.prueba.utils.HeadersUtils;

@ControllerAdvice
public class ExceptionHandlerController {

	static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@Value("${properties.randomService.statusMessageNok}")
	private String statusMessageNok;

	@ExceptionHandler
	@ResponseBody
	ResponseEntity<MessageDTO> handleException(final Exception ex) {
		logger.info("handleException - init");
		logger.error("Se ha capturado un error", ex);
		MessageDTO response = handleResponseMessage(ex);
		logger.info("handleException - end");
		return new ResponseEntity<>(response, HeadersUtils.getGenericHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This method should handle every kind of exception and map it to an HTTP
	 * response
	 * 
	 * @param ex {@link Exception} An exception to be handled
	 * @return {@link MessageDTO} A response message mapped from the handled
	 *         exception
	 */
	private MessageDTO handleResponseMessage(final Exception ex) {

		if (ex instanceof IOException) {
			return new MessageDTO(statusMessageNok, InternalCodes.INTERNAL_SERVER_ERROR.getReasonPhrase());
		} else if (ex instanceof InterruptedException) {
			return new MessageDTO(statusMessageNok, InternalCodes.INTERNAL_SERVER_ERROR.getReasonPhrase());
		} else if (ex instanceof NullPointerException) {
			return new MessageDTO(statusMessageNok, InternalCodes.INTERNAL_SERVER_ERROR.getReasonPhrase());
		} else if (ex instanceof RuntimeException) {
			return new MessageDTO(statusMessageNok, InternalCodes.INTERNAL_SERVER_ERROR.getReasonPhrase());
		} else if (ex instanceof IonixException) {
			return new MessageDTO(statusMessageNok, ex.getMessage());
		} else {
			return new MessageDTO(ex.getMessage(), InternalCodes.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

}