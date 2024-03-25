package spring.crut.corpus.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.dto.SentenceDTO;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;
import spring.crut.corpus.repositories.DocumentsRepository;
import spring.crut.corpus.repositories.SentencesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SentencesService {
    private final SentencesRepository sentencesRepository;
    private final TokensService tokensService;
    private final DocumentsRepository documentsRepository;
    @Transactional
    public void createSentences(List<SentenceDTO> sentenceDTOList, Document document) {
        for (var sentenceDTO : sentenceDTOList) {
            Sentence sentence = new Sentence();
            sentence.setText(sentenceDTO.getText());
            sentence.setNum(sentenceDTO.getNum());
            sentence.setDocument(document);
            sentence.setTokens(new ArrayList<>());
            sentence = sentencesRepository.save(sentence);
            document.getSentences().add(sentence);
            documentsRepository.save(document);
            tokensService.createTokens(sentenceDTO.getTokens(), sentence);
        }
    }

    @Transactional
    public List<Sentence> getByCertainSearch(List<Document> documents, String wordform) {
        List<Sentence> resultSentences = new ArrayList<>();
        for (var document : documents) {
            List<Sentence> sentences = document.getSentences();
            for (var sentence : sentences) {
                for (var token : sentence.getTokens()) {
                    if (token.getLemma().equals(wordform)) {
                        resultSentences.add(sentence);
                        break;
                    }
                }
            }
        }
        return resultSentences;
    }
}
