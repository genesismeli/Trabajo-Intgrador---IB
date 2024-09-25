package com.dh.apiClinic.controller;
import com.dh.apiClinic.DTO.ModelChatGPT.Service.OpenAIService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "AI", description = "Operations about AI")
@RequestMapping("/ai")
@RestController
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @GetMapping("/obtenerRespuesta")
    public String obtenerRespuesta(@RequestParam String mensaje) {
        return openAIService.realizarConsultaGPT(mensaje);
    }
}
