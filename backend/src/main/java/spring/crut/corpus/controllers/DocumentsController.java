package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.CreateUpdateDocumentDTO;
import spring.crut.corpus.dto.CertainSearchDTO;
import spring.crut.corpus.dto.DocumentDTO;
import spring.crut.corpus.dto.LexGramSearchDTO;
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
    public ResponseEntity<HttpStatus> createDocument(@RequestBody CreateUpdateDocumentDTO documentDTO) {
        documentsService.createDocument(documentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    // TODO подумать насчет изменения документов без необходимых прав
    // TODO стоит задуматься над аннотациями
    public ResponseEntity<HttpStatus> updateDocumentById(@PathVariable Integer id, @RequestBody CreateUpdateDocumentDTO documentDTO) {
        documentsService.updateDocument(id, documentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDocumentById(@PathVariable Integer id) {
        documentsService.deleteDocument(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/search/certain")
    public ResponseEntity<?> certainSearch(@RequestBody CertainSearchDTO certainSearchDTO) {
        List<Document> documents = documentsService.specifySubcorpus(certainSearchDTO.getSubcorpusData());
        return ResponseEntity.ok(sentencesService.getByCertainSearch(documents, certainSearchDTO.getWordform()));
    }
    @PostMapping("/search/lex_gram")
    public ResponseEntity<?> lexGramSearch(@RequestBody LexGramSearchDTO lexGramSearchDTO) {
        List<Document> documents = documentsService.specifySubcorpus(lexGramSearchDTO.getSubcorpusData());
        return ResponseEntity.ok(sentencesService.getByLexGramSearch(documents, lexGramSearchDTO.getLexGramTokensDTO()));
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
        documentDTO.setStatusNum(Status.valueOf(document.getStatus().name()).ordinal());
        documentsService.setAttrsForTokensInDocumentDTO(documentDTO);
        return ResponseEntity.ok(documentDTO);
    }
    @PatchMapping ("/{id}/set_status/{status}")
    public ResponseEntity<?> updateStatusForDocumentById(@PathVariable Integer id, @PathVariable Integer status) {
        documentsService.setStatusById(id, status);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
