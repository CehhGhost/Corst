<template>
  <q-page-container>
    <h3></h3>
    <q-page class="q-pa-md" style="max-width: 1000px; margin: 0 auto">
      <div v-if="responseSuccess">
        <div class="row">
          <h3 class="row text-h6 q-mb-md">{{ document.title }}</h3>
        </div>
        <q-card class="rounded-borders q-mb-md">
          <q-card-section class="row items-center">
            <h2>{{ document.text }}</h2>
          </q-card-section>
        </q-card>
      </div>
      <div v-else>
        <h1 class="text-h3">Error</h1>
        <p>Failed to load document</p>
        <q-btn
          push
          color="secondary"
          label="Back"
          class="q-mt-md"
          to="/documents"
        />
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
      document: this.loadDocument(),
      responseSuccess: true,
    };
  },
  methods: {
    async loadDocument() {
      const documentId = this.$route.params.id;
      const url = serverAdress + `/documents/${documentId}`;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.document = data;
        })
        .catch((error) => {
          console.error("Error:", error);
          this.responseSuccess = false;
        });
    },
  },
  async mounted() {
    if (!isLogin()) {
      this.$router.push("/login");
    } else {
      await this.loadDocument();
    }
  },
};
</script>
