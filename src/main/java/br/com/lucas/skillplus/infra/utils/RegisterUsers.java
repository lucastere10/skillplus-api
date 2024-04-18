package br.com.lucas.skillplus.infra.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.lucas.skillplus.core.security.dto.RegisterDTO;

public class RegisterUsers {

    private static final String REGISTER_URL = "http://localhost:8080/api/auth/register";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // List of users to be registered
        RegisterDTO[] users = {
            new RegisterDTO("SpringySam", "sam@mail.com", "admin"),
            new RegisterDTO("JavaJill", "jill@mail.com", "admin"),
            new RegisterDTO("RestfulRyan", "ryan@mail.com", "admin"),
            new RegisterDTO("BootifulBeth", "beth@mail.com", "admin"),
            new RegisterDTO("DockerDave", "dave@mail.com", "admin"),
            new RegisterDTO("KubernetesKate", "kate@mail.com", "admin"),
            new RegisterDTO("MicroserviceMike", "mike@mail.com", "admin"),
            new RegisterDTO("CloudyChris", "chris@mail.com", "admin"),
            new RegisterDTO("ContainerCara", "cara@mail.com", "admin"),
            new RegisterDTO("DevOpsDan", "dan@mail.com", "admin"),
            new RegisterDTO("AgileAlex", "alex@mail.com", "admin"),
            new RegisterDTO("BackendBella", "bella@mail.com", "admin"),
            new RegisterDTO("FrontendFelix", "felix@mail.com", "admin"),
            new RegisterDTO("TestingTina", "tina@mail.com", "admin"),
            new RegisterDTO("SecuritySteve", "steve@mail.com", "admin"),
            new RegisterDTO("DatabaseDiana", "diana@mail.com", "admin"),
            new RegisterDTO("NetworkNina", "nina@mail.com", "admin"),
            new RegisterDTO("ApiAdam", "adam@mail.com", "admin"),
            new RegisterDTO("UiUma", "uma@mail.com", "admin"),
            new RegisterDTO("GitGary", "gary@mail.com", "admin")
        };
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        for (RegisterDTO user : users) {
            HttpEntity<RegisterDTO> request = new HttpEntity<>(user, headers);
            ResponseEntity<Void> response = restTemplate.exchange(REGISTER_URL, HttpMethod.POST, request, Void.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("User " + user.nome() + " registered successfully.");
            } else {
                System.out.println("Failed to register user " + user.nome());
            }
        }
    }
}
