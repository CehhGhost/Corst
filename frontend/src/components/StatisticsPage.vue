<template>
  <div>
    <h1></h1>
    <q-btn
      push
      color="primary"
      label="Show Users"
      class="button"
      @click="showUsers()"
    />
  </div>
</template>

<script>
import { serverAdress } from "src/global/globalVaribles";

export default {
  data() {
    return {
      users: [],
    };
  },
  methods: {
    async showUsers() {
      try {
        const response = await fetch(serverAdress + "/admin/user", {
          method: "GET",
        });
        const users = await response.json();
        this.users = users;
        console.log("Users:", this.users);
      } catch (error) {
        console.error("Error:", error);
      }
    },
  },
  mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
  },
};
</script>
