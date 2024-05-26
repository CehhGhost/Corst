<script setup>
import NavBar from "src/components/administration/partials/NavBar.vue";
import SideBar from "src/components/administration/partials/SideBar.vue";
import AdminCreateGenre from "src/components/administration/info/genre/CreateGenrePage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminCreateGenre />
    </div>
  </q-layout>
</template>
