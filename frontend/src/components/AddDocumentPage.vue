<template>
  <q-page-container class="q-pa-md">
    <q-page class="flex flex-center">
      <div v-if="isLogin()" class="create-document">
        <h2 class="q-mb-md">Add Document</h2>
        <q-form @submit="addDocument" class="q-gutter-md">
          <q-input
            v-model="title"
            outlined
            label="Title"
            dense
            placeholder="Enter title"
            class="q-mb-md"
          />
          <q-input
            v-model="content"
            outlined
            label="Content"
            type="textarea"
            dense
            placeholder="Enter content"
            class="q-mb-md"
          />
          Author Gender
          <q-select v-model="authorsGender" outlined :options="genders" />
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

<script>
import { serverAdress } from "../global/globalVaribles.js";
import { isLogin } from "../global/globalFunctions.js";

export default {
  data() {
    return {
      title: "",
      content: "",
      authorsGender: "",

      genders: ["Male", "Female", "Unknown"],

      userStatus: false,
    };
  },
  methods: {
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
          text: this.content,
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
  },
};
</script>

<style scoped>
.create-document {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.create-article h2 {
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
