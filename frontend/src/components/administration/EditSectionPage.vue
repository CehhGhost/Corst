<template>
  <div
    class="create-article"
    style="width: (100% - 200px); margin-left: 250px; margin-top: 50px"
    v-if="userStatus"
  >
    <h3>
      {{ $t("edit_section") }}
    </h3>
    <q-form @submit="submitForm" class="form">
      <q-input
        filled
        v-model="number"
        type="number"
        label="Number"
        lazy-rules
        :rules="numRules"
      />
      <q-input outlined v-model="headerRus" label="Header in Russian" />
      <q-editor v-model="textRus" placeholder="Text in Russian" />
      <q-input outlined v-model="headerEng" label="Header in English" />
      <q-editor v-model="textEng" placeholder="Text in English" />
      <q-btn
        type="submit"
        :label="$t('submit')"
        color="primary"
        class="submit-btn"
      />
    </q-form>
    <q-btn
      unelevated
      color="negative"
      icon="delete"
      class="button"
      @click="deleteSection()"
      style="margin-top: 10px"
    />
    <h1></h1>
  </div>
</template>

<script>
import { serverAdress } from "../../global/globalVaribles.js";
import { isLogin } from "../../global/globalFunctions.js";

export default {
  setup() {
    return {
      numRules: [
        (val) => (val !== null && val !== "") || "Please type number",
        (val) => val > 0 || "Number should be >0",
      ],
    };
  },
  data() {
    return {
      number: null,
      headerEng: "",
      headerRus: "",
      textRus: "",
      textEng: "",

      userStatus: false,
    };
  },
  methods: {
    async loadSection() {
      const response = await fetch(
        `${serverAdress}/sections/${this.$route.params.id}`,
        {
          type: "GET",
          headers: {
            Authorization: "Bearer " + localStorage.getItem("corst_token"),
          },
        }
      );
      const data = await response.json();
      this.number = data.number;
      this.headerRus = data.headerRus;
      this.headerEng = data.headerEng;
      this.textRus = data.textRus;
      this.textEng = data.textEng;
    },
    async submitForm() {
      const data = {
        number: this.number,
        headerRus: this.headerRus,
        headerEng: this.headerEng,
        textRus: this.textRus,
        textEng: this.textEng,
      };
      const response = await fetch(
        `${serverAdress}/sections/${this.$route.params.id}`,
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
        this.$router.push("/admin/sections");
      }
    },
    async deleteSection() {
      const confirmDelete = confirm(
        "Are you sure you want to delete this section?"
      );
      if (!confirmDelete) {
        return;
      }
      const response = await fetch(
        `${serverAdress}/sections/${this.$route.params.id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("corst_token")}`,
          },
        }
      );
      if (response.ok) {
        this.$router.push("/admin/sections");
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

    await this.loadSection();
  },
};
</script>

<style scoped>
.create-article {
  max-width: 600px;
  margin: 0 auto;
}

.create-article h3 {
  text-align: center;
}

.form {
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-btn {
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 10px;
}
</style>
