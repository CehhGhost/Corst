<script setup>
import SideBar from "src/components/administration/SideBar.vue";
import NavBar from "src/components/administration/NavBar.vue";
import AdminAllDocumentsView from "src/views/administration/AdminAllDocumentsView.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminAllDocumentsView />
    </div>
  </q-layout>
</template>
