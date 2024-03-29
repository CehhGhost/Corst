import { createRouter, createWebHistory } from "vue-router";
import routes from "./routes"; // Assuming your routes are defined in a separate file

const router = createRouter({
  history: createWebHistory(process.env.VUE_ROUTER_BASE),
  routes,
});

export default router;
