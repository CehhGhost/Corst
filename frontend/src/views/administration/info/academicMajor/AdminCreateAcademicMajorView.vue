<script setup>
import NavBar from "src/components/administration/partials/NavBar.vue";
import SideBar from "src/components/administration/partials/SideBar.vue";
import AdminCreateAcademicMajor from "src/components/administration/info/academicMajor/CreateAcademicMajorPage.vue";
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
