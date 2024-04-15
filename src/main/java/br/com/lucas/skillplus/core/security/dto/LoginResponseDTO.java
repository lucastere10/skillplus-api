package br.com.lucas.skillplus.core.security.dto;

public record LoginResponseDTO(String token, boolean hasPicture, boolean isProfileComplete) {
}
