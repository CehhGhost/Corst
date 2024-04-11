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
            <q-btn flat dense to="/" style="height: 80px; margin-left: 27%">
              <img src="../../resources/KRUT2.png" alt="Website Logo" />
            </q-btn>
          </div>
          <div
            class="text-center text-white"
            style="font-size: 18px; margin-bottom: 10px"
          >
            {{ name }}
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
            :to="route.path"
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
import { serverAdress } from "../../global/globalVaribles.js";
import { isLogin } from "../../global/globalFunctions.js";

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
        { label: "Authorities", path: "/admin/authorities" },
        { label: "Error Tags", path: "/admin/error_tags" },
        { label: "Genres", path: "/admin/genres" },
        { label: "Domains", path: "/admin/domains" },
        { label: "Academic Majors", path: "/admin/academic_majors" },
        { label: "Courses", path: "/admin/courses" },
      ],

      name: "",
      authorities: [],
    };
  },
  methods: {
    async getName() {
      if (!isLogin()) {
        this.$router.push("/login");
        return;
      }
      const url = serverAdress + "/auth/get_auth_info";
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
      });
      if (response.ok) {
        const data = await response.json();
        this.name = data.name + " " + data.surname;
        this.authorities = data.authorities;
      } else {
        this.name = "Error";
      }
    },
  },

  mounted() {
    this.getName();
  },
};
</script>
