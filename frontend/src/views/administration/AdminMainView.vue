<script setup>
import SideBar from "src/components/administration/partials/SideBar.vue";
import NavBar from "src/components/administration/partials/NavBar.vue";
import AdminMainPage from "src/components/administration/AdminMainPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminMainPage />
    </div>
  </q-layout>
</template>
