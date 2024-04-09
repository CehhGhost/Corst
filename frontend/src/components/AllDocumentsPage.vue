<template>
  <q-page-container>
    <q-page class="q-pa-xs" style="max-width: 1000px; margin: 0 auto">
      <div class="q-gutter-xs">
        <h1></h1>
        <q-btn
          push
          icon="add"
          color="primary"
          :label="$t('add_document')"
          class="button"
          to="/addDocument"
          size="large"
        />
      </div>
      <div class="q-mt-xs">
        <div v-if="!loadingComplete" class="text-center text-grey-8">
          {{ $t("loading") }}
          <q-spinner color="primary" size="3em" :thickness="2" />
        </div>
        <div v-else>
          <div v-if="documents.length === 0" class="text-center text-grey-8">
            {{ $t("no_documents_found") }}
          </div>
          <q-card
            flat
            bordered
            v-else
            v-for="(document, i) in documents"
            :key="i"
            style="margin-top: 20px"
          >
            <q-card-section>
              <q-expansion-item>
                <template v-slot:header>
                  <div class="q-gutter-md">
                    <q-item-label class="text-h5">{{
                      document.title
                    }}</q-item-label>
                  </div>
                </template>
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
                      <span class="info-value">{{
                        document.ownerUsername
                      }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label"> {{ $t("status") }}: </span>
                      <span class="info-value">{{
                        documentAdditionalInformation.statuses[
                          document.statusNum
                        ]
                      }}</span>
                    </div>
                  </div>
                  <div class="info-column">
                    <div class="info-item">
                      <span class="info-label"> {{ $t("gender") }}: </span>
                      <span class="info-value">{{
                        document.authorsGender[0]
                      }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">
                        {{ $t("authors_course") }}:
                      </span>
                      <span class="info-value">{{
                        document.authorsCourse
                      }}</span>
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
              </q-expansion-item>
              <p v-if="document.text.length <= limit" class="text-body2">
                {{ document.text }}
              </p>
              <p v-else class="text-body2">
                {{ truncateText(document.text, limit) }}
              </p>

              <div class="q-pa-xs">
                <div class="row justify-between">
                  <div class="row-auto">
                    <q-btn
                      unelevated
                      color="primary"
                      :label="$t('annotate')"
                      class="button"
                      :to="'/annotateDocument/' + document.id"
                    />
                    <q-btn
                      flat
                      color="primary"
                      :label="$t('show_more')"
                      icon="visibility"
                      class="button"
                      :to="'/documents/' + document.id"
                    />
                  </div>
                  <div class="row-auto">
                    <q-btn
                      no-caps
                      unelevated
                      color="secondary"
                      icon="edit"
                      :label="$t('edit')"
                      class="button"
                      :to="'/editDocument/' + document.id"
                      style="margin-right: 10px"
                    />
                    <q-btn
                      unelevated
                      color="negative"
                      icon="delete"
                      class="button"
                      @click="deleteDocument(document.id)"
                    />
                  </div>
                </div>
              </div>
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
      loadingComplete: false,
      userStatus: false,
      limit: 3000,

      documentAdditionalInformation: {
        statuses: statuses(),
      },
    };
  },
  methods: {
    async loadAllDocuments() {
      try {
        const response = await fetch(serverAdress + "/documents", {
          method: "GET",
        });
        this.responseSuccess = true;
        if (response.ok) {
          const data = await response.json();
          this.documents = data;
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },
    async deleteDocument(id) {
      try {
        const confirmation =
          this.$i18n.locale === "ru"
            ? confirm("Вы уверены, что хотите удалить документ?")
            : confirm("Are you sure you want to delete the document?");
        if (!confirmation) return;
        const response = await fetch(serverAdress + "/documents/delete/" + id, {
          method: "DELETE",
        });
        if (response.ok) {
          this.loadAllDocuments();
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },

    truncateText(text, limit) {
      if (text.length <= limit) {
        return text;
      } else {
        return text.substring(0, limit) + "...";
      }
    },

    statuses() {
      return this.$i18n.locale === "ru"
        ? ["Не аннотирован", "Аннотирован", "Проверен"]
        : ["Not annotated", "Annotated", "Checked"];
    },
  },
  async mounted() {
    this.userStatus = await isLogin();
    if (this.userStatus) {
      await this.loadAllDocuments();
      this.loadingComplete = true;
    } else {
      this.$router.push("/");
    }
  },
};
</script>

<style>
.item-info {
  display: flex;
  flex-direction: row;
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
}
</style>
