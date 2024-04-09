package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.dto.LexGramTokenDTO;
import spring.crut.corpus.dto.TokenDTO;

import spring.crut.corpus.models.Sentence;
import spring.crut.corpus.models.Token;
import spring.crut.corpus.repositories.TokensRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokensService {
    private final TokensRepository tokensRepository;
    private final ObjectMapper objectMapper;
    @Transactional
    public void createTokens(List<TokenDTO> tokenDTOList, Sentence sentence) {
        StringBuilder lemmatizedSentence = new StringBuilder();
        for (var tokenDTO : tokenDTOList) {
            Token token = new Token();
            token.setText(tokenDTO.getText());
            token.setNum(tokenDTO.getNum());
            token.setPos(tokenDTO.getPos());
            token.setLemma(tokenDTO.getLemma());
            if (tokenDTO.getPos() != null && !tokenDTO.getPos().equals("PUNCT")) {
                lemmatizedSentence.append(" ").append(tokenDTO.getLemma()).append(" ");
            }
            try {
                token.setAttrs(objectMapper.writeValueAsString(tokenDTO.getAttrs()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            token.setSentence(sentence);
            tokensRepository.save(token);
            sentence.getTokens().add(token);
        }
        sentence.setLemmatizedText(lemmatizedSentence.toString());
    }
    public void setAttrsForTokensDTO(List<TokenDTO> tokensDTO, List<Token> tokens) {
        for (int i = 0; i < tokens.size(); ++i) {
            try {
                tokensDTO.get(i).setAttrs(objectMapper.readValue(tokens.get(i).getAttrs(), new TypeReference<>() {}));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Token> getAllBySentenceAndSort(Sentence sentence) {
        return tokensRepository.findAllBySentenceOrderByNum(sentence);
    }

    // TODO продумать, как работать с грамматикой и тегами ошибок
    public boolean equalsToLexGramTokenDTO(Token token, LexGramTokenDTO token1) {
        if (!token.getLemma().equals(token1.getWordform())) {
            return false;
        }
        boolean posFlag = token1.getPartOfSpeech() == null || token1.getPartOfSpeech().isEmpty() || token.getPos().equals(token1.getPartOfSpeech());
        if (!posFlag) {
            return false;
        }
        boolean attrFlag = true;
        Map<String, String> tokenAttrs = null;
        try {
            tokenAttrs = objectMapper.readValue(token.getAttrs(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (token1.getGrammar() == null || token1.getGrammar().isEmpty()) {
            return true;
        }
        boolean gramFlag = false;
        for (var grammar : token1.getGrammar()) {
            gramFlag = false;
            for (var feature : grammar.getValue()) {
                gramFlag = false;
                for (var value : feature.getValue()) {
                    if (tokenAttrs.get(feature.getName()).equals(value)) {
                        gramFlag = true;
                        break;
                    }
                }
                if (!gramFlag) {
                    break;
                }
            }
            if (!gramFlag) {
                break;
            }
        }
        return gramFlag;
    }

    public void deleteTokens(List<Token> tokens) {
        tokensRepository.deleteAll(tokens);
    }
}
