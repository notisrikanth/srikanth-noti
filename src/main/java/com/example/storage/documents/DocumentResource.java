package com.example.storage.documents;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class DocumentResource {
	
	@Autowired
	private DocumentRepository documentRepository;

	@GetMapping("/documents")
	public List<Document> retrieveAllDocuments() {
		return documentRepository.findAll();
	}

	@GetMapping("/documents/{id}")
	public Document retrieveDocument(@PathVariable long id) {
		Optional<Document> document = documentRepository.findById(id);

		if (!document.isPresent())
			throw new DocumentNotFoundException("id-" + id);

		return document.get();
	}

	@DeleteMapping("/documents/{id}")
	public void deleteDocument(@PathVariable long id) {
		documentRepository.deleteById(id);
	}

	@PostMapping("/documents")
	public ResponseEntity<Object> createDocument(@RequestBody Document document) {
		Document savedDocument = documentRepository.save(document);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedDocument.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/documents/{id}")
	public ResponseEntity<Object> updatedocument(@RequestBody Document document, @PathVariable long id) {

		Optional<Document> documentOptional = documentRepository.findById(id);

		if (!documentOptional.isPresent())
			return ResponseEntity.notFound().build();

		document.setId(id);
		
		documentRepository.save(document);

		return ResponseEntity.noContent().build();
	}

}
