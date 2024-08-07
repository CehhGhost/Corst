package spring.crut.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import spring.crut.administration.dto.CreateUpdateRoleDTO;
import spring.crut.administration.dto.CreateUserDTO;
import spring.crut.administration.services.AuthoritiesService;
import spring.crut.administration.services.RolesService;
import spring.crut.administration.services.UsersService;
import spring.crut.corpus.services.info.AcademicMajorsService;
import spring.crut.corpus.services.info.CoursesService;
import spring.crut.corpus.services.info.DomainsService;
import spring.crut.corpus.services.info.GenresService;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UsersService usersService;
    private final RolesService rolesService;
    private final AuthoritiesService authoritiesService;
    private final AcademicMajorsService academicMajorsService;
    private final CoursesService coursesService;
    private final DomainsService domainsService;
    private final GenresService genresService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authoritiesService.createDefaultAuthorities();
        rolesService.createDefaultRoles();
        usersService.createDefaultUsers();
        academicMajorsService.createDefaultInfo();
        coursesService.createDefaultInfo();
        domainsService.createDefaultInfo();
        genresService.createDefaultInfo();
    }
}
