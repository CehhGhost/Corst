<script setup>
import NavBar from "src/components/administration/partials/NavBar.vue";
import SideBar from "src/components/administration/partials/SideBar.vue";
import AdminEditAcademicMajor from "src/components/administration/info/academicMajor/EditAcademicMajorPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminEditAcademicMajor />
    </div>
  </q-layout>
</template>
