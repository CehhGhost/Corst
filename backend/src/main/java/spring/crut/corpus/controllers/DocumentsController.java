package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.CreateDocumentDTO;
import spring.crut.corpus.dto.CertainSearchDTO;
import spring.crut.corpus.dto.DocumentDTO;
import spring.crut.corpus.enums.Status;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.services.DocumentsService;
import spring.crut.corpus.services.SentencesService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentsController {
    private final ModelMapper modelMapper;
    private final DocumentsService documentsService;
    private final SentencesService sentencesService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createDocument(@RequestBody CreateDocumentDTO documentDTO) {
        documentDTO.setAuthorsGender(documentDTO.getAuthorsGender().toUpperCase());
        var document = modelMapper.map(documentDTO, Document.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        document.setOwner(userDetails.getUser());
        documentsService.createDocument(document, documentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Integer id) {
        documentsService.deleteDocument(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/search/certain")
    public ResponseEntity<?> certainSearch(@RequestBody CertainSearchDTO certainSearchDTO) {
        // TODO List<Document> documents = documentsService.specifySubcorpus(certainSearchDTO); учитываает подкорпус, но требует настройки
        List<Document> documents = documentsService.getAllDocuments();
        return ResponseEntity.ok(sentencesService.getByCertainSearch(documents, certainSearchDTO.getWordform()));
    }
    @GetMapping
    public ResponseEntity<?> getAllDocuments() {
        List<DocumentDTO> documentsDTO = new ArrayList<>();
        for (var document: documentsService.getAllDocuments()) {
            var documentDTO = modelMapper.map(document, DocumentDTO.class);
            documentDTO.setOwnerUsername(document.getOwner().getUsername());
            documentDTO.setStatusNum(Status.valueOf(document.getStatus().name()).ordinal());
            documentsService.setAttrsForTokensInDocumentDTO(documentDTO);
            documentsDTO.add(documentDTO);
        }
        return ResponseEntity.ok(documentsDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentByID(@PathVariable Integer id) {
        Document document = documentsService.getDocumentByID(id);
        var documentDTO = modelMapper.map(document, DocumentDTO.class);
        documentsService.setAttrsForTokensInDocumentDTO(documentDTO);
        return ResponseEntity.ok(documentDTO);
    }
    @PutMapping("/{id}/set_status/{status}")
    public ResponseEntity<?> updateStatusForDocument(@PathVariable Integer id, @PathVariable Integer status) {
        documentsService.setStatusById(id, status);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
