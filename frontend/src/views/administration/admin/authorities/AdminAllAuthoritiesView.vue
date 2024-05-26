<script setup>
import NavBar from "src/components/administration/partials/NavBar.vue";
import SideBar from "src/components/administration/partials/SideBar.vue";
import AdminAllAuthorities from "src/components/administration/admin/authorities/AllAuthoritiesPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminAllAuthorities />
    </div>
  </q-layout>
</template>
