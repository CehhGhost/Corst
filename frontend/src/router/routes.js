const routes = [
  {
    path: "/",
    component: () => import("../views/MainView.vue"),
  },
  {
    path: "/news",
    component: () => import("../views/content/AllArticlesView.vue"),
  },
  {
    path: "/search",
    component: () => import("../views/SearchView.vue"),
  },
  {
    path: "/login",
    component: () => import("../views/authorization/LoginView.vue"),
  },
  {
    path: "/statistics",
    component: () => import("../views/StatisticsView.vue"),
  },
  {
    path: "/createArticle",
    component: () => import("../views/content/CreateArticleView.vue"),
  },
  {
    path: "/help",
    component: () => import("../views/HelpView.vue"),
  },
  {
    path: "/documents",
    component: () => import("../views/corpus/AllDocumentsView.vue"),
  },
  {
    path: "/addDocument",
    component: () => import("../views/corpus/CreateDocumentView.vue"),
  },
  {
    path: "/annotateDocument/:id",
    component: () => import("../views/corpus/AnnotateDocumentView.vue"),
  },
  {
    path: "/editDocument/:id",
    component: () => import("../views/corpus/EditDocumentView.vue"),
  },
  {
    path: "/editArticle/:id",
    component: () => import("../views/content/EditArticleView.vue"),
  },
  {
    path: "/documents/:id",
    component: () => import("../views/corpus/DocumentView.vue"),
  },
  {
    path: "/admin",
    component: () => import("../views/administration/AdminMainView.vue"),
  },
  {
    path: "/admin/documents",
    component: () =>
      import(
        "../views/administration/corpus/document/AdminAllDocumentsView.vue"
      ),
  },
  {
    path: "/admin/documents/:id",
    component: () =>
      import(
        "../views/administration/corpus/document/AdminAllDocumentsView.vue"
      ),
  },
  {
    path: "/admin/documents/create",
    component: () =>
      import(
        "../views/administration/corpus/document/AdminCreateDocumentView.vue"
      ),
  },
  {
    path: "/admin/articles",
    component: () =>
      import(
        "../views/administration/content/article/AdminAllArticlesView.vue"
      ),
  },
  {
    path: "/admin/articles/create",
    component: () =>
      import(
        "../views/administration/content/article/AdminCreateArticleView.vue"
      ),
  },
  {
    path: "/admin/articles/:id",
    component: () =>
      import(
        "src/views/administration/content/article/AdminEditArticleView.vue"
      ),
  },
  {
    path: "/admin/sections",
    component: () =>
      import(
        "../views/administration/content/section/AdminAllSectionsView.vue"
      ),
  },
  {
    path: "/admin/sections/create",
    component: () =>
      import(
        "../views/administration/content/section/AdminCreateSectionsView.vue"
      ),
  },
  {
    path: "/admin/sections/:id",
    component: () =>
      import(
        "../views/administration/content/section/AdminEditSectionsView.vue"
      ),
  },
  {
    path: "/admin/users",
    component: () =>
      import("../views/administration/admin/user/AdminAllUsersView.vue"),
  },
  {
    path: "/admin/users/create",
    component: () =>
      import("../views/administration/admin/user/AdminCreateUserView.vue"),
  },
  {
    path: "/admin/users/:id",
    component: () =>
      import("../views/administration/admin/user/AdminEditUserView.vue"),
  },
  {
    path: "/admin/roles",
    component: () =>
      import("../views/administration/admin/role/AdminAllRolesView.vue"),
  },
  {
    path: "/admin/roles/create",
    component: () =>
      import("../views/administration/admin/role/AdminCreateRoleView.vue"),
  },
  {
    path: "/admin/roles/:id",
    component: () =>
      import("../views/administration/admin/role/AdminEditRoleView.vue"),
  },
  {
    path: "/admin/authorities",
    component: () =>
      import(
        "../views/administration/admin/authorities/AdminAllAuthoritiesView.vue"
      ),
  },
  {
    path: "/admin/error_tags",
    component: () =>
      import("../views/administration/info/errorTag/AdminAllErrorTagsView.vue"),
  },
  {
    path: "/admin/error_tags/create",
    component: () =>
      import(
        "../views/administration/info/errorTag/AdminCreateErrorTagView.vue"
      ),
  },
  {
    path: "/admin/error_tags/:id",
    component: () =>
      import("../views/administration/info/errorTag/AdminEditErrorTagView.vue"),
  },
  {
    path: "/admin/domains",
    component: () =>
      import("../views/administration/info/domain/AdminAllDomainsView.vue"),
  },
  {
    path: "/admin/domains/create",
    component: () =>
      import("../views/administration/info/domain/AdminCreateDomainView.vue"),
  },
  {
    path: "/admin/domains/:id",
    component: () =>
      import("../views/administration/info/domain/AdminEditDomainView.vue"),
  },
  {
    path: "/admin/academic_majors",
    component: () =>
      import(
        "../views/administration/info/academicMajor/AdminAllAcademicMajorView.vue"
      ),
  },
  {
    path: "/admin/academic_majors/create",
    component: () =>
      import(
        "../views/administration/info/academicMajor/AdminCreateAcademicMajorView.vue"
      ),
  },
  {
    path: "/admin/academic_majors/:id",
    component: () =>
      import(
        "../views/administration/info/academicMajor/AdminEditAcademicMajorView.vue"
      ),
  },
  {
    path: "/admin/courses",
    component: () =>
      import("../views/administration/info/course/AdminAllCoursesView.vue"),
  },
  {
    path: "/admin/courses/create",
    component: () =>
      import("../views/administration/info/course/AdminCreateCourseView.vue"),
  },
  {
    path: "/admin/courses/:id",
    component: () =>
      import("../views/administration/info/course/AdminEditCourseView.vue"),
  },
  {
    path: "/admin/genres",
    component: () =>
      import("../views/administration/info/genre/AdminAllGenresView.vue"),
  },
  {
    path: "/admin/genres/create",
    component: () =>
      import("../views/administration/info/genre/AdminCreateGenreView.vue"),
  },
  {
    path: "/admin/genres/:id",
    component: () =>
      import("../views/administration/info/genre/AdminEditGenreView.vue"),
  },
  // Error route
  {
    path: "/:catchAll(.*)*",
    //TODO: Create a 404 page
    component: () => import("../views/MainView.vue"),
  },
];

export default routes;
