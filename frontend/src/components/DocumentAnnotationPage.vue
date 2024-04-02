<template>
  <q-page-container>
    <h3></h3>
    <q-page class="q-pa-md">
      <div v-if="responseSuccess">
        <div class="row">
          <h3 class="row text-h6 q-mb-md">Document status</h3>
          <q-select
            outlined
            v-model="documentStatus"
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
import { isLogin } from "../global/globalFunctions.js";
import { Recogito } from "@recogito/recogito-js";
import "@recogito/recogito-js/dist/recogito.min.css";

export default {
  data() {
    return {
      document: this.loadDocument(),
      responseSuccess: true,
      recogitoInstances: [],
      documentStatus: 0,
      options: ["Not annotated", "Annotated", "Checked"],

      userStatus: false,
    };
  },
  watch: {
    documentStatus: {
      handler() {
        this.changeStatus();
      },
      deep: true,
    },
  },
  methods: {
    async loadDocument() {
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
          this.document = data;
          this.documentStatus = this.options[this.document.statusNum];
        } else {
          console.error(response);
        }
      } catch (error) {
        console.error("Error during login:", error);
      }
    },
    async changeStatus() {
      console.log(this.documentStatus);
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      try {
        const response = await fetch(
          serverAdress +
            "/documents/" +
            this.$route.params.id +
            "/set_status/" +
            this.options.indexOf(this.documentStatus),
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
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
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
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
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
    this.userStatus = await isLogin();
    if (!this.userStatus) {
      this.$router.push("/");
    }
    if (this.responseSuccess) {
      await this.loadDocument();
      await this.loadRecogito();
    }
  },
  beforeUnmount() {
    if (this.recogito) {
      this.recogito.destroy();
    }
  },
};
</script>
