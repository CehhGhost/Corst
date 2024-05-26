<script setup>
import SideBar from "src/components/administration/partials/SideBar.vue";
import NavBar from "src/components/administration/partials/NavBar.vue";
import AdminAllDocumentsPage from "src/components/administration/corpus/document/AllDocumentsPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminAllDocumentsPage />
    </div>
  </q-layout>
</template>
