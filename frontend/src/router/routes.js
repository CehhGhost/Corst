const routes = [
  {
    path: "/",
    component: () => import("../views/MainView.vue"),
  },
  {
    path: "/news",
    component: () => import("../views/AllArticlesView.vue"),
  },
  {
    path: "/search",
    component: () => import("../views/SearchView.vue"),
  },
  {
    path: "/login",
    component: () => import("../views/LoginView.vue"),
  },
  {
    path: "/statistics",
    component: () => import("../views/StatisticsView.vue"),
  },
  {
    path: "/createArticle",
    component: () => import("../views/CreateArticleView.vue"),
  },
  {
    path: "/help",
    component: () => import("../views/HelpView.vue"),
  },
  {
    path: "/demo",
    component: () => import("../views/DemoView.vue"),
  },
  {
    path: "/documents",
    component: () => import("../views/AllDocumentsView.vue"),
  },
  {
    path: "/addDocument",
    component: () => import("../views/CreateDocumentView.vue"),
  },
  {
    path: "/annotateDocument/:id",
    component: () => import("../views/AnnotateDocumentView.vue"),
  },
  {
    path: "/editDocument/:id",
    component: () => import("../views/EditDocumentView.vue"),
  },
  {
    path: "/editArticle/:id",
    component: () => import("../views/EditArticleView.vue"),
  },
  {
    path: "/documents/:id",
    component: () => import("../views/DocumentView.vue"),
  },
  {
    path: "/admin",
    component: () => import("../views/administration/AdminMainView.vue"),
  },
  {
    path: "/admin/documents",
    component: () =>
      import("../views/administration/AdminAllDocumentsView.vue"),
  },
  {
    path: "/admin/documents/:id",
    component: () => import("../views/administration/AdminDocumentView.vue"),
  },
  {
    path: "/admin/documents/create",
    component: () =>
      import("../views/administration/AdminCreateDocumentView.vue"),
  },
  {
    path: "/admin/articles",
    component: () => import("../views/administration/AdminAllArticlesView.vue"),
  },
  {
    path: "/admin/articles/create",
    component: () =>
      import("../views/administration/AdminCreateArticleView.vue"),
  },
  {
    path: "/admin/articles/:id",
    component: () =>
      import("src/views/administration/AdminEditArticleView.vue"),
  },
  {
    path: "/admin/sections",
    component: () => import("../views/administration/AdminAllSectionsView.vue"),
  },
  {
    path: "/admin/sections/create",
    component: () =>
      import("../views/administration/AdminCreateSectionsView.vue"),
  },
  {
    path: "/admin/sections/:id",
    component: () =>
      import("../views/administration/AdminEditSectionsView.vue"),
  },
  {
    path: "/admin/users",
    component: () => import("../views/administration/AdminAllUsersView.vue"),
  },
  {
    path: "/admin/users/create",
    component: () => import("../views/administration/AdminCreateUserView.vue"),
  },
  {
    path: "/admin/users/:id",
    component: () => import("../views/administration/AdminEditUserView.vue"),
  },
  {
    path: "/admin/roles",
    component: () => import("../views/administration/AdminAllRolesView.vue"),
  },
  {
    path: "/admin/roles/create",
    component: () => import("../views/administration/AdminCreateRoleView.vue"),
  },
  {
    path: "/admin/roles/:id",
    component: () => import("../views/administration/AdminEditRoleView.vue"),
  },
  {
    path: "/admin/authorities",
    component: () =>
      import("../views/administration/AdminAllAuthoritiesView.vue"),
  },
  {
    path: "/admin/error_tags",
    component: () =>
      import("../views/administration/AdminAllErrorTagsView.vue"),
  },
  {
    path: "/admin/error_tags/create",
    component: () =>
      import("../views/administration/AdminCreateErrorTagView.vue"),
  },
  {
    path: "/admin/error_tags/:id",
    component: () =>
      import("../views/administration/AdminEditErrorTagView.vue"),
  },
  {
    path: "/admin/domains",
    component: () => import("../views/administration/AdminAllDomainsView.vue"),
  },
  {
    path: "/admin/domains/create",
    component: () =>
      import("../views/administration/AdminCreateDomainView.vue"),
  },
  {
    path: "/admin/domains/:id",
    component: () => import("../views/administration/AdminEditDomainView.vue"),
  },
  {
    path: "/admin/academic_majors",
    component: () =>
      import("../views/administration/AdminAllAcademicMajorView.vue"),
  },
  {
    path: "/admin/academic_majors/create",
    component: () =>
      import("../views/administration/AdminCreateAcademicMajorView.vue"),
  },
  {
    path: "/admin/academic_majors/:id",
    component: () =>
      import("../views/administration/AdminEditAcademicMajorView.vue"),
  },
  {
    path: "/admin/courses",
    component: () => import("../views/administration/AdminAllCoursesView.vue"),
  },
  {
    path: "/admin/courses/create",
    component: () =>
      import("../views/administration/AdminCreateCourseView.vue"),
  },
  {
    path: "/admin/courses/:id",
    component: () => import("../views/administration/AdminEditCourseView.vue"),
  },
  {
    path: "/admin/genres",
    component: () => import("../views/administration/AdminAllGenresView.vue"),
  },
  {
    path: "/admin/genres/create",
    component: () => import("../views/administration/AdminCreateGenreView.vue"),
  },
  {
    path: "/admin/genres/:id",
    component: () => import("../views/administration/AdminEditGenreView.vue"),
  },
  // Error route
  {
    path: "/:catchAll(.*)*",
    //TODO: Create a 404 page
    component: () => import("../views/MainView.vue"),
  },
];

export default routes;
