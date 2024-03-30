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
        style="margin-right: 1%"
      >
        Statistics
      </q-btn>
      <q-btn flat dense to="/help" active-class="text-white" class="text-white">
        Help
      </q-btn>
      <q-space />
      <div v-if="!CheckLogin()">
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
      <div v-else>
        <q-btn
          flat
          dense
          to="/demo"
          active-class="text-white"
          class="text-white"
        >
          Demo annotation
        </q-btn>
        <q-btn
          flat
          dense
          to="/documents"
          active-class="text-white"
          class="text-white"
        >
          All Documents
        </q-btn>
        <q-btn
          flat
          dense
          active-class="text-white"
          class="text-white"
          @click="Logout()"
        >
          Logout
        </q-btn>
      </div>
    </q-toolbar>
  </q-header>
</template>

<script>
import router from "src/router";

export default {
  name: "AppHeader",
  methods: {
    CheckLogin() {
      if (localStorage.getItem("corst_token") === null) {
        return false;
      } else {
        //TODO Check expired token
        return true;
      }
    },
    Logout() {
      localStorage.removeItem("corst_token");
      router.push("/");
      location.reload();
    },
  },
};
</script>
