<template>
  <q-page-container class="q-pa-xs">
    <q-page class="flex">
      <div
        v-if="userStatus"
        class="create-document"
        style="width: (100% - 200px); margin-left: 200px; margin-top: 50px"
      >
        <h3 class="q-mb-md" style="margin-bottom: 25px">
          {{ $t("edit_role") }}
        </h3>
        <q-form @submit="editRole" class="q-gutter-md">
          <q-input outlined v-model="name" :label="$t('title')" />
          <q-select
            label="Authorities"
            v-model="selectedAuthorities"
            outlined
            type="checkbox"
            :options="authorities"
            multiple
          />
          <q-btn
            type="submit"
            :label="$t('submit')"
            color="primary"
            class="q-mt-md"
          />
        </q-form>
      </div>
    </q-page>
  </q-page-container>
</template>

<script>
import { serverAdress } from "../../global/globalVaribles.js";
import { isLogin } from "../../global/globalFunctions.js";

export default {
  data() {
    return {
      name: "",
      selectedAuthorities: [],

      authorities: [],

      userStatus: false,
    };
  },
  methods: {
    async getRole() {
      const response = await fetch(
        `${serverAdress}/roles/${this.$route.params.id}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("corst_token")}`,
          },
        }
      );
      if (response.ok) {
        const data = await response.json();
        return data;
      }
    },
    async getAuthorities() {
      const response = await fetch(`${serverAdress}/authorities`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("corst_token")}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        for (let i = 0; i < data.length; i++) {
          this.authorities.push(data[i].name);
        }
      }
    },
    async editRole() {
      const data = {
        name: this.name,
        authorities: this.selectedAuthorities,
      };
      const response = await fetch(
        `${serverAdress}/roles/${this.$route.params.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("corst_token")}`,
          },
          body: JSON.stringify(data),
        }
      );
      if (response.ok) {
        this.$router.push("/admin/roles");
      }
    },
    parseData(data) {
      this.name = data.name;
      this.selectedAuthorities = [];
      for (let i = 0; i < data.authorities.length; i++) {
        this.selectedAuthorities.push(data.authorities[i].name);
      }
    },
  },
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.userStatus = await isLogin();
    if (!this.userStatus) {
      this.$router.push("/");
    }

    try {
      this.parseData(await this.getRole());
      await this.getAuthorities();
    } catch (error) {
      console.error("Error:", error);
    }
  },
};
</script>

<style scoped>
.create-document {
  width: 750px;
  margin: 0 auto;
  padding: 20px;
}

.create-document h3 {
  text-align: center;
}

.form {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-btn {
  margin-top: 20px;
}
</style>
