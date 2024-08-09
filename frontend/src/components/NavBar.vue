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
        {{ $t("home") }}
      </q-btn>
      <q-btn
        flat
        dense
        to="/news"
        active-class="text-white"
        class="text-white"
        style="margin-right: 1%"
      >
        {{ $t("news") }}
      </q-btn>
      <q-btn
        flat
        dense
        to="/search"
        active-class="text-white"
        class="text-white"
        style="margin-right: 1%"
      >
        {{ $t("search") }}
      </q-btn>
      <q-btn
        flat
        dense
        to="/statistics"
        active-class="text-white"
        class="text-white"
        style="margin-right: 1%"
      >
        {{ $t("statistics") }}
      </q-btn>
      <q-btn flat dense to="/help" active-class="text-white" class="text-white">
        {{ $t("help") }}
      </q-btn>
      <q-space />
      <q-btn
        v-if="$i18n.locale === 'ru'"
        flat
        dense
        label="RU"
        active-class="text-white"
        class="text-white"
        @click="switchLanguage('en')"
      ></q-btn>
      <q-btn
        v-else
        flat
        dense
        label="EN"
        active-class="text-white"
        class="text-white"
        @click="switchLanguage('ru')"
      />
      <div v-if="!checkLogin()">
        <q-btn
          flat
          dense
          to="/login"
          active-class="text-white"
          class="text-white"
        >
          {{ $t("login_button") }}
        </q-btn>
      </div>
      <div v-else>
        <q-btn
          v-if="canAddDocument"
          flat
          dense
          to="/addDocument"
          active-class="text-white"
          class="text-white"
        >
          {{ $t("add_document") }}
        </q-btn>
        <q-btn
          v-if="canSeeDocuments"
          flat
          dense
          to="/documents"
          active-class="text-white"
          class="text-white"
        >
          {{ $t("all_documents") }}
        </q-btn>
        <q-btn
          v-if="canAdministrate"
          flat
          dense
          active-class="text-white"
          class="text-white"
          to="/admin/documents"
        >
          {{ $t("administration") }}
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
import { getAuthorities } from "src/global/globalFunctions";

export default {
  name: "AppHeader",
  data() {
    return {
      authorities: [],
    };
  },
  computed: {
    canAddDocument() {
      return this.authorities.some(
        (auth) => auth.authority === "CREATE_DOCUMENTS"
      );
    },
    canSeeDocuments() {
      return (
        this.authorities.some(
          (auth) => auth.authority === "SEE_READ_ALLDOCUMENTS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CREATE_DOCUMENTS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "UPDATE_DELETE_ALLDOCUMENTS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "ANNOTATE_ALLDOCUMENTS"
        )
      );
    },
    canAdministrate() {
      return (
        this.authorities.some(
          (auth) => auth.authority === "CREATE_UPDATE_DELETE_SECTIONS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CREATE_UPDATE_DELETE_ARTICLES"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CREATE_DOCUMENTS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "UPDATE_DELETE_ALLDOCUMENTS"
        ) ||
        this.authorities.some((auth) => auth.authority === "CREATE_INFO") ||
        this.authorities.some(
          (auth) => auth.authority === "UPDATE_DELETE_INFO"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CREATE_UPDATE_DELETE_ERRORTAGS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CREATE_UPDATE_DELETE_ROLES"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CREATE_UPDATE_DELETE_USERS"
        )
      );
    },
  },
  methods: {
    checkLogin() {
      return localStorage.getItem("corst_token") ? true : false;
    },
    logout() {
      localStorage.removeItem("corst_token");
      router.push("/");
      location.reload();
      this.isLogin = false;
    },
    switchLanguage(locale) {
      this.$i18n.locale = locale;
      localStorage.setItem("corst_locale", locale);
    },
  },
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.authorities = await getAuthorities();
  },
};
</script>
