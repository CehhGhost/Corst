<script setup>
import SideBar from "src/components/administration/SideBar.vue";
</script>

<template>
  <q-layout view="lhr Lpr lfr">
    <SideBar />
    <div class="ml-64">
      <router-view />
    </div>
  </q-layout>
</template>
