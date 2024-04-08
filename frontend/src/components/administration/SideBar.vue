<template>
  <q-page-container>
    <q-drawer
      v-model="drawerModel"
      :content-class="'bg-grey-3'"
      :width="drawerWidth"
      :breakpoint="breakpoint"
      :overlay="true"
      :persistent="persistent"
    >
      <q-list no-border>
        <q-item
          v-for="(route, index) in routes"
          :key="index"
          clickable
          @click="navigateTo(route.path)"
        >
          <q-item-section>
            <q-item-label>{{ route.label }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <!-- Your main content here -->
      <router-view></router-view>
    </q-page-container>
  </q-page-container>
</template>

<script>
export default {
  data() {
    return {
      drawerModel: true,
      drawerWidth: 200,
      breakpoint: 992, // Adjust as needed
      persistent: true, // Sidebar stays open on larger screens
      routes: [
        { label: "Dashboard", path: "/dashboard" },
        { label: "Users", path: "/users" },
        { label: "Settings", path: "/settings" },
        // Add more routes as needed
      ],
    };
  },
  methods: {
    navigateTo(path) {
      this.$router.push(path);
    },
  },
};
</script>

<style scoped>
/* Add any custom styling here */
</style>
