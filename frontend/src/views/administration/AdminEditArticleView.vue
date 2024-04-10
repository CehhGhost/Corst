<script setup>
import NavBar from "src/components/administration/NavBar.vue";
import SideBar from "src/components/administration/SideBar.vue";
import AdminEditArticle from "src/components/administration/AdminEditArticlePage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminEditArticle />
    </div>
  </q-layout>
</template>
