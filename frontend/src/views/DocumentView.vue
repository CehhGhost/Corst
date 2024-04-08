<script setup>
import DocumentPage from "../components/DocumentPage.vue";
import NavBar from "../components/NavBar.vue";
import FooterBar from "../components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <DocumentPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
