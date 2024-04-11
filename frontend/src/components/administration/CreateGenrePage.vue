<template>
  <q-page-container class="q-pa-xs">
    <q-page class="flex">
      <div
        v-if="userStatus"
        class="create-document"
        style="width: (100% - 200px); margin-left: 200px; margin-top: 50px"
      >
        <h3 class="q-mb-md" style="margin-bottom: 25px">
          {{ $t("add_genre") }}
        </h3>
        <q-form @submit="addGenre" class="q-gutter-md">
          <q-input outlined v-model="name" :label="$t('name')" />
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

      userStatus: false,
    };
  },
  methods: {
    async addGenre() {
      try {
        const response = await fetch(`${serverAdress}/info/genres/create`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("corst_token")}`,
          },
          body: JSON.stringify({
            name: this.name,
          }),
        });
        if (response.ok) {
          this.$router.push("/admin/genres");
        }
      } catch (error) {
        console.error("Error:", error);
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
