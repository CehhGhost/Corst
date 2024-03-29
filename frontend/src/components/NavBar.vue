<template>
  <q-layout>
    <q-header elevated class="bg-secondary text-white">
      <q-toolbar class="row items-center">
        <q-btn flat dense to="/">
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
        >
          Home
        </q-btn>
        <q-btn
          flat
          dense
          to="/news"
          active-class="text-white"
          class="text-white"
        >
          News
        </q-btn>
        <q-btn
          flat
          dense
          to="/search"
          active-class="text-white"
          class="text-white"
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
  </q-layout>
</template>

<script>
export default {
  name: "AppHeader",
};
</script>
