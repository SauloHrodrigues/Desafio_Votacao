package com.db.votacao.desafio_votacao.exceptions;

import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoJaExistenteException;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoNaoExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AssociadoJaExistenteException.class)
    public ResponseEntity<Object> handlerAssociadoJaExistenteException(AssociadoJaExistenteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Erro! ", exception.getMessage()));
    }

    @ExceptionHandler(AssociadoNaoExistenteException.class)
    public ResponseEntity<Object> handlerAssociadoNaoExistenteException(AssociadoNaoExistenteException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erro! ", exception.getMessage()));
    }



//    ************************************************************************
    static class ErrorResponse {
        private String tipoErro;
        private String mensagem;

        public ErrorResponse(String tipoErro, String mensagem) {
            this.tipoErro = tipoErro;
            this.mensagem = mensagem;
        }

        public String getTipoErro() {
            return tipoErro;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}