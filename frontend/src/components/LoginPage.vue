<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center">
        <q-card class="q-pa-md" style="max-width: 400px">
          <q-card-section class="text-h6">Login</q-card-section>
          <q-card-section>
            <q-form @submit="login">
              <q-input
                v-model="username"
                outlined
                label="Username"
                dense
                class="q-mb-md"
              />
              <q-input
                v-model="password"
                outlined
                label="Password"
                type="password"
                dense
                class="q-mb-md"
              />
              <!--TODO Add functionality-->
              <q-btn
                type="submit"
                color="primary"
                label="Login"
                dense
                to="/"
                class="full-width"
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
    };
  },
  methods: {
    async login() {
      try {
        const response = await fetch("http://localhost:8080/login", {
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
          console.log("Authentication successful:", data);
        } else {
          console.error("Authentication failed");
        }
      } catch (error) {
        console.error("Error during login:", error);
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
