const routes = [
  {
    path: "/",
    component: () => import("../views/MainView.vue"),
  },
  {
    path: "/news",
    component: () => import("../views/NewsView.vue"),
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
    component: () => import("../views/AddDocumentView.vue"),
  },
  // Error route
  {
    path: "/:catchAll(.*)*",
    //TODO: Create a 404 page
    component: () => import("../views/MainView.vue"),
  },
];

export default routes;
