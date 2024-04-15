package br.com.lucas.skillplus.domain.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.lucas.skillplus.api.dto.input.FotoInput;
import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.model.Foto;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.FotoRepository;


@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    // buscar foto por usuario logado
    public Foto getFoto(Usuario usuario) {
        return fotoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new NegocioException("Foto não encontrada"));
    }

    public Foto uploadFoto(Usuario usuario, FotoInput fotoInput) {
        try {
            MultipartFile arquivo = fotoInput.getArquivo();
    
            // Create a directory if it doesn't exist
            File directory = new File("src/main/resources/uploads");
            if (!directory.exists()) {
                directory.mkdir();
            }
    
            // Create a new file with the name as the user's email
            String fileName = usuario.getEmail() + ".jpg"; // Add appropriate file extension
            File newFile = new File(directory + "/" + fileName);
    
            // Copy the content of the uploaded file to the new file
            Files.copy(arquivo.getInputStream(), newFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
    
            // Get the existing photo if it exists, or create a new one
            Foto foto = fotoRepository.findByUsuario(usuario).orElse(new Foto());
            foto.setUsuario(usuario);
            foto.setFotoUrl(newFile.getPath());
            foto.setNome(usuario.getEmail());
            foto.setDescricao(fotoInput.getDescricao());
    
            // Add these lines to set the size and content type
            foto.setTamanho(arquivo.getSize());
            foto.setContentType(arquivo.getContentType());
    
            // Save the photo
            return fotoRepository.save(foto);
    
        } catch (IOException e) {
            throw new NegocioException("Erro ao salvar o arquivo", e);
        }
    }    

}
