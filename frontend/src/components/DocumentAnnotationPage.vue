<template>
  <q-page-container>
    <h3></h3>
    <q-page class="q-pa-md">
      <div v-if="responseSuccess">
        <div class="row">
          <h3 class="row text-h6 q-mb-md">Document status</h3>
          <q-select
            outlined
            v-model="document.status"
            :options="options"
            class="q-mb-md"
            style="width: 170px"
          />
        </div>
        <q-card
          v-for="(sentence, i) in document.sentences"
          :key="i"
          class="rounded-borders q-mb-md"
        >
          <q-card-section class="row items-center">
            <div :id="'a-card-' + sentence.id">{{ sentence.text }}</div>
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
          serverAdress + "/documents/" + this.$route.params.id,
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
          serverAdress +
            "/documents/" +
            this.$route.params.id +
            "/set_status/" +
            this.options.indexOf(this.document.status),
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
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
        console.error("Error:", error);
      }
    },
    async loadRecogito() {
      this.document.sentences.forEach((sentence, i) => {
        const recogito = new Recogito({
          content: "a-card-" + sentence.id,
          mode: "pre",
        });
        recogito.on("createAnnotation", (annotation) => {
          console.log(annotation);
          //this.sendAnnotation(annotation, sentence.id);
        });
        recogito.on("updateAnnotation", (annotation) => {
          console.log(annotation);
          //this.updateAnnotation(annotation, sentence.id);
        });
        recogito.on("deleteAnnotation", (annotation) => {
          //this.deleteAnnotation(annotation, sentence.id);
        });
        this.recogitoInstances.push(recogito);
      });
    },
    async sendAnnotation(annotation, sentenceId) {
      try {
        const response = await fetch(
          serverAdress + "/documents/" + this.$route.params.id,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              sentenceId: sentenceId,
              annotation: annotation,
            }),
          }
        );
        if (!response.ok) {
          console.error(response);
        }
      } catch (error) {
        console.error("Error during login:", error);
      }
    },
    async updateAnnotation(annotation, sentenceId) {
      try {
        const response = await fetch(
          serverAdress + "/documents/" + this.$route.params.id,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              sentenceId: sentenceId,
              annotation: annotation,
            }),
          }
        );
        if (!response.ok) {
          console.error(response);
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },
    async deleteAnnotation(annotation, sentenceId) {
      try {
        const response = await fetch(
          serverAdress + "/documents/" + this.$route.params.id,
          {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              sentenceId: sentenceId,
              annotation: annotation,
            }),
          }
        );
        if (!response.ok) {
          console.error(response);
        }
      } catch (error) {
        console.error("Error:", error);
      }
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
