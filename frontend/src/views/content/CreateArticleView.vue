<script setup>
import CreateArticle from "../../components/content/CreateArticlePage.vue";
import NavBar from "../../components/partials/NavBar.vue";
import FooterBar from "../../components/partials/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <CreateArticle />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
