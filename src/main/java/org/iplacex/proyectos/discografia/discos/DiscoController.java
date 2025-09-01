package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
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
public class DiscoController {

//inyeccion de dependencias
private final IDiscoRepository discoRepository;
private final IArtistaRepository artistaRepository;

//constructor de DiscoController
public DiscoController(IDiscoRepository discoRepository, IArtistaRepository artistaRepository) {
this.discoRepository = discoRepository;
this.artistaRepository = artistaRepository;
}

//metodo para manejar POST para crear un nuevo disco
@PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
// Se validara existencia del artista antes de guardar
if (disco.idArtista == null || !artistaRepository.existsById(disco.idArtista)) {
return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe para idArtista: " + disco.idArtista);
}
Disco saved = discoRepository.save(disco);
return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}

//metodo para manejar GET para obtener los discos
@GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
return ResponseEntity.ok(discoRepository.findAll());
}

//metodo para manejar GET para obtener un disco por id
@GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
Optional<Disco> found = discoRepository.findById(id);
return found.<ResponseEntity<Object>>map(ResponseEntity::ok)
.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disco no encontrado"));
}

//metodo para manejar PUT para actualizar un disco por id
@GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
List<Disco> discos = discoRepository.findDiscosByIdArtista(idArtista);
return ResponseEntity.ok(discos);
}
}