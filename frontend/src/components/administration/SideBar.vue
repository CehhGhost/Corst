<template>
  <q-page-container>
    <q-drawer
      v-model="drawerModel"
      :width="drawerWidth"
      :breakpoint="breakpoint"
      :overlay="true"
      :persistent="persistent"
    >
      <q-banner class="absolute-top bg-primary" style="height: 130px">
        <div
          class="absolute-bottom bg-transparent d-flex flex-column justify-center items-center"
        >
          <div class="d-flex justify-center w-100">
            <q-btn flat dense to="/" style="height: 80px; margin-left: 25%">
              <img src="../../resources/KRUT2.png" alt="Website Logo" />
            </q-btn>
          </div>
          <div
            class="text-center text-white"
            style="font-size: 18px; margin-bottom: 10px"
          >
            Matvey Tataurovskiy
          </div>
        </div>
      </q-banner>

      <q-scroll-area
        style="
          height: calc(100% - 130px);
          margin-top: 130px;
          border-right: 1px solid #ccc;
        "
      >
        <q-list>
          <q-item
            v-for="(route, index) in routes"
            :key="index"
            clickable
            @click="navigateTo(route.path)"
            :active-class="'bg-grey-2'"
          >
            <q-item-section>
              <q-item-label
                style="color: #18bc9c; font-weight: 350; font-size: 18px"
                >{{ route.label }}</q-item-label
              >
            </q-item-section>
          </q-item>
        </q-list>
      </q-scroll-area>
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
