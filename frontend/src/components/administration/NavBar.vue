<template>
  <q-header class="bg-primary text-white">
    <q-toolbar class="items-center">
      <q-space />
      <div>
        <q-btn flat dense to="/" active-class="text-white" class="text-white">
          {{ $t("home") }}
        </q-btn>
        <q-btn
          flat
          dense
          to="/change_password"
          active-class="text-white"
          class="text-white"
        >
          {{ $t("change_password") }}
        </q-btn>
        <q-btn
          flat
          dense
          active-class="text-white"
          class="text-white"
          @click="logout()"
        >
          {{ $t("logout") }}
        </q-btn>
      </div>
    </q-toolbar>
  </q-header>
</template>

<script>
import router from "src/router";

export default {
  name: "AppHeader",
  data() {
    return {};
  },
  methods: {
    checkLogin() {
      return localStorage.getItem("corst_token") ? true : false;
    },
    logout() {
      localStorage.removeItem("corst_token");
      this.$router.push("/");
      location.reload();
      this.isLogin = false;
    },
    switchLanguage(locale) {
      this.$i18n.locale = locale;
      localStorage.setItem("corst_locale", locale);
    },
  },
  mounted() {
    if (!localStorage.getItem("corst_token")) {
      this.$router.push("/");
    }
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
  },
};
</script>
