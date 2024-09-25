package com.dh.apiClinic.DTO.ModelChatGPT.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
@Service
public class OpenAIService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);
        return factory;
    }

    public String realizarConsultaGPT(String consulta) {
        try {
            String url = "https://api.openai.com/v1/chat/completions";
            String authToken = "Bearer " + apiKey;

            RestTemplate restTemplate = restTemplate();

            // Configurar la solicitud
            String requestJson = "{\"model\": \"gpt-3.5-turbo\"," +
                    " \"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}," +
                    "{\"role\": \"user\", \"content\": \"" + consulta + "\"}" +
                    "]" +
                    "}";

            // Configurar las cabeceras de la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authToken);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

            // Enviar la solicitud y recibir la respuesta
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            // Procesar la respuesta según tus necesidades
            String respuesta = procesarRespuestaGPT(responseEntity.getBody());

            return respuesta;
        } catch (HttpClientErrorException ex) {
            int statusCode = ex.getRawStatusCode(); // Obtener el código de estado HTTP
            String statusText = ex.getStatusText(); // Obtener el texto del estado HTTP
            // Manejar el código de estado HTTP, por ejemplo:
            return "Error al procesar la solicitud a GPT. Código de error: " + statusCode + ", Descripción: " + statusText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar la solicitud a GPT";
        }
    }

    private String procesarRespuestaGPT(String jsonResponse) {
        try {
            // Parsear la respuesta JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Obtener el contenido del mensaje
            JsonNode choicesNode = rootNode.get("choices");
            if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode firstChoice = choicesNode.get(0);
                JsonNode messageNode = firstChoice.get("message");
                if (messageNode != null) {
                    JsonNode contentNode = messageNode.get("content");
                    if (contentNode != null) {
                        // Devolver el contenido del mensaje
                        return contentNode.asText();
                    }
                }
            }
            // Si no se encuentra el contenido del mensaje, devolver la respuesta JSON completa
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar cualquier error y devolver la respuesta JSON completa
            return jsonResponse;
        }
    }
}
