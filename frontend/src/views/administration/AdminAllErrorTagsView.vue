<script setup>
import NavBar from "src/components/administration/NavBar.vue";
import SideBar from "src/components/administration/SideBar.vue";
import AdminAllErrorTags from "src/components/administration/AllErrorTagsPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminAllErrorTags />
    </div>
  </q-layout>
</template>
