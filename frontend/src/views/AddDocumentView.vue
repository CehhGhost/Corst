<script setup>
import AddDocumentPage from "src/components/AddDocumentPage.vue";
import NavBar from "src/components/NavBar.vue";
import FooterBar from "src/components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <AddDocumentPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
