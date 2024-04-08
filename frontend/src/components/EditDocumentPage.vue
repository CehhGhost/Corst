<template>
  <q-page-container class="q-pa-md">
    <q-page class="flex">
      <div v-if="userStatus" class="create-document">
        <h2 class="q-mb-md">Edit Document</h2>
        <q-form @submit="saveDocument" class="q-gutter-md">
          <q-input
            v-model="title"
            outlined
            label="Title"
            dense
            autofocus
            placeholder="Enter title"
            class="q-mb-md"
            style="margin-top: 35px"
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
          <q-select
            label="Gender"
            v-model="authorsGender"
            outlined
            :options="authorsGenders"
          />
          <div class="row">
            <q-select
              outlined
              v-model="genre"
              use-input
              use-chips
              label="Genre"
              input-debounce="0"
              @new-value="createValue"
              :options="filteredGenres"
              @filter="filterGenres"
              style="width: 47%; margin-right: 6%"
            />
            <q-select
              outlined
              v-model="domain"
              use-input
              label="Domain"
              use-chips
              input-debounce="0"
              @new-value="createValue"
              :options="filteredDomains"
              @filter="filterDomains"
              style="width: 47%"
            />
          </div>
          <div class="row">
            <q-select
              outlined
              v-model="authorsCourse"
              use-input
              use-chips
              label="Course"
              input-debounce="0"
              @new-value="createValue"
              :options="filteredAuthorsCourses"
              @filter="filterAuthorsCourses"
              style="width: 47%; margin-right: 6%"
            />
            <q-select
              outlined
              v-model="authorsAcademicMajor"
              use-input
              use-chips
              label="Academic Major"
              input-debounce="0"
              @new-value="createValue"
              :options="filteredAuthorsAcademicMajors"
              @filter="filterAuthorsAcademicMajors"
              style="width: 47%"
            />
          </div>
          <q-btn
            type="submit"
            label="Save Document"
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
  width: 750px;
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
      title: null,
      text: null,

      genre: null,
      domain: null,
      authorsCourse: null,
      authorsAcademicMajor: null,
      genres: [],
      domains: [],
      authorsCourses: [],
      authorsAcademicMajors: [],

      filteredGenres: [],
      filteredDomains: [],
      filteredAuthorsCourses: [],
      filteredAuthorsAcademicMajors: [],

      authorsGender: null,
      authorsGenders: ["Male", "Female", "Unknown"],

      userStatus: false,
      responseSuccess: false,
    };
  },
  methods: {
    async saveDocument() {
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      if (
        !this.title ||
        !this.text ||
        !this.authorsGender ||
        !this.genre ||
        !this.domain ||
        !this.authorsCourse ||
        !this.authorsAcademicMajor
      ) {
        alert("Please fill all fields");
        return;
      }
      const response = await fetch(
        serverAdress + "/documents/update/" + this.$route.params.id,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("corst_token"),
          },
          body: JSON.stringify({
            title: this.title,
            text: this.text,
            genre: this.genre,
            domain: this.domain,
            authorsCourse: this.authorsCourse,
            authorsAcademicMajor: this.authorsAcademicMajor,
            authorsGender: this.authorsGender,
          }),
        }
      );
      if (response.ok) {
        this.$router.push("/documents");
      } else {
        console.error(response);
      }
    },
    async getDocumentInfo() {
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
    async getDocument() {
      try {
        const response = await fetch(
          serverAdress + "/documents/" + this.$route.params.id,
          {
            method: "GET",
          }
        );
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
      this.title = data.title;
      this.text = data.text;
      this.genre = data.genre;
      this.domain = data.domain;
      this.authorsCourse = data.authorsCourse;
      this.authorsAcademicMajor = data.authorsAcademicMajor;
      this.authorsGender =
        data.authorsGender[0].toUpperCase() +
        data.authorsGender.slice(1).toLowerCase();
    },

    parseInfo(data) {
      this.genres = data.genres;
      this.domains = data.domains;
      this.authorsCourses = data.courses;
      this.authorsAcademicMajors = data.academicMajors;
      for (let i = 0; i < this.genres.length; i++) {
        this.filteredGenres.push(this.genres[i]);
      }
      for (let i = 0; i < this.domains.length; i++) {
        this.filteredDomains.push(this.domains[i]);
      }
      for (let i = 0; i < this.authorsCourses.length; i++) {
        this.filteredAuthorsCourses.push(this.authorsCourses[i]);
      }
      for (let i = 0; i < this.authorsAcademicMajors.length; i++) {
        this.filteredAuthorsAcademicMajors.push(this.authorsAcademicMajors[i]);
      }
    },
    async changeDocument() {
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      if (
        !this.title ||
        !this.text ||
        !this.authorsGender ||
        !this.genre ||
        !this.domain ||
        !this.authorsCourse ||
        !this.authorsAcademicMajor
      ) {
        alert("Please fill all fields");
        return;
      }
    },
    createValue(val, done) {
      if (val.length > 0) {
        done(val, "add-unique");
      }
    },
    filterGenres(val, update) {
      update(() => {
        if (val === "") {
          this.filteredGenres = this.genres;
        } else {
          const needle = val.toLowerCase();
          this.filteredGenres = this.genres.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },
    filterDomains(val, update) {
      update(() => {
        if (val === "") {
          this.filteredDomains = this.domains;
        } else {
          const needle = val.toLowerCase();
          this.filteredDomains = this.domains.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },
    filterAuthorsCourses(val, update) {
      update(() => {
        if (val === "") {
          this.filteredAuthorsCourses = this.authorsCourses;
        } else {
          const needle = val.toLowerCase();
          this.filteredAuthorsCourses = this.authorsCourses.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },
    filterAuthorsAcademicMajors(val, update) {
      update(() => {
        if (val === "") {
          this.filteredAuthorsAcademicMajors = this.authorsAcademicMajors;
        } else {
          const needle = val.toLowerCase();
          this.filteredAuthorsAcademicMajors =
            this.authorsAcademicMajors.filter(
              (v) => v.toLowerCase().indexOf(needle) > -1
            );
        }
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
      const document = await this.getDocument();
      if (this.responseSuccess) {
        await this.parseInfo(data);
        await this.parseData(document);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  },
};
</script>
