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
                      <q-card-section
                        class="bg-primary text-white"
                        @click="toggleTextFeaturesTextboxes()"
                      >
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
              <q-form @submit="lexgramSearch" class="q-gutter-md">
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
                          @click="focusInput('from', index)"
                        />
                        <q-input
                          outlined
                          v-model="block.from"
                          :placeholder="index"
                          dense
                          style="width: 50px"
                          :ref="'fromInput' + index"
                          id="from"
                        />
                        <q-btn
                          unelevated
                          no-caps
                          color="teal"
                          label="to"
                          @click="focusInput('to', index)"
                        />
                        <q-input
                          outlined
                          v-model="block.to"
                          :placeholder="index"
                          style="width: 50px"
                          dense
                          :ref="'toInput' + index"
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
                      <div class="col-3">
                        <label v-if="!showDeleteButton || !index > 0">
                          Part of speech
                        </label>
                        <q-input
                          readonly
                          outlined
                          v-model="block.partOfSpeech"
                          placeholder="Part of speech"
                          dense
                        >
                          <template v-slot:append>
                            <q-icon name="menu" class="cursor-pointer">
                              <q-popup-proxy
                                cover
                                transition-show="scale"
                                transition-hide="scale"
                                style="background-color: rgba(0, 0, 0, 0.5)"
                              >
                                <q-card>
                                  <q-card-section
                                    class="bg-primary text-white row items-center"
                                  >
                                    <div
                                      class="text-h6"
                                      @click="
                                        toggleAllCheckboxes(
                                          lexgrammFeaturesFixed.partOfSpeech,
                                          block.partOfSpeech,
                                          'upper'
                                        )
                                      "
                                    >
                                      Part of speech
                                    </div>
                                    <q-space />
                                    <q-btn
                                      icon="close"
                                      flat
                                      round
                                      dense
                                      v-close-popup
                                    />
                                  </q-card-section>
                                  <q-card-section
                                    style="display: flex; flex-wrap: wrap"
                                  >
                                    <q-list style="width: 33.33%">
                                      <q-item
                                        v-for="partOfSpeech in lexgrammFeaturesFixed.partOfSpeech.slice(
                                          0,
                                          Math.ceil(
                                            lexgrammFeaturesFixed.partOfSpeech
                                              .length / 3
                                          )
                                        )"
                                        :key="partOfSpeech"
                                      >
                                        <q-checkbox
                                          v-model="block.partOfSpeech"
                                          :val="partOfSpeech"
                                          :label="partOfSpeech"
                                        />
                                      </q-item>
                                    </q-list>
                                    <q-list style="width: 33.33%">
                                      <q-item
                                        v-for="partOfSpeech in lexgrammFeaturesFixed.partOfSpeech.slice(
                                          Math.ceil(
                                            lexgrammFeaturesFixed.partOfSpeech
                                              .length / 3
                                          ),
                                          Math.ceil(
                                            (2 *
                                              lexgrammFeaturesFixed.partOfSpeech
                                                .length) /
                                              3
                                          )
                                        )"
                                        :key="partOfSpeech"
                                      >
                                        <q-checkbox
                                          v-model="block.partOfSpeech"
                                          :val="partOfSpeech"
                                          :label="partOfSpeech"
                                        />
                                      </q-item>
                                    </q-list>
                                    <q-list style="width: 33.33%">
                                      <q-item
                                        v-for="partOfSpeech in lexgrammFeaturesFixed.partOfSpeech.slice(
                                          Math.ceil(
                                            (2 *
                                              lexgrammFeaturesFixed.partOfSpeech
                                                .length) /
                                              3
                                          )
                                        )"
                                        :key="partOfSpeech"
                                      >
                                        <q-checkbox
                                          v-model="block.partOfSpeech"
                                          :val="partOfSpeech"
                                          :label="partOfSpeech"
                                        />
                                      </q-item>
                                    </q-list>
                                  </q-card-section>
                                </q-card>
                              </q-popup-proxy>
                            </q-icon>
                          </template>
                        </q-input>
                      </div>
                      <!-- Include gram select component here -->
                      <div class="col-3">
                        <label v-if="!showDeleteButton || !index > 0">
                          Grammar
                        </label>
                        <q-input
                          readonly
                          outlined
                          v-model="block.grammar"
                          placeholder="Grammar characteristics"
                          dense
                        >
                          <template v-slot:append>
                            <q-icon name="menu" class="cursor-pointer">
                              <q-popup-proxy
                                cover
                                transition-show="scale"
                                transition-hide="scale"
                              >
                                <q-card>
                                  <q-card-section
                                    class="bg-primary text-white row items-center"
                                  >
                                    <div class="text-h6">
                                      Grammar characteristics
                                    </div>
                                    <q-space />
                                    <q-btn
                                      icon="close"
                                      flat
                                      round
                                      dense
                                      v-close-popup
                                    />
                                  </q-card-section>
                                  <q-card-section>
                                    <div
                                      v-for="block_ in lexgrammFeaturesFixed.grammar"
                                      :key="block_"
                                    >
                                      <q-card-section
                                        style="display: flex; flex-wrap: wrap"
                                      >
                                        <div class="text-h4">
                                          {{ block_.block }}
                                        </div>
                                      </q-card-section>
                                      <q-card-section
                                        style="display: flex; flex-wrap: wrap"
                                      >
                                        <q-list>
                                          <div
                                            class="row items-center"
                                            style="
                                              display: flex;
                                              flex-wrap: wrap;
                                            "
                                          >
                                            <q-item
                                              v-for="blockValue in block_.value"
                                              :key="blockValue.name"
                                            >
                                              <q-card-section
                                                style="
                                                  flex-direction: row;
                                                  align-items: flex-start;
                                                "
                                              >
                                                <div class="text-h6">
                                                  {{ blockValue.name }}
                                                </div>
                                                <div
                                                  v-for="(
                                                    value, i
                                                  ) in blockValue.value"
                                                  :key="i"
                                                >
                                                  <q-checkbox
                                                    v-model="block.grammar"
                                                    :val="value"
                                                    :label="value"
                                                  ></q-checkbox>
                                                </div>
                                              </q-card-section>
                                            </q-item>
                                          </div>
                                        </q-list>
                                      </q-card-section>
                                    </div>
                                  </q-card-section>
                                </q-card>
                              </q-popup-proxy>
                            </q-icon>
                          </template>
                        </q-input>
                      </div>
                      <!-- Include err select component here -->
                      <div class="col-2">
                        <label v-if="!showDeleteButton || !index > 0">
                          Errors
                        </label>
                        <q-input
                          readonly
                          outlined
                          v-model="block.errors"
                          placeholder="Tags"
                          dense
                        >
                          <template v-slot:append>
                            <q-icon name="menu" class="cursor-pointer">
                              <q-popup-proxy
                                cover
                                transition-show="scale"
                                transition-hide="scale"
                              >
                              </q-popup-proxy>
                            </q-icon>
                          </template>
                        </q-input>
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
          partOfSpeech: [],
          grammar: [],
          errors: [],
          additional: false,
          from: 0,
          to: 0,
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

      lexgrammFeaturesFixed: {
        partOfSpeech: [
          "ADJ",
          "ADP",
          "ADV",
          "AUX",
          "CCONJ",
          "DET",
          "INTJ",
          "NOUN",
          "NUM",
          "PART",
          "PRON",
          "PROPN",
          "PUNCT",
          "SCONJ",
          "SYM",
          "VERB",
          "X",
        ],
        grammar: [
          {
            block: "Nominal Features",
            value: [
              {
                name: "Gender",
                value: ["Masc", "Fem", "Neut"],
              },
              {
                name: "Animacy",
                value: ["Anim", "Inan"],
              },
              {
                name: "Number",
                value: ["Sing", "Plur"],
              },
              {
                name: "Case",
                value: ["Nom", "Gen", "Dat", "Acc", "Ins", "Loc", "Voc", "Par"],
              },
            ],
          },
          {
            block: "Degree and Polarity",
            value: [
              {
                name: "Degree",
                value: ["Pos", "Cmp", "Sup"],
              },
              {
                name: "Polarity",
                value: ["Neg"],
              },
              {
                name: "Variant",
                value: ["Short"],
              },
            ],
          },
          {
            block: "Verbal Features",
            value: [
              {
                name: "Aspect",
                value: ["Perf", "AspectImp"],
              },
              {
                name: "Mood",
                value: ["Ind", "MoodImp", "Cnd"],
              },
              {
                name: "Tense",
                value: ["Past", "Pres", "Fut"],
              },
              {
                name: "Voice",
                value: ["Act", "Pass", "Mid"],
              },
            ],
          },
          {
            block: "Pronouns, Determiners, Quantifiers",
            value: [
              {
                name: "Person",
                value: ["1", "2", "3"],
              },
            ],
          },
          {
            block: "Other Features",
            value: [
              {
                name: "Hyph",
                value: ["HyphYes"],
              },
              {
                name: "Foreign",
                value: ["ForeignYes"],
              },
            ],
          },
        ],
        errors: [],
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
        console.log(response);
        const data = await response.json();
        this.searchResults = data;
      } else {
        console.error(response);
      }
    },

    async lexgramSearch() {
      this.searchResults = [];
      const data = {
        lexgramBlocks: this.lexgramBlocks,
        subcorpusData: this.subcorpusData,
      };
      try {
        const response = await fetch(
          serverAdress + "/documents/search/lex_gram",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
          }
        );
        if (response.ok) {
          const data = await response.json();
          this.searchResults = data;
        } else {
          console.log(response);
        }
      } catch (error) {
        console.log("Error in lexgramSearch");
        console.error(error);
      }
    },
    addLexgramBlock() {
      this.lexgramBlocks.push({
        wordform: "",
        partOfSpeech: [],
        grammar: [],
        errors: [],
        additional: true,
        from: "1",
        to: "1",
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
    toggleTextFeaturesTextboxes() {
      const allChecked =
        this.subcorpusData.genres.length ===
          this.subcorpusTextInfoContainer.genres.length &&
        this.subcorpusData.authorsAcademicMajors.length ===
          this.subcorpusTextInfoContainer.authorsAcademicMajors.length &&
        this.subcorpusData.domains.length ===
          this.subcorpusTextInfoContainer.domains.length;
      if (allChecked) {
        this.subcorpusData.genres.splice(0);
        this.subcorpusData.authorsAcademicMajors.splice(0);
        this.subcorpusData.domains.splice(0);
      } else {
        this.subcorpusData.genres.splice(
          0,
          this.subcorpusData.genres.length,
          ...this.subcorpusTextInfoContainer.genres
        );
        this.subcorpusData.authorsAcademicMajors.splice(
          0,
          this.subcorpusData.authorsAcademicMajors.length,
          ...this.subcorpusTextInfoContainer.authorsAcademicMajors
        );
        this.subcorpusData.domains.splice(
          0,
          this.subcorpusData.domains.length,
          ...this.subcorpusTextInfoContainer.domains
        );
      }
    },
    toggleAllCheckboxes(checkboxes, data, caseType = "lower") {
      const allChecked = data.length === checkboxes.length;

      if (allChecked) {
        data.splice(0);
      } else {
        if (caseType === "lower") {
          data.splice(
            0,
            data.length,
            ...checkboxes.map((item) => item.toLowerCase())
          );
        } else if (caseType === "upper") {
          data.splice(
            0,
            data.length,
            ...checkboxes.map((item) => item.toUpperCase())
          );
        } else {
          data.splice(0, data.length, ...checkboxes);
        }
      }
    },
    focusInput(field, id) {
      this.$refs[`${field}Input${id}`][0].focus();
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
