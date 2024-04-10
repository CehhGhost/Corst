<script setup>
import SideBar from "src/components/administration/SideBar.vue";
import NavBar from "src/components/NavBar.vue";
import FooterBar from "src/components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <SideBar />
    <div class="ml-64">
      <router-view />
    </div>
  </q-layout>
</template>
