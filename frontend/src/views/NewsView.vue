<script setup>
import NewsPage from "../components/NewsPage.vue";
import NavBar from "../components/NavBar.vue";
import FooterBar from "../components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <NewsPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
