<script setup>
import NavBar from "src/components/administration/NavBar.vue";
import SideBar from "src/components/administration/SideBar.vue";
import AdminCreateAcademicMajor from "src/components/administration/CreateAcademicMajorPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminCreateAcademicMajor />
    </div>
  </q-layout>
</template>
