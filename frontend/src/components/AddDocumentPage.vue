<template>
  <q-page-container class="q-pa-md">
    <q-page class="flex flex-center">
      <div v-if="isLogin()" class="q-gutter-md">
        <h1 class="text-h3 q-mb-md">Add Document</h1>
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
export default {
  data() {
    return {
      title: "",
      content: "",
    };
  },
  methods: {
    isLogin() {
      if (localStorage.getItem("corst_token") == null) {
        return false;
      } else {
        //TODO Check expired token
        return true;
      }
    },
    addDocument() {
      fetch("http://localhost:8081/documents/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
        body: JSON.stringify({
          title: this.title,
          text: this.content,
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
  mounted() {
    console.log(this.isLogin());
    if (!this.isLogin()) {
      console.log("Not logged in");
      this.$router.push("/");
    }
  },
};
</script>
