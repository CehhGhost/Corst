<template>
  <h3></h3>
  <q-page-container v-if="responseSuccess">
    <q-page class="q-pa-md">
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-for="(sentence, i) in document.sentences" :key="i">
          <q-card class="rounded-borders" :id="'q-card-' + i">
            <q-card-section>
              <div class="row q-gutter-md items-center">
                {{ sentence.text }}
              </div>
            </q-card-section>
          </q-card>
        </div>
      </div>
    </q-page>
  </q-page-container>
  <q-page-container v-else>
    <q-page class="q-pa-md">
      <div>
        <h1 class="text-h3">Error</h1>
        <p>Failed to load document</p>
        <q-btn
          push
          color="secondary"
          label="Back"
          class="button"
          to="/documents"
        />
      </div>
    </q-page>
  </q-page-container>
</template>

<script>
import { Recogito } from "@recogito/recogito-js";
import "@recogito/recogito-js/dist/recogito.min.css";

export default {
  data() {
    return {
      document: this.loadDocument(),
      responseSuccess: true,
      recogitoInstances: [],
    };
  },

  methods: {
    checkLogin() {
      if (localStorage.getItem("corst_token") == null) {
        return false;
      } else {
        //TODO Check expired token
        return true;
      }
    },
    async loadDocument() {
      try {
        const response = await fetch(
          "http://localhost:8081/documents/" + this.$route.params.id,
          {
            method: "GET",
          }
        );
        if (response.ok) {
          this.responseSuccess = true;
          const data = await response.json();
          this.document = data;
        } else {
          console.error(response);
          this.responseSuccess = false;
        }
      } catch (error) {
        console.error("Error during login:", error);
        this.responseSuccess = false;
      }
    },
    async loadRecogito() {
      this.document.sentences.forEach((sentence, i) => {
        const recogito = new Recogito({
          content: "recogito",
          mode: "annotate",
          selectHandler: (annotation) => {
            console.log("Selected:", annotation);
          },
          createAnnotationHandler: (annotation) => {
            console.log("Created:", annotation);
          },
          updateAnnotationHandler: (annotation) => {
            console.log("Updated:", annotation);
          },
          deleteAnnotationHandler: (annotation) => {
            console.log("Deleted:", annotation);
          },
        });
        recogito.attachTo("q-card-" + i);
        this.recogitoInstances.push(recogito);
      });
    },
  },
  async mounted() {
    if (!this.checkLogin()) {
      this.$router.push("/");
    }
    if (this.responseSuccess) {
      this.loadDocument();
      console.log(this.document);
    }
  },
  beforeUnmount() {
    if (this.recogito) {
      this.recogito.destroy();
    }
  },
};
</script>
