<template>
  <q-page-container>
    <q-page class="q-pa-md">
      <div class="row q-gutter-md justify-center">
        <div class="col-lg-6 col-md-8 col-sm-10">
          <h3></h3>
          <div class="col-3">
            <q-btn-group vertical style="margin-bottom: 20px">
              <q-btn
                label="Specify Subcorpus"
                color="primary"
                @click="displaySubcorpusModal = true"
              />
              <q-btn
                label="Display Options"
                color="primary"
                @click="displaySettingsModal = true"
              />
            </q-btn-group>
          </div>
          <q-dialog v-model="displaySubcorpusModal">
            <q-card style="width: 800px; max-width: 90vw">
              <q-card-section class="row items-center q-pb-none">
                <label class="text-h6">Specify Subcorpus</label>
                <q-space />
                <q-btn icon="close" flat round dense v-close-popup />
              </q-card-section>
              <q-card-section class="row items-center">
                <hr />
                <div
                  class="row"
                  style="display: flex; flex-wrap: wrap; width: 1100px"
                >
                  <div style="flex: 1 0 30%; max-width: 30%">
                    <q-card outlined>
                      <q-card-section class="bg-primary text-white">
                        <div class="text-h6">Period</div>
                      </q-card-section>
                      <q-card-actions>
                        <div class="row">
                          <q-input
                            v-model="subcorpusData.periodFrom"
                            label="From"
                            placeholder="2014"
                            outlined
                            style="width: 48%; margin-right: 4%"
                          />
                          <q-input
                            v-model="subcorpusData.periodTo"
                            label="To"
                            placeholder="2015"
                            outlined
                            style="width: 48%"
                          />
                        </div>
                      </q-card-actions>
                    </q-card>

                    <q-card outlined style="margin-top: 20px">
                      <q-card-section
                        class="bg-primary text-white"
                        @click="
                          toggleAllCheckboxes(
                            subcorpusTextInfoContainer.authorsGenders,
                            subcorpusData.authorsGenders
                          )
                        "
                      >
                        <div class="text-h6">Author Gender</div>
                      </q-card-section>
                      <q-card-actions>
                        <div
                          v-for="(
                            gender, i
                          ) in subcorpusTextInfoContainer.authorsGenders"
                          :key="i"
                        >
                          <q-checkbox
                            v-model="subcorpusData.authorsGenders"
                            :val="gender.toLowerCase()"
                            :label="gender"
                          ></q-checkbox>
                        </div>
                      </q-card-actions>
                    </q-card>

                    <q-card outlined style="margin-top: 20px">
                      <q-card-section
                        class="bg-primary text-white"
                        @click="
                          toggleAllCheckboxes(
                            subcorpusTextInfoContainer.authorsCourses,
                            subcorpusData.authorsCourses
                          )
                        "
                      >
                        <div class="text-h6">Authors course</div>
                      </q-card-section>
                      <q-card-actions
                        style="flex-direction: column; align-items: flex-start"
                      >
                        <div
                          v-for="(
                            course, i
                          ) in subcorpusTextInfoContainer.authorsCourses"
                          :key="i"
                        >
                          <q-checkbox
                            v-model="subcorpusData.authorsCourses"
                            :val="course.toLowerCase()"
                            :label="course"
                          ></q-checkbox>
                        </div>
                      </q-card-actions>
                    </q-card>
                  </div>

                  <div style="flex: 1 0 30%; max-width: 70%">
                    <q-card outlined style="margin-left: 30px">
                      <q-card-section class="bg-primary text-white">
                        <div class="text-h6">Text features</div>
                      </q-card-section>
                      <q-card-actions
                        style="flex-direction: row; align-items: flex-start"
                      >
                        <div style="flex: 1">
                          <label
                            @click="
                              toggleAllCheckboxes(
                                subcorpusTextInfoContainer.genres,
                                subcorpusData.genres
                              )
                            "
                            >Type of assignment
                          </label>
                          <div
                            style="display: flex; flex-direction: column"
                            v-for="(
                              genre, i
                            ) in subcorpusTextInfoContainer.genres"
                            :key="i"
                          >
                            <q-checkbox
                              v-model="subcorpusData.genres"
                              :val="genre.toLowerCase()"
                              :label="genre"
                            ></q-checkbox>
                          </div>
                        </div>
                        <div style="flex: 1">
                          <label
                            @click="
                              toggleAllCheckboxes(
                                subcorpusTextInfoContainer.authorsAcademicMajors,
                                subcorpusData.authorsAcademicMajors
                              )
                            "
                            >Academic major
                          </label>
                          <div
                            style="display: flex; flex-direction: column"
                            v-for="(
                              academicMajor, i
                            ) in subcorpusTextInfoContainer.authorsAcademicMajors"
                            :key="i"
                          >
                            <q-checkbox
                              v-model="subcorpusData.authorsAcademicMajors"
                              :val="academicMajor.toLowerCase()"
                              :label="academicMajor"
                            ></q-checkbox>
                          </div>
                        </div>
                        <div style="flex: 1">
                          <label
                            @click="
                              toggleAllCheckboxes(
                                subcorpusTextInfoContainer.domains,
                                subcorpusData.domains
                              )
                            "
                            >Domain
                          </label>
                          <div
                            style="display: flex; flex-direction: column"
                            v-for="(
                              domain, i
                            ) in subcorpusTextInfoContainer.domains"
                            :key="i"
                          >
                            <q-checkbox
                              v-model="subcorpusData.domains"
                              :val="domain.toLowerCase()"
                              :label="domain"
                            ></q-checkbox>
                          </div>
                        </div>
                      </q-card-actions>
                    </q-card>

                    <q-card
                      outlined
                      style="margin-top: 20px; margin-left: 30px"
                    >
                      <q-card-section
                        class="bg-primary text-white"
                        @click="
                          toggleAllCheckboxes(
                            subcorpusTextInfoContainer.statuses,
                            subcorpusData.statuses
                          )
                        "
                      >
                        <div class="text-h6">Text status</div>
                      </q-card-section>
                      <q-card-actions>
                        <div
                          v-for="(
                            status, i
                          ) in subcorpusTextInfoContainer.statuses"
                          :key="i"
                        >
                          <q-checkbox
                            v-model="subcorpusData.statuses"
                            :val="status.toLowerCase()"
                            :label="status"
                          ></q-checkbox>
                        </div>
                      </q-card-actions>
                    </q-card>
                  </div>
                </div>
              </q-card-section>
            </q-card>
          </q-dialog>
          <q-card class="rounded-borders">
            <q-card-section class="exactSearch">
              <h3 class="text-h6">Exact Search</h3>
              <q-form @submit="exactSearch" class="q-gutter-md">
                <div class="row q-gutter-md items-center">
                  <div class="col">
                    <q-input
                      v-model="exactSearchInput"
                      placeholder="Exact search"
                      outlined
                    />
                  </div>
                  <div class="col-auto">
                    <q-btn
                      no-caps
                      type="submit"
                      icon="search"
                      color="primary"
                      label="Search"
                      size="large"
                      dense
                    />
                  </div>
                </div>
              </q-form>
            </q-card-section>
          </q-card>
          <h4></h4>
          <q-card class="rounded-borders">
            <q-card-section class="exactSearch">
              <h3 class="text-h6">Lexgramm Search</h3>
              <q-form @submit="lexgrammSearch" class="q-gutter-md">
                <div>
                  <div
                    style="display: flex; flex-direction: column"
                    v-for="(block, index) in lexgramBlocks"
                    :key="index"
                  >
                    <div
                      v-if="block.additional"
                      class="row"
                      style="
                        flex-direction: row;
                        align-items: flex-start;
                        margin-top: 20px;
                      "
                    >
                      <div style="display: flex; margin-right: 20px">
                        <q-btn
                          unelevated
                          no-caps
                          color="teal"
                          label="From"
                          @click="focusInput('from')"
                        />
                        <q-input
                          outlined
                          v-model="block.from"
                          :placeholder="index"
                          dense
                          style="width: 50px"
                          ref="fromInput"
                          id="from"
                        />
                        <q-btn
                          unelevated
                          no-caps
                          color="teal"
                          label="to"
                          @click="focusInput('to')"
                        />
                        <q-input
                          outlined
                          v-model="block.to"
                          :placeholder="index"
                          style="width: 50px"
                          dense
                          ref="toInput"
                          id="to"
                        />
                      </div>
                    </div>

                    <div class="row align-items-end" style="margin-top: 17px">
                      <div class="col-3">
                        <label v-if="!showDeleteButton || !index > 0">
                          Wordform
                        </label>
                        <q-input
                          outlined
                          v-model="block.wordform"
                          placeholder="Wordform"
                          dense
                        />
                      </div>
                      <!-- Include lex select component here -->
                      <div class="col-3">
                        <label v-if="!showDeleteButton || !index > 0">
                          Part of speech
                        </label>
                        <q-input
                          outlined
                          v-model="block.partOfSpeech"
                          placeholder="Part of speech"
                          dense
                        />
                      </div>
                      <!-- Include gram select component here -->
                      <div class="col-3">
                        <label v-if="!showDeleteButton || !index > 0">
                          Grammar
                        </label>
                        <q-input
                          outlined
                          v-model="block.grammar"
                          placeholder="Grammar characteristics"
                          dense
                        />
                      </div>
                      <!-- Include err select component here -->
                      <div class="col-2">
                        <label v-if="!showDeleteButton || !index > 0">
                          Errors
                        </label>
                        <q-input
                          outlined
                          v-model="block.errors"
                          placeholder="Tags"
                          dense
                        />
                      </div>
                      <div class="col-1">
                        <q-btn
                          unelevated
                          v-if="showDeleteButton && index > 0"
                          @click="removeBlock(index)"
                          color="negative"
                          class="delete-btn"
                          icon="delete"
                        >
                        </q-btn>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-1">
                  <q-btn
                    @click="addLexgramBlock"
                    outline
                    color="primary"
                    label="Add Wordform"
                  />
                  <q-btn
                    unelevated
                    type="submit"
                    label="Search"
                    color="primary"
                  />
                </div>
              </q-form>
            </q-card-section>
          </q-card>
          <h4></h4>
          <div>
            <div v-for="(result, index) in searchResults" :key="index">
              <q-card class="rounded-borders" style="margin-top: 10px">
                <q-card-section>
                  <div class="row q-gutter-md items-center">
                    <div class="col-3">
                      <!-- TODO Уточнить, что должно быть в названии -->
                      <h3 class="text-h6">{{ result.documentTitle }}</h3>
                      <p>{{ result.text }}</p>
                    </div>
                  </div>
                </q-card-section>
              </q-card>
            </div>
          </div>
        </div>
      </div>
    </q-page>
  </q-page-container>
</template>

<script>
import { serverAdress } from "src/global/globalVaribles";
import { ref } from "vue";

export default {
  setup() {
    const displaySettingsModal = ref(false);
    const displaySubcorpusModal = ref(false);
    return { displaySettingsModal, displaySubcorpusModal };
  },
  data() {
    return {
      exactSearchInput: "",
      lexgramBlocks: [
        {
          wordform: "",
          partOfSpeech: "",
          grammar: "",
          errors: "",
          additional: false,
          from: "",
          to: "",
        },
      ],

      subcorpusData: {
        periodFrom: "",
        periodTo: "",
        authorsGenders: [],
        genres: [],
        domains: [],
        authorsCourses: [],
        authorsAcademicMajors: [],
        statuses: [],
      },

      subcorpusTextInfoContainer: {
        authorsGenders: ["Male", "Female", "Unknown"],
        genres: [],
        domains: [],
        authorsCourses: [],
        authorsAcademicMajors: [],
        statuses: ["Not Annotated", "Annotated", "Checked"],
      },

      showDeleteButton: true,
      searchResults: [],
    };
  },
  methods: {
    async getSubcorpusTextInfo() {
      try {
        const response = await fetch(serverAdress + "/info/document", {
          method: "GET",
        });
        if (response.ok) {
          const data = await response.json();
          return data;
        } else {
          console.error(response);
        }
      } catch (error) {
        console.error(error);
      }
    },

    async exactSearch() {
      console.log(
        JSON.stringify({
          wordform: this.exactSearchInput,
          subcorpusData: this.subcorpusData,
        })
      );
      this.searchResults = [];
      const response = await fetch(serverAdress + "/documents/search/certain", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          wordform: this.exactSearchInput,
          subcorpusData: this.subcorpusData,
        }),
      });
      if (response.ok) {
        const data = await response.json();
        this.searchResults = data;
      } else {
        console.error(response);
      }
    },
    lexgrammSearch() {},
    addLexgramBlock() {
      this.lexgramBlocks.push({
        wordform: "",
        partOfSpeech: "",
        grammar: "",
        errors: "",
        additional: true,
        from: this.lexgramBlocks.length,
        to: this.lexgramBlocks.length,
      });
      if (this.lexgramBlocks.length > 1) {
        this.showDeleteButton = true;
      }
    },
    removeBlock(index) {
      this.lexgramBlocks.splice(index, 1);
      if (this.lexgramBlocks.length === 1) {
        this.showDeleteButton = false;
      }
    },
    openSubcorpusModal() {
      this.displaySubcorpusModal = true;
      console.log(this.displaySubcorpusModal);
    },
    openDisplaySettingsModal() {
      this.displaySettingsModal = true;
    },
    closeSubcorpusModal() {
      this.displaySubcorpusModal = false;
    },
    closeDisplaySettingsModal() {
      this.displaySettingsModal = false;
    },
    toggleAllCheckboxes(checkboxes, data) {
      const allChecked = data.length === checkboxes.length;

      if (allChecked) {
        data.splice(0);
      } else {
        data.splice(
          0,
          data.length,
          ...checkboxes.map((course) => course.toLowerCase())
        );
      }
    },
    focusInput(field) {
      this.$refs[`${field}Input`][0].focus();
    },
  },
  async mounted() {
    await this.getSubcorpusTextInfo().then((data) => {
      this.subcorpusTextInfoContainer.genres = data.genres;
      this.subcorpusTextInfoContainer.domains = data.domains;
      this.subcorpusTextInfoContainer.authorsCourses = data.courses;
      this.subcorpusTextInfoContainer.authorsAcademicMajors =
        data.academicMajors;
    });
  },
};
</script>

<style scoped>
.exactSearch {
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
.rounded-borders {
  border-radius: 12px;
}
.delete-btn {
  margin-top: 2px;
  margin-left: 5px;
}
</style>
