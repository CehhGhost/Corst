<script setup>
import NavBar from "src/components/administration/partials/NavBar.vue";
import SideBar from "src/components/administration/partials/SideBar.vue";
import AdminAllUsers from "src/components/administration/admin/user/AllUsersPage.vue";
</script>

<template>
  <q-layout view="lHh Lpr lff">
    <q-header>
      <NavBar />
    </q-header>
    <SideBar />
    <div>
      <AdminAllUsers />
    </div>
  </q-layout>
</template>
