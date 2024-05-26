<script setup>
import CreateDocumentPage from "src/components/corpus/CreateDocumentPage.vue";
import NavBar from "src/components/partials/NavBar.vue";
import FooterBar from "src/components/partials/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <CreateDocumentPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
