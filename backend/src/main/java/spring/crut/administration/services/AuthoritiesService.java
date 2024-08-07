package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.dto.AuthInfoDTO;
import spring.crut.administration.models.Authority;
import spring.crut.administration.repositories.AuthoritiesRepository;
import spring.crut.administration.security.CrutUserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;

    @Transactional
    public List<Authority> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }

    @Transactional
    public Authority getByName(String authority) {
        var actualAuthority = authoritiesRepository.findByName(authority);
        if (actualAuthority.isEmpty()) {
            throw new IllegalArgumentException("There is no such authority");
        }
        return actualAuthority.get();
    }

    public List<String> getAllAuthoritiesNames() {
        List<String> result = new ArrayList<>();
        for (var authority : authoritiesRepository.findAll()) {
            result.add(authority.getName());
        }
        return result;
    }

    public void createDefaultAuthorities() {
        // размеры двух массивов должны совпадать и i-ый элемент названий соответсвует i-ому элементу описанию
        String[] authoritiesNames = {"CREATE_UPDATE_DELETE_SECTIONS", "CREATE_UPDATE_DELETE_ARTICLES", "SET_HELPPAGE", "CHECK_ORIGINALDOCUMENT", "SEE_READ_ALLDOCUMENTS", "CREATE_DOCUMENTS", "UPDATE_DELETE_ALLDOCUMENTS", "ANNOTATE_ALLDOCUMENTS", "CHECK_ANNOTATEDDOCUMENTS", "LOAD_ALLDOCUMENTS", "CREATE_INFO", "UPDATE_DELETE_INFO", "CREATE_UPDATE_DELETE_ERRORTAGS", "CREATE_UPDATE_DELETE_ROLES", "CREATE_UPDATE_DELETE_USERS"};
        String[] authoritiesDescriptions = {"Редактирование секций главной страницы, также предоставляет доступ для соответствующего раздела админ панели", "Редактирование новостей, также предоставляет доступ для соответствующего раздела админ панели", "Изменение страницы помощи", "Увидеть оригинальный документ", "Доступ, чтение и фильтрация страницы всех документов, а также прочитать о тексте подробней (его метаинформацию и разметку)", "Создание документов  и редактирование собственных документов, кроме его статуса", "Доступ, чтение и фильтрация страницы всех документов, а также прочитать о тексте подробней (его метаинформацию и разметку), а также редактирование всех документов, также предоставляет доступ для соответствующего раздела админ панели", "Доступ, чтение и фильтрация всех документов, а также прочитать о тексте подробней (его метаинформацию и разметку), аннотирование документов, а также смена статуса на “Аннотировано”", "Смена статуса аннотированного документа на “Проверено”", "Выгрузка отфильтрованных документов со всей метаинформацией", "Создание новых типов метаинформации о тексте", "Редактирование уже созданных типов метаинформации", "Создание и редактирование тегов ошибок", "Создание и редактирование ролей, а также  чтение ролей", "Создание и редактирования пользователей, а также присваивание им ролей"};
        int size = authoritiesDescriptions.length;
        for (int i = 0; i < size; ++i) {
            var name = authoritiesNames[i];
            var description = authoritiesDescriptions[i];
            if (authoritiesRepository.findByName(name).isEmpty()) {
                Authority authority = new Authority();
                authority.setName(name);
                authority.setDescription(description);
                authoritiesRepository.save(authority);
            }
        }
    }
}
