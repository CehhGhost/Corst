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

    public boolean equalsToLexGramTokenDTO(Token token, LexGramTokenDTO token1) {
        return token.getLemma().equals(token1.getWordform());
    }
}
