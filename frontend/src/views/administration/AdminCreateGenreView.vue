<script setup>
import NavBar from "src/components/administration/NavBar.vue";
import SideBar from "src/components/administration/SideBar.vue";
import AdminCreateGenre from "src/components/administration/CreateGenrePage.vue";
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
