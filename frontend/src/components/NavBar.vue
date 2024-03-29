<template>
  <q-header class="bg-primary text-white">
    <q-toolbar class="items-center">
      <q-btn flat dense to="/" style="margin-right: 1%">
        <img
          src="../resources/KRUT2.png"
          alt="Website Logo"
          style="height: 40px"
        />
      </q-btn>
      <q-btn
        flat
        dense
        to="/"
        exact
        active-class="text-white"
        class="text-white"
        style="margin-right: 1%"
      >
        Home
      </q-btn>
      <q-btn
        flat
        dense
        to="/news"
        active-class="text-white"
        class="text-white"
        style="margin-right: 1%"
      >
        News
      </q-btn>
      <q-btn
        flat
        dense
        to="/search"
        active-class="text-white"
        class="text-white"
        style="margin-right: 1%"
      >
        Search
      </q-btn>
      <q-btn
        flat
        dense
        to="/statistics"
        active-class="text-white"
        class="text-white"
      >
        Statistics
      </q-btn>
      <q-space />
      <div>
        <q-btn
          flat
          dense
          to="/login"
          active-class="text-white"
          class="text-white"
        >
          Login
        </q-btn>
      </div>
    </q-toolbar>
  </q-header>
</template>

<script>
export default {
  name: "AppHeader",
};
</script>
