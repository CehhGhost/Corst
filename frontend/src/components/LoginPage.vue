<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center">
        <q-card class="q-pa-md" :style="{ maxWidth: cardWidth }">
          <q-card-section class="text-h5">Login</q-card-section>
          <q-card-section>
            <q-form @submit="fakeLogin">
              <q-input
                v-model="username"
                outlined
                label="Username"
                dense
                class="q-mb-md"
                style="font-size: 18px; height: 48px"
              />
              <q-input
                v-model="password"
                outlined
                label="Password"
                type="password"
                dense
                class="q-mb-md"
                style="font-size: 18px; height: 48px"
              />
              <!--TODO Add functionality-->
              <q-btn
                type="submit"
                color="primary"
                label="Login"
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
export default {
  data() {
    return {
      username: "",
      password: "",
      cardWidth: "400px",
    };
  },
  mounted() {
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
    fakeLogin() {
      console.log("Fake login with username:", this.username);
      this.$router.push("/");
    },
    async login() {
      try {
        const formData = new URLSearchParams();
        formData.append("username", this.username);
        formData.append("password", this.password);

        const response = await fetch("http://localhost:8081/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: formData,
        });
        if (response.ok) {
          const data = await response.json();
          console.log("Authentication successful:", data);
        } else {
          console.error("Authentication failed");
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
