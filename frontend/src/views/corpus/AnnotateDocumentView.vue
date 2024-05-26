<script setup>
import DocumentAnnotationPage from "../../components/corpus/AnnotateDocumentPage.vue";
import NavBar from "../../components/partials/NavBar.vue";
import FooterBar from "../../components/partials/FooterBar.vue";
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
