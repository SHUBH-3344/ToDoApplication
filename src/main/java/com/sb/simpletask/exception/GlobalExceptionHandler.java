package com.sb.simpletask.exception;

import com.sb.simpletask.utils.CustomResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomResponseDTO("User already exist", "Failure", false, null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomResponseDTO> handleUserNotFound(UserNotFoundException ex) {
        CustomResponseDTO response = new CustomResponseDTO();
        response.setResultType("Error");
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<CustomResponseDTO> handleTaskNotFoundException(TaskNotFoundException ex) {
        CustomResponseDTO response = new CustomResponseDTO();
        response.setResultType("Error");
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponseDTO> handleGeneralException(Exception ex) {
        CustomResponseDTO response = new CustomResponseDTO();
        response.setResultType("Error");
        response.setMessage("An unexpected error occurred: " + ex.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomResponseDTO> handleJwtException(JwtException ex) {
        CustomResponseDTO responseDTO = new CustomResponseDTO();
        responseDTO.setMessage("JWT authentication failed: " + ex.getMessage());
        responseDTO.setResultType("Failure");
        responseDTO.setSuccess(false);
        responseDTO.setResultMap(null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        CustomResponseDTO responseDTO = new CustomResponseDTO();
        responseDTO.setMessage("Access denied: " + ex.getMessage());
        responseDTO.setResultType("Failure");
        responseDTO.setSuccess(false);
        responseDTO.setResultMap(null); // or omit this line if you want to leave it unset


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }

}