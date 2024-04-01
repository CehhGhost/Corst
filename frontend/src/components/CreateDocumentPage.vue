<template>
  <q-page-container class="q-pa-md">
    <q-page class="flex">
      <div v-if="userStatus" class="create-document">
        <h2 class="q-mb-md">Add Document</h2>
        <q-form @submit="addDocument" class="q-gutter-md">
          <q-input
            v-model="title"
            outlined
            label="Title"
            dense
            autofocus
            placeholder="Enter title"
            class="q-mb-md"
          />
          <q-input
            v-model="text"
            outlined
            label="Content"
            type="textarea"
            dense
            placeholder="Enter content"
            class="q-mb-md"
          />
          Author Gender
          <q-select
            v-model="authorsGender"
            outlined
            :options="authorsGenders"
          />
          <q-select
            filled
            v-model="model"
            use-input
            use-chips
            input-debounce="0"
            @new-value="createValue"
            :options="filterOptions"
            @filter="filterFn"
            style="width: 250px"
          />
          <q-btn
            type="submit"
            label="Add Document"
            color="primary"
            class="q-mt-md"
          />
        </q-form>
      </div>
    </q-page>
  </q-page-container>
</template>

<style scoped>
.create-document {
  width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.create-document h2 {
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

<script>
import { serverAdress } from "../global/globalVaribles.js";
import { isLogin } from "../global/globalFunctions.js";

export default {
  data() {
    return {
      title: "",
      text: "",

      genre: "",
      domain: "",
      authorsCourse: "",
      authorsAcademicMajor: "",
      genres: [],
      domains: [],
      authorsCourses: [],
      authorsAcademicMajors: [],

      authorsGender: "",
      authorsGenders: ["Male", "Female", "Unknown"],

      userStatus: false,
      responseSuccess: false,
    };
  },
  methods: {
    async getDocumentInfo() {
      try {
        const response = await fetch(serverAdress + "/documents", {
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
    parseData(data) {
      this.genres = data.genres;
      this.domains = data.domains;
      this.authorsCourses = data.courses;
      this.authorsAcademicMajors = data.academicMajors;
    },
    async addDocument() {
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      await fetch(serverAdress + "/documents/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
        body: JSON.stringify({
          title: this.title,
          text: this.text,
          authorsGender: this.authorsGender.toUpperCase(),
        }),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push("/documents");
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
    this.userStatus = await isLogin();
    if (!this.userStatus) {
      this.$router.push("/");
    }

    try {
      const data = await this.getDocumentInfo();
      if (this.responseSuccess) {
        this.parseData(data);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  },
};
</script>
