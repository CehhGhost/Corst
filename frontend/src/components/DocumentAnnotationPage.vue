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
        // this.changeStatus();
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
          content: "a-card-" + i,
          mode: "pre",
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

<template>
  <q-page-container>
    <h3></h3>
    <q-page class="q-pa-md">
      <div v-if="responseSuccess">
        <h3 class="row text-h6 q-mb-md">Document status</h3>
        <q-select
          outlined
          v-model="document.status"
          :options="options"
          class="q-mb-md"
        />
        <q-card
          v-for="(sentence, i) in document.sentences"
          :key="i"
          class="rounded-borders q-mb-md"
        >
          <q-card-section class="row items-center">
            <div :id="'a-card-' + i">{{ sentence.text }}</div>
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
