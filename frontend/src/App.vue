<template>
  <NavBar />
  <router-view />
</template>

<script setup>
import NavBar from "./components/NavBar.vue";
defineOptions({
  name: "App",
});
</script>
