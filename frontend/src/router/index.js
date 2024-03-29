import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/Main.vue'
import NewsView from '../views/News.vue'
import SearchView from '../views/Search.vue'
import LoginView from '../views/Login.vue'
import StatisticsView from '../views/Statistics.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: MainView
    },
    {
      path: '/news',
      name: 'news',
      component: NewsView
    },
    {
      path: '/search',
      name: 'search',
      component: SearchView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView
    }
  ]
})

export default router
