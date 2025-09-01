package org.iplacex.proyectos.discografia.artistas;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//uso de los controladores @RestController y @CrossOrigin
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    // inyeccion de dependencias
    private final IArtistaRepository artistaRepository;

    // constructor de ArtistaController
    public ArtistaController(IArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    // uso de los mapeos @PostMapping, @GetMapping, @PutMapping y @DeleteMapping
    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista saved = artistaRepository.save(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // uso de @GetMapping
    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return ResponseEntity.ok(artistaRepository.findAll());
    }

    // uso de @GetMapping con id
    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> found = artistaRepository.findById(id);
        return found.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado"));
    }

    // uso de @PutMapping
    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable("id") String id,
            @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }
        artista._id = id;
        Artista updated = artistaRepository.save(artista);
        return ResponseEntity.ok(updated);
    }

    // uso de @DeleteMapping
    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }
        artistaRepository.deleteById(id);
        return ResponseEntity.ok("Artista eliminado");
    }
}