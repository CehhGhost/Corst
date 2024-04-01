<script setup>
import AllArticlesPage from "../components/AllArticlesPage.vue";
import NavBar from "../components/NavBar.vue";
import FooterBar from "../components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <AllArticlesPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
