<script setup>
import DocumentDemo from "../components/DocumentDemo.vue";
import NavBar from "../components/NavBar.vue";
import FooterBar from "../components/FooterBar.vue";
</script>

<template>
  <q-layout view="lHh LpR lFf">
    <q-header>
      <NavBar />
    </q-header>
    <DocumentDemo />
    <q-footer>
      <FooterBar />
    </q-footer>
  </q-layout>
</template>
