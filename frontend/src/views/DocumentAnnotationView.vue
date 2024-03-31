<script setup>
import DocumentAnnotationPage from "../components/DocumentAnnotationPage.vue";
import NavBar from "../components/NavBar.vue";
import FooterBar from "../components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <DocumentAnnotationPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
