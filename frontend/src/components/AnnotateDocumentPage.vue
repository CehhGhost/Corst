<template>
  <q-page-container>
    <h3></h3>
    <q-page class="q-pa-xs" style="max-width: 1000px; margin: 0 auto">
      <div v-if="responseSuccess">
        <h3 class="row q-mb-md">{{ document.title }}</h3>
        <q-card flat style="width: fit-content">
          <q-card-section class="item-info">
            <div class="column">
              <div class="info-item">
                <span class="info-label">Created:</span>
                <span class="info-value">{{
                  new Date(document.createdAt).toLocaleString()
                }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Genre:</span>
                <span class="info-value">{{ document.genre }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Owner:</span>
                <span class="info-value">{{ document.ownerUsername }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Status:</span>
                <span class="info-value">{{
                  options[document.statusNum]
                }}</span>
              </div>
            </div>
            <div class="info-column">
              <div class="info-item">
                <span class="info-label">Gender:</span>
                <span class="info-value">{{ document.authorsGender }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Course:</span>
                <span class="info-value">{{ document.authorsCourse }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Domain:</span>
                <span class="info-value">{{ document.domain }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Academic Major:</span>
                <span class="info-value">{{
                  document.authorsAcademicMajor
                }}</span>
              </div>
            </div>
          </q-card-section>
        </q-card>
        <div class="row items-center">
          <span class="text-h6">Document status</span>
          <q-select
            outlined
            v-model="documentStatus"
            :options="options"
            class="q-mb-md"
            style="width: 170px; margin-left: 10px"
          />
        </div>
        <q-card
          flat
          bordered
          v-for="(sentence, i) in document.sentences"
          :key="i"
          class="rounded-borders q-mb-md"
        >
          <q-card-section class="row items-center">
            <div class="row" :id="'a-card-' + sentence.id">
              <div v-for="(token, i) in sentence.tokens" :key="i">
                <span v-if="token.pos == 'PUNCT'">{{ token.text }}</span>
                <span v-else style="margin-left: 3px">{{ token.text }}</span>
              </div>
            </div>
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
      loadingComplete: false,
      recogitoInstances: [],
      documentStatus: "Not annotated",
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
          console.log(data);
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
            method: "PATCH",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        if (response.ok) {
          // TODO Add Notifier
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
          this.sendAnnotation(annotation, sentence.id);
        });
        recogito.on("updateAnnotation", (annotation) => {
          console.log("update");
          console.log(annotation);
          //this.updateAnnotation(annotation, sentence.id);
        });
        recogito.on("deleteAnnotation", (annotation) => {
          this.deleteAnnotation(annotation, sentence.id);
        });
        recogito.loadAnnotations(
          serverAdress + "/annotations/get_by_sentence/" + sentence.id
        );
        this.recogitoInstances.push(recogito);
      });
    },
    async sendAnnotation(annotation, sentenceId) {
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      try {
        const response = await fetch(serverAdress + "/annotations/create", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("corst_token"),
          },
          body: JSON.stringify({
            sentenceId: sentenceId,
            info: annotation,
          }),
        });
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
        const response = await fetch(serverAdress + "/annotations/", {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            sentenceId: sentenceId,
            annotation: annotation,
          }),
        });
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
          serverAdress + "/documents/" + sentenceId,
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
      this.loadingComplete = true;
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

<style>
.item-info {
  display: flex;
  flex-direction: row;
  margin-bottom: 15px;
}

.info-column {
  margin-left: 50px;
}

.info-item {
  display: flex;
  margin-bottom: 5px;
}

.info-label {
  font-weight: bold;
  margin-right: 5px;
  font-size: 16px;
}

.info-value {
  font-size: 16px;
  white-space: pre-line;
}
</style>
