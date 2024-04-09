<template>
  <q-page-container>
    <h3></h3>
    <q-page class="q-pa-xs" style="max-width: 1200px; margin: 0 auto">
      <div v-if="responseSuccess && document != null">
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
                  documentAdditionalInformation.statuses[document.statusNum]
                }}</span>
              </div>
            </div>
            <div class="info-column">
              <div class="info-item">
                <span class="info-label">Gender:</span>
                <span class="info-value">{{ document.authorsGender[0] }}</span>
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
        <div class="row-auto" style="margin-bottom: 15px">
          <q-btn
            unelevated
            color="primary"
            label="Annotate"
            class="button"
            :to="'/annotateDocument/' + document.id"
          />
          <q-btn
            no-caps
            unelevated
            color="secondary"
            icon="edit"
            label="Edit"
            class="button"
            :to="'/editDocument/' + document.id"
            style="margin-left: 10px"
          />
        </div>
        <q-card flat bordered class="rounded-borders q-mb-xs">
          <q-card-section class="row items-center">
            <span class="info-value" id="main-text" v-if="document != null">
              {{ document.text }}
            </span>
          </q-card-section>
        </q-card>
      </div>
      <div v-else>
        <h1 class="text-h3">Error</h1>
        <p>Failed to load document</p>
        <q-btn
          push
          color="secondary"
          label="Go to all documents"
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
      document: null,
      responseSuccess: true,

      documentAdditionalInformation: {
        statuses: ["Not annotated", "Annotated", "Checked"],
      },
    };
  },
  methods: {
    async loadDocument() {
      const documentId = this.$route.params.id;
      const url = serverAdress + `/documents/${documentId}`;
      await fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.document = data;
        })
        .catch((error) => {
          console.error("Error:", error);
          this.responseSuccess = false;
          this.document = "error";
        });
    },
    async loadRecogito() {
      const recogito = new Recogito({
        content: "main-text",
        readOnly: true,
      });
      recogito.loadAnnotations(
        serverAdress + "/annotations/get_by_document/" + this.document.id
      );
    },
  },
  async mounted() {
    if (!isLogin()) {
      this.$router.push("/login");
    } else {
      await this.loadDocument();
      if (this.document != null) {
        console.log(this.document);
        this.loadRecogito();
      }
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
