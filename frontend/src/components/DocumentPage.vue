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
                <span class="info-label"> {{ $t("created_at") }}: </span>
                <span class="info-value">{{
                  new Date(document.createdAt).toLocaleString()
                }}</span>
              </div>
              <div class="info-item">
                <span class="info-label"> {{ $t("genre") }}: </span>
                <span class="info-value">{{ document.genre }}</span>
              </div>
              <div class="info-item">
                <span class="info-label"> {{ $t("owner") }}: </span>
                <span class="info-value">{{ document.ownerUsername }}</span>
              </div>
              <div class="info-item">
                <span class="info-label"> {{ $t("status") }}: </span>
                <span class="info-value">{{
                  documentAdditionalInformation.statuses[document.statusNum]
                }}</span>
              </div>
            </div>
            <div class="info-column">
              <div class="info-item">
                <span class="info-label"> {{ $t("gender") }}: </span>
                <span class="info-value">{{ document.authorsGender[0] }}</span>
              </div>
              <div class="info-item">
                <span class="info-label"> {{ $t("authors_course") }}: </span>
                <span class="info-value">{{ document.authorsCourse }}</span>
              </div>
              <div class="info-item">
                <span class="info-label"> {{ $t("domain") }}: </span>
                <span class="info-value">{{ document.domain }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">
                  {{ $t("authors_academic_major") }}:
                </span>
                <span class="info-value">{{
                  document.authorsAcademicMajor
                }}</span>
              </div>
            </div>
          </q-card-section>
        </q-card>
        <div class="row-auto" style="margin-bottom: 15px">
          <q-btn
            v-if="canAnnotate"
            unelevated
            color="primary"
            :label="$t('annotate')"
            class="button"
            :to="'/annotateDocument/' + document.id"
          />
          <q-btn
            v-if="canEdit"
            no-caps
            unelevated
            color="secondary"
            :label="$t('edit')"
            class="button"
            :to="'/editDocument/' + document.id"
            style="margin-left: 10px"
          />
        </div>
        <q-card flat bordered class="rounded-borders q-mb-xs">
          <q-card-section class="row items-center">
            <span
              class="info-value"
              id="main-text"
              v-if="document"
              v-html="formatText(document.text)"
              style="white-space: pre-wrap"
            ></span>
          </q-card-section>
        </q-card>
      </div>
      <div v-else>
        <div class="text-center text-grey-8">
          {{ $t("loading") }}
          <q-spinner color="primary" size="3em" :thickness="2" />
        </div>
      </div>
    </q-page>
  </q-page-container>
</template>

<script>
import { serverAdress } from "../global/globalVaribles.js";
import { getAuthorities } from "../global/globalFunctions.js";
import { Recogito } from "@recogito/recogito-js";
import "@recogito/recogito-js/dist/recogito.min.css";

export default {
  data() {
    return {
      authorities: [],
      document: null,
      responseSuccess: true,
      userStatus: false,

      documentAdditionalInformation: {
        statuses: ["Not annotated", "Annotated", "Checked"],
      },
    };
  },
  computed: {
    canCreate() {
      return this.authorities.some(
        (auth) => auth.authority === "CREATE_DOCUMENTS"
      );
    },
    canEdit() {
      return this.authorities.some(
        (auth) => auth.authority === "UPDATE_DELETE_ALLDOCUMENTS"
      );
    },
    canAnnotate() {
      return this.authorities.some(
        (auth) => auth.authority === "ANNOTATE_ALLDOCUMENTS"
      );
    },
  },
  methods: {
    async loadDocument() {
      const documentId = this.$route.params.id;
      const url = serverAdress + `/documents/${documentId}`;
      await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
      })
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
    formatText(text) {
      if (text) {
        return text.replace(/\n/g, "&#8203;<br>");
      }
      return "";
    },
  },
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.authorities = getAuthorities();
    this.userStatus =
      this.authorities.some(
        (auth) => auth.authority === "SEE_READ_ALLDOCUMENTS"
      ) ||
      this.authorities.some((auth) => auth.authority === "CREATE_DOCUMENTS") ||
      this.authorities.some(
        (auth) => auth.authority === "UPDATE_DELETE_ALLDOCUMENTS"
      ) ||
      this.authorities.some(
        (auth) => auth.authority === "ANNOTATE_ALLDOCUMENTS"
      );
    if (!this.authorities) {
      this.$router.push("/login");
    } else {
      await this.loadDocument();
      if (this.document != null) {
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
  white-space: pre-wrap;
}
</style>
