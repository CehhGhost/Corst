<template>
  <q-page-container>
    <q-page class="q-pa-xs" style="max-width: 1000px; margin: 0 auto">
      <div class="q-gutter-xs">
        <h1></h1>
        <q-btn
          v-if="canCreate"
          push
          icon="add"
          color="primary"
          :label="$t('add_document')"
          class="button"
          to="/addDocument"
          size="large"
        />
      </div>

      <div
        class="row"
        style="
          display: flex;
          justify-content: space-between;
          width: 100%;
          margin-top: 20px;
        "
      >
        <div class="row-auto" style="flex: 1; min-width: 75px; max-width: 12%">
          <q-input v-model="selectedFrom" outlined :label="$t('from')" />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 75px; max-width: 13%">
          <q-input v-model="selectedTo" outlined :label="$t('to')" />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedOwners"
            :options="filteredOwners"
            outlined
            multiple
            use-input
            @filter="filterOwners"
            input-debounce="0"
            :label="$t('owner')"
            class="fixed-height-select"
          />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedAuthorsGenders"
            :options="filteredAuthorsGenders"
            outlined
            multiple
            use-input
            @filter="filterAuthorsGenders"
            input-debounce="0"
            :label="$t('gender')"
            class="fixed-height-select"
          />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedStatuses"
            :options="filteredStatuses"
            outlined
            multiple
            use-input
            @filter="filterStatuses"
            input-debounce="0"
            :label="$t('status')"
            class="fixed-height-select"
          />
        </div>
      </div>
      <div
        class="row"
        style="
          display: flex;
          justify-content: space-between;
          width: 100%;
          margin-bottom: 10px;
        "
      >
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedGenres"
            :options="filteredGenres"
            outlined
            multiple
            use-input
            @filter="filterGenres"
            input-debounce="0"
            :label="$t('genre')"
            class="fixed-height-select"
          />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedAuthorsDomains"
            :options="filteredAuthorsDomains"
            outlined
            multiple
            use-input
            @filter="filterAuthorsDomains"
            input-debounce="0"
            :label="$t('domain')"
            class="fixed-height-select"
          />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedAuthorsCourses"
            :options="filteredAuthorsCourses"
            outlined
            multiple
            use-input
            @filter="filterAuthorsCourses"
            input-debounce="0"
            :label="$t('authors_course')"
            class="fixed-height-select"
          />
        </div>
        <div class="row-auto" style="flex: 1; min-width: 150px; max-width: 25%">
          <q-select
            v-model="selectedAuthorsAcademicMajors"
            :options="filteredAuthorsAcademicMajors"
            outlined
            multiple
            use-input
            @filter="filterAuthorsAcademicMajors"
            input-debounce="0"
            :label="$t('authors_academic_major')"
            class="fixed-height-select"
          />
        </div>
      </div>

      <div class="q-pa-xs">
        <div class="row justify-between">
          <div class="row-auto">
            <q-btn
              unelevated
              color="primary"
              icon="download"
              :label="$t('download_filtered')"
              class="button"
            />
          </div>
          <div class="row-auto">
            <q-btn
              @click="clearFilters"
              no-caps
              outline
              color="secondary"
              :label="$t('Clear')"
              class="button"
              style="margin-right: 10px"
            />
            <q-btn
              @click="filterDocuments"
              no-caps
              unelevated
              color="secondary"
              icon="filter_list"
              :label="$t('filter')"
              class="button"
              style="margin-right: 10px"
            />
          </div>
        </div>
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
                      v-if="canAnnotate"
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
                      v-if="canEdit"
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
                      v-if="canEdit"
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
import { ref } from "vue";
import { serverAdress } from "../global/globalVaribles.js";
import { getAuthorities, checkAuthorities } from "../global/globalFunctions.js";

export default {
  data() {
    return {
      documents: [],
      authorities: [],
      responseSuccess: true,
      loadingComplete: false,
      userStatus: false,
      limit: 3000,

      selectedFrom: 0,
      selectedTo: 2024,
      selectedGenres: [],
      selectedOwners: [],
      selectedStatuses: [],
      selectedAuthorsGenders: [],
      selectedAuthorsDomains: [],
      selectedAuthorsCourses: [],
      selectedAuthorsAcademicMajors: [],

      genres: [],
      owners: [],
      authorsDomains: [],
      authorsCourses: [],
      authorsAcademicMajors: [],
      authorsGenders: ["Мужской", "Женский", "Неизвестно"],
      documentStatuses: ["Not Annotated", "Annotated", "Checked"],

      filteredGenres: [],
      filteredOwners: [],
      filteredAuthorsDomains: [],
      filteredAuthorsCourses: [],
      filteredAuthorsAcademicMajors: [],
      filteredAuthorsGenders: ["Мужской", "Женский", "Неизвестно"],
      filteredStatuses: ["Not Annotated", "Annotated", "Checked"],

      documentAdditionalInformation: {
        statuses: this.statuses(),
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
      return (
        this.authorities.some(
          (auth) => auth.authority === "ANNOTATE_ALLDOCUMENTS"
        ) ||
        this.authorities.some(
          (auth) => auth.authority === "CHECK_ANNOTATEDDOCUMENTS"
        )
      );
    },
  },
  methods: {
    async getDocumentInfo() {
      try {
        const response = await fetch(serverAdress + "/info/document", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("corst_token"),
          },
        });
        this.responseSuccess = response.ok;
        if (response.ok) {
          const data = await response.json();
          return data;
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },

    async getAllOwners() {
      try {
        const response = await fetch(
          serverAdress + "/documents/get_all_owners",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.getItem("corst_token"),
            },
          }
        );
        if (response.ok) {
          const data = await response.json();
          this.owners = data;
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },

    parseData(data) {
      this.genres = data.genres;
      this.authorsDomains = data.domains;
      this.authorsCourses = data.courses;
      this.authorsAcademicMajors = data.academicMajors;
      for (let i = 0; i < this.genres.length; i++) {
        this.filteredGenres.push(this.genres[i]);
      }
      for (let i = 0; i < this.authorsDomains.length; i++) {
        this.filteredAuthorsDomains.push(this.authorsDomains[i]);
      }
      for (let i = 0; i < this.authorsCourses.length; i++) {
        this.filteredAuthorsCourses.push(this.authorsCourses[i]);
      }
      for (let i = 0; i < this.authorsAcademicMajors.length; i++) {
        this.filteredAuthorsAcademicMajors.push(this.authorsAcademicMajors[i]);
      }
    },

    async loadAllDocuments() {
      try {
        const response = await fetch(serverAdress + "/documents", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("corst_token"),
          },
        });
        this.responseSuccess = response.ok;
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

    async filterDocuments() {
      const subcorpusData = {
        periodFrom: this.selectedFrom,
        periodTo: this.selectedTo,
        genres: this.selectedGenres,
        statuses: this.selectedStatuses,
        authorsGenders: this.selectedAuthorsGenders,
        domains: this.selectedAuthorsDomains,
        authorsCourses: this.selectedAuthorsCourses,
        authorsAcademicMajors: this.selectedAuthorsAcademicMajors,
      };
      const data = {
        subcorpusData: subcorpusData,
        owners: this.selectedOwners,
      };
      this.loadingComplete = false;
      const response = await fetch(serverAdress + "/documents/filter", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
        body: JSON.stringify(data),
      });
      if (response.ok) {
        const data = await response.json();
        this.documents = data;
        this.loadingComplete = true;
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

    filterGenres(val, update) {
      update(() => {
        if (val === "") {
          this.filteredGenres = this.genres;
        } else {
          const needle = val.toLowerCase();
          this.filteredGenres = this.genres.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },

    filterOwners(val, update) {
      update(() => {
        if (val === "") {
          this.filteredOwners = this.owners;
        } else {
          const needle = val.toLowerCase();
          this.filteredOwners = this.owners.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },

    filterStatuses(val, update) {
      update(() => {
        if (val === "") {
          this.filteredStatuses = this.documentStatuses;
        } else {
          const needle = val.toLowerCase();
          this.filteredStatuses = this.documentStatuses.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },

    filterAuthorsGenders(val, update) {
      update(() => {
        if (val === "") {
          this.filteredAuthorsGenders = this.authorsGenders;
        } else {
          const needle = val.toLowerCase();
          this.filteredAuthorsGenders = this.authorsGenders.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },

    filterAuthorsDomains(val, update) {
      update(() => {
        if (val === "") {
          this.filteredAuthorsDomains = this.authorsDomains;
        } else {
          const needle = val.toLowerCase();
          this.filteredAuthorsDomains = this.authorsDomains.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },

    filterAuthorsCourses(val, update) {
      update(() => {
        if (val === "") {
          this.filteredAuthorsCourses = this.authorsCourses;
        } else {
          const needle = val.toLowerCase();
          this.filteredAuthorsCourses = this.authorsCourses.filter(
            (v) => v.toLowerCase().indexOf(needle) > -1
          );
        }
      });
    },

    filterAuthorsAcademicMajors(val, update) {
      update(() => {
        if (val === "") {
          this.filteredAuthorsAcademicMajors = this.authorsAcademicMajors;
        } else {
          const needle = val.toLowerCase();
          this.filteredAuthorsAcademicMajors =
            this.authorsAcademicMajors.filter(
              (v) => v.toLowerCase().indexOf(needle) > -1
            );
        }
      });
    },

    async clearFilters() {
      this.selectedFrom = 0;
      this.selectedTo = 2024;
      this.selectedGenres = [];
      this.selectedOwners = [];
      this.selectedStatuses = [];
      this.selectedAuthorsGenders = [];
      this.selectedAuthorsDomains = [];
      this.selectedAuthorsCourses = [];
      this.selectedAuthorsAcademicMajors = [];
      await this.loadAllDocuments();
    },
  },
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.authorities = await getAuthorities();
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
    if (this.userStatus) {
      const data = await this.getDocumentInfo();
      if (this.responseSuccess) {
        this.parseData(data);
      }
      await this.getAllOwners();
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

.fixed-height-select .q-field__native {
  min-height: 31px;
  max-height: 32px;
  overflow-y: auto;
}

.fixed-height-select .q-field__control {
  display: flex;
  align-items: center;
  height: 100%;
}
</style>
