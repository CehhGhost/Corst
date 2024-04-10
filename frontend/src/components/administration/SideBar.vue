<template>
  <q-page-container>
    <q-drawer
      v-model="drawerModel"
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
          :active-class="'bg-grey-2'"
        >
          <q-item-section>
            <q-item-label
              style="color: #18bc9c; font-weight: 500; font-size: 18px"
              >{{ route.label }}</q-item-label
            >
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>
  </q-page-container>
</template>

<script>
export default {
  data() {
    return {
      drawerModel: true,
      drawerWidth: 200,
      breakpoint: 992,
      persistent: true,
      activeIndex: 0,
      routes: [
        { label: "Documents", path: "/admin/documents" },
        { label: "Articles", path: "/admin/articles" },
        { label: "Sections", path: "/admin/sections" },
        { label: "Users", path: "/admin/users" },
        { label: "Roles", path: "/admin/roles" },
        { label: "Permissions", path: "/admin/permissions" },
        { label: "Genres", path: "/admin/genres" },
      ],
    };
  },
  methods: {
    navigateTo(path, index) {
      this.activeIndex = index;
      this.$router.push(path);
    },
  },
};
</script>
