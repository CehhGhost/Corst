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
export default {
  data() {
    return {
      users: [],
    };
  },
  methods: {
    async showUsers() {
      try {
        const response = await fetch("http://localhost:8081/admin/user", {
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
};
</script>
