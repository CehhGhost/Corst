package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.DocumentDTO;
import spring.crut.corpus.dto.CertainSearchDTO;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;
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
    public ResponseEntity<HttpStatus> createDocument(@RequestBody DocumentDTO documentDTO) {
        var document = modelMapper.map(documentDTO, Document.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        document.setOwner(userDetails.getUser());
        documentsService.createDocument(document);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Integer id) {
        documentsService.deleteDocument(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/search/certain")
    public ResponseEntity<List<Sentence>> certainSearch(@RequestBody CertainSearchDTO certainSearchDTO) {
        List<Document> documents = documentsService.specifySubcorpus(certainSearchDTO);
        return ResponseEntity.ok(sentencesService.getByCertainSearch(documents, certainSearchDTO.getWordform()));
    }
}
