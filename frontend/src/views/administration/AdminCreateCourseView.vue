<script setup>
import NavBar from "src/components/administration/NavBar.vue";
import SideBar from "src/components/administration/SideBar.vue";
import AdminCreateCourse from "src/components/administration/CreateCoursePage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminCreateCourse />
    </div>
  </q-layout>
</template>
