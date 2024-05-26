<script setup>
import EditDocumentPage from "src/components/content/EditDocumentPage.vue";
import NavBar from "../../components/partials/NavBar.vue";
import FooterBar from "../../components/partials/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <EditDocumentPage />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
