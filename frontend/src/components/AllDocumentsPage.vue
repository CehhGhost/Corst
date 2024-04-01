<template>
  <h3></h3>
  <q-page-container>
    <q-page class="q-pa-md">
      <div>
        <q-btn
          push
          icon="add"
          color="primary"
          label="Add Document"
          class="button"
          to="/addDocument"
        />
      </div>
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-for="(document, i) in documents" :key="i">
          <q-card class="rounded-borders">
            <q-card-section>
              <h3 class="text-h6">{{ document.title }}</h3>
              <div class="row q-gutter-md items-center">
                {{ document.text }}
              </div>
              <q-btn
                push
                color="primary"
                label="Annotate"
                class="button"
                :to="'/annotateDocument/' + document.id"
              />
            </q-card-section>
          </q-card>
        </div>
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
        if (response.ok) {
          const data = await response.json();
          this.documents = data;
        } else {
          this.responseSuccess = false;
        }
      } catch (error) {
        console.error("Error:", error);
        this.responseSuccess = false;
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
