import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/Main.vue'
import NewsView from '../views/News.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: MainView
    },
    {
      path: '/about',
      name: 'about',
      component: NewsView
    }
  ]
})

export default router
