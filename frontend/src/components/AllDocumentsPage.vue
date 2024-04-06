<template>
  <q-page-container>
    <q-page class="q-pa-xs" style="max-width: 1000px; margin: 0 auto">
      <div class="q-gutter-xs">
        <h1></h1>
        <q-btn
          push
          icon="add"
          color="primary"
          label="Add Document"
          class="button"
          to="/addDocument"
        />
      </div>
      <div class="col-lg-4 col-md-6 col-sm-8 q-mt-xs">
        <div v-if="documents.length === 0" class="text-center text-grey-8">
          No documents found.
        </div>
        <q-card
          flat
          bordered
          v-else
          v-for="(document, i) in documents"
          :key="i"
          style="margin-top: 20px"
        >
          <q-card-section>
            <h3 class="text-h6">{{ document.title }}</h3>
            <p class="text-body2">{{ document.text }}</p>
            <q-btn
              push
              color="primary"
              label="Annotate"
              class="button"
              @click="annotateDocument(document.id)"
            />
          </q-card-section>
        </q-card>
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
      documents: [],
      responseSuccess: true,
      userStatus: false,
    };
  },
  methods: {
    async loadAllDocuments() {
      try {
        const response = await fetch(serverAdress + "/documents", {
          method: "GET",
        });
        this.responseSuccess = true;
        if (response.ok) {
          const data = await response.json();
          this.documents = data;
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },
  },
  async mounted() {
    this.userStatus = await isLogin();
    if (this.userStatus) {
      this.loadAllDocuments();
    } else {
      this.$router.push("/");
    }
  },
};
</script>
