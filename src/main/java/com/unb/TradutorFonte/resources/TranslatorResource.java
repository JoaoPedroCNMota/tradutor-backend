package com.unb.TradutorFonte.resources;

import com.amazonaws.handlers.StackedRequestHandler;
import com.sun.java.accessibility.util.Translator;
import com.unb.TradutorFonte.domain.Code;
import com.unb.TradutorFonte.services.TranslatorService;
import javazoom.jl.decoder.JavaLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/translate")
public class TranslatorResource {

    private final TranslatorService translatorService;

    public TranslatorResource(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

//    @RequestMapping(value = "/codeToSpeech", method = RequestMethod.POST)
//    public ResponseEntity<?> synthesizeCode() throws IOException, JavaLayerException {
//
//        translatorService.speechCode();
//        return null;
//    }

    @RequestMapping(value = "/codeToPseudocode", method = RequestMethod.POST)
    public ResponseEntity<?> translateToPseudocode(@RequestBody String sourceCode){

        Code code = new Code().builder().code(sourceCode).build();

        translatorService.codeToPseudocode(code);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }


    @RequestMapping(value = "/testVoice", method = RequestMethod.POST)
    public ResponseEntity<?> translateToPseudocode() throws IOException, JavaLayerException {
//        String teste = "O português é uma língua indo-europeia, do grupo das línguas românicas (ou latinas), as quais descendem do latim, pertencente ao ramo itálico da família indo";

        String teste = "#include <stdio.h>\n" +
                "int main() {\n" +
                "   // printf() displays the string inside quotation\n" +
                "   printf(\"Hello, World!\");\n" +
                "   return 0;\n" +
                "}";


        translatorService.speechCode(teste);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
