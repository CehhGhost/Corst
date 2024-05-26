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
                  options[document.statusNum]
                }}</span>
              </div>
            </div>
            <div class="info-column">
              <div class="info-item">
                <span class="info-label"> {{ $t("gender") }}: </span>
                <span class="info-value">{{ document.authorsGender }}</span>
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
        <div class="row items-center">
          <span class="text-h6" style="font-weight: bold">
            {{ $t("document_status") }}:
          </span>
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
          <q-card-section class="row">
            <q-expansion-item expand-icon-toggle>
              <template v-slot:header>
                <div :id="'a-card-' + sentence.id">{{ sentence.text }}</div>
              </template>
              <q-card-section class="row items-center">
                <q-chip
                  v-for="(token, i) in sentence.tokens"
                  :key="i"
                  :label="token.text"
                >
                  <q-tooltip
                    anchor="top middle"
                    self="bottom middle"
                    :offset="[10, 10]"
                  >
                    <div>
                      <div>
                        <span class="tooltip-header">Lemma: </span>
                        <span class="tooltip-info">{{ token.lemma }}</span>
                      </div>
                      <div>
                        <span class="tooltip-header">POS: </span>
                        <span class="tooltip-info">{{ token.pos }}</span>
                      </div>
                      <div v-for="(attr, i) in token.attrs" :key="i">
                        <div>
                          <span class="tooltip-header">{{ i }}: </span>
                          <span class="tooltip-info">{{ attr }}</span>
                        </div>
                      </div>
                    </div>
                  </q-tooltip>
                </q-chip>
              </q-card-section>
            </q-expansion-item>
          </q-card-section>
        </q-card>
      </div>
      <div v-else>
        <h1 class="text-h3">
          {{ $t("error") }}
        </h1>
        <p>
          {{ $t("error_loading_document") }}
        </p>
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
import { serverAdress } from "../../global/globalVaribles.js";
import { isLogin } from "../../global/globalFunctions.js";
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
      options: this.statuses(),
      userStatus: false,

      tags: [],
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
      let widgets = [];
      widgets.push("COMMENT");
      widgets.push({
        widget: "TAG",
        vocabulary: this.tags,
      });
      this.document.sentences.forEach((sentence, i) => {
        const recogito = new Recogito({
          allowEmpty: false,
          content: "a-card-" + sentence.id,
          mode: "pre",
          locale: "ru",
          widgets: widgets,
        });
        recogito.on("createAnnotation", (annotation) => {
          this.sendAnnotation(annotation, sentence.id);
        });
        recogito.on("updateAnnotation", (annotation) => {
          this.updateAnnotation(annotation, sentence.id);
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
        const response = await fetch(
          serverAdress + "/annotations/" + annotation.id.slice(1),
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              sentenceId: sentenceId,
              info: annotation,
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
          serverAdress + "/annotations/" + annotation.id.slice(1),
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

    async getAllErrorTags() {
      try {
        const response = await fetch(serverAdress + "/info/error_tags/", {
          method: "GET",
        });
        if (response.ok) {
          const data = await response.json();
          return data;
        }
      } catch (error) {
        console.error(error);
      }
    },

    parseTags(data) {
      const tags = [];
      for (let i = 0; i < data.length; i++) {
        tags.push(data[i].name);
      }
      return tags;
    },

    statuses() {
      return this.$i18n.locale === "ru"
        ? ["Не аннотирован", "Аннотирован", "Проверен"]
        : ["Not annotated", "Annotated", "Checked"];
    },
  },
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.userStatus = await isLogin();
    if (!this.userStatus) {
      this.$router.push("/");
    }
    if (this.responseSuccess) {
      await this.loadDocument();
      this.loadingComplete = true;
      this.tags = this.parseTags(await this.getAllErrorTags());
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

.tooltip-header {
  font-weight: bold;
  font-size: 12px;
}

.tooltip-info {
  font-weight: normal;
  font-size: 12px;
}
</style>
