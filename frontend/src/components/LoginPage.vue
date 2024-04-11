<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center">
        <q-card class="q-pa-md" :style="{ maxWidth: cardWidth }">
          <q-card-section class="text-h5">
            <div class="text-h5">
              {{ $t("login") }}
            </div>
          </q-card-section>
          <q-card-section>
            <q-form @submit="login">
              <q-input
                v-model="username"
                outlined
                :label="$t('username')"
                dense
                class="q-mb-md"
                style="font-size: 18px; height: 48px"
              />
              <q-input
                v-model="password"
                outlined
                :label="$t('password')"
                type="password"
                dense
                class="q-mb-md"
                style="font-size: 18px; height: 48px"
              />
              <q-btn
                type="submit"
                color="primary"
                :label="$t('login_button')"
                dense
                class="full-width"
                style="font-size: 18px; height: 40px"
                :disabled="formIsEmpty()"
              />
            </q-form>
          </q-card-section>
        </q-card>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script>
import { serverAdress } from "../global/globalVaribles.js";

export default {
  data() {
    return {
      username: "",
      password: "",
      cardWidth: "400px",
    };
  },
  mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.calculateCardWidth();
    window.addEventListener("resize", this.calculateCardWidth);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.calculateCardWidth);
  },
  methods: {
    formIsEmpty() {
      return this.username === "" || this.password === "";
    },
    async login() {
      try {
        const response = await fetch(serverAdress + "/auth", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password,
          }),
        });
        if (response.ok) {
          const data = await response.json();
          localStorage.setItem("corst_token", data.jwt);
          this.$router.push("/");
        } else {
          this.password = "";
          console.error("Response:", response.body);
          alert("Incorrect username or password. Please try again.");
        }
      } catch (error) {
        console.error("Error during login:", error);
      }
    },
    calculateCardWidth() {
      const screenWidth = window.innerWidth;
      if (screenWidth >= 600) {
        this.cardWidth = "400px";
      } else {
        this.cardWidth = "90%";
      }
    },
  },
};
</script>

<style scoped>
.full-width {
  width: 100%;
}
</style>
