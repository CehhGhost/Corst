<template>
  <q-page-container class="q-pa-xs">
    <q-page class="flex">
      <div
        v-if="userStatus"
        class="create-document"
        style="width: (100% - 200px); margin-left: 200px; margin-top: 50px"
      >
        <h3 class="q-mb-md">
          {{ $t("add_user") }}
        </h3>
        <q-form @submit="addUser" class="q-gutter-md">
          <div class="row">
            <q-input
              outlined
              v-model="name"
              :label="$t('name')"
              style="width: 47%; margin-right: 6%"
            />
            <q-input
              outlined
              v-model="surname"
              :label="$t('surname')"
              input-debounce="0"
              style="width: 47%"
            />
          </div>
          <div class="row">
            <q-input
              outlined
              v-model="username"
              :label="$t('username')"
              style="width: 47%; margin-right: 6%"
            />
            <q-input
              outlined
              v-model="password"
              type="password"
              :label="$t('password')"
              style="width: 47%"
            />
          </div>
          <q-select
            :label="$t('role')"
            v-model="role"
            outlined
            :options="roles"
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
      surname: "",
      username: "",
      password: "",

      roles: [],
    };
  },
  methods: {
    async getRoles() {
      try {
        const response = await fetch(serverAdress + "/info/document", {
          method: "GET",
        });
        this.responseSuccess = response.ok;
        if (response.ok) {
          const data = await response.json();
          return data;
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },
    async addUser() {
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      if (
        this.name == "" ||
        this.surname == "" ||
        this.username == "" ||
        this.password == "" ||
        this.role == ""
      ) {
        if (this.$i18n.locale == "ru") alert("Пожалуйста, заполните все поля");
        else alert("Please fill all fields");
        return;
      }
      if (this.$i18n.locale == "ru") {
        this.authorsGender =
          this.authorsGenders[
            this.authorsGendersLoc.indexOf(this.authorsGender)
          ];
      }
      await fetch(serverAdress + "/documents/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
        body: JSON.stringify({
          title: this.title,
          text: this.text.replace(/"/g, "'"),
          authorsGender: this.authorsGender,
          genre: this.genre,
          domain: this.domain,
          authorsCourse: this.authorsCourse,
          authorsAcademicMajor: this.authorsAcademicMajor,
        }),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push("/admin/documents");
          } else {
            console.error(response);
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
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
      await this.getRoles();
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
