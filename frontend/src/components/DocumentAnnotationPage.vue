<template>
  <h3></h3>
  <q-page-container v-if="responseSuccess">
    <div>
      <h3 class="text-h6 q-mb-md">Document status</h3>
      <q-select outlined v-model="document.status" :options="options" />
    </div>
    <q-page class="q-pa-md">
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-for="(sentence, i) in document.sentences" :key="i">
          <q-card class="rounded-borders">
            <q-card-section>
              <div class="row q-gutter-md items-center" :id="'a-card-' + i">
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
      options: ["Not annotated", "Annotated", "Checked"],
    };
  },
  watch: {
    "document.status": {
      handler() {
        this.changeStatus();
      },
      deep: true,
    },
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
          this.document.status = this.options[this.document.status];
        } else {
          console.error(response);
          this.responseSuccess = false;
        }
      } catch (error) {
        console.error("Error during login:", error);
        this.responseSuccess = false;
      }
    },
    async changeStatus() {
      try {
        const response = await fetch(
          "http://localhost:8081/documents/" + this.$route.params.id,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              status: this.options.indexOf(this.document.status),
            }),
          }
        );
        if (response.ok) {
          if (
            "Notification" in window &&
            Notification.permission === "granted"
          ) {
            new Notification("Status changed");
          }
        } else {
          console.error(response);
        }
      } catch (error) {
        console.error("Error during login:", error);
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
        recogito.attachTo("a-card-" + i);
        this.recogitoInstances.push(recogito);
      });
    },
  },
  async mounted() {
    if (!this.checkLogin()) {
      this.$router.push("/");
    }
    if (this.responseSuccess) {
      await this.loadDocument();
      await this.loadRecogito();
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
