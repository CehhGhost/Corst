<script setup>
import NavBar from "src/components/administration/partials/NavBar.vue";
import SideBar from "src/components/administration/partials/SideBar.vue";
import AdminAllAcademicMajors from "src/components/administration/info/academicMajor/AllAcademicMajorPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminAllAcademicMajors />
    </div>
  </q-layout>
</template>
