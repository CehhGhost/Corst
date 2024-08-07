package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.*;
import spring.crut.corpus.enums.Status;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.services.DocumentsService;
import spring.crut.corpus.services.SentencesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
// TODO продумать ситуацию, что удаление и изменение позволительны для своих документов, если есть хотя бы разрешение CREATE_DOCUMENTS
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
    public ResponseEntity<HttpStatus> updateDocumentById(@PathVariable Long id, @RequestBody CreateUpdateDocumentDTO documentDTO) {
        documentsService.updateDocument(id, documentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDocumentById(@PathVariable Long id) {
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
            documentDTO.getSentences().sort(Comparator.comparing(SentenceDTO::getNum));
            documentsDTO.add(documentDTO);
        }
        return ResponseEntity.ok(documentsDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentByID(@PathVariable Long id) {
        Document document = documentsService.getDocumentByID(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        boolean flag = false;
        for (var authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals("SEE_READ_ALLDOCUMENTS")) {
                flag = true;
                break;
            }
        }
        if (!flag && !userDetails.getUser().getId().equals(document.getOwner().getId())) {
            return ResponseEntity.badRequest().build();
        }
        var documentDTO = modelMapper.map(document, DocumentDTO.class);
        documentDTO.setStatusNum(Status.valueOf(document.getStatus().name()).ordinal());
        documentsService.setAttrsForTokensInDocumentDTO(documentDTO);
        documentDTO.getSentences().sort(Comparator.comparing(SentenceDTO::getNum));
        return ResponseEntity.ok(documentDTO);
    }
    @PatchMapping ("/{id}/set_status/{status}")
    public ResponseEntity<?> updateStatusForDocumentById(@PathVariable Long id, @PathVariable Integer status) {
        documentsService.setStatusById(id, status);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    // TODO продумать ситуацию с тем, что полный контекст видят только пользователи с определенными правами
    @GetMapping("/sentences/{id}/get_context/{amount}")
    public ResponseEntity<?> getContextForSentence(@PathVariable Long id, @PathVariable Integer amount) {
        return ResponseEntity.ok(sentencesService.getContextForSentence(id, amount));
    }
}
