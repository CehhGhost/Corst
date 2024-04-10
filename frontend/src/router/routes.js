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
  // Error route
  {
    path: "/:catchAll(.*)*",
    //TODO: Create a 404 page
    component: () => import("../views/MainView.vue"),
  },
];

export default routes;
