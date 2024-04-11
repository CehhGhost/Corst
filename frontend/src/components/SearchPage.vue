<template>
  <q-page-container>
    <q-page class="q-pa-sm">
      <div class="row q-gutter-md justify-center">
        <div class="col-lg-6 col-md-8 col-sm-10">
          <h3></h3>
          <div class="col-3">
            <q-btn-group vertical style="margin-bottom: 20px">
              <q-btn
                :label="$t('specify_subcorpus')"
                color="primary"
                @click="displaySubcorpusModal = true"
              />
              <q-btn
                :label="$t('display_options')"
                color="primary"
                @click="displaySettingsModal = true"
              />
            </q-btn-group>
          </div>
          <q-dialog v-model="displaySubcorpusModal">
            <q-card style="width: 800px; max-width: 90vw">
              <q-card-section class="row items-center q-pb-sm">
                <label class="text-h6">
                  {{ $t("specify_subcorpus") }}
                </label>
                <q-space />
                <q-btn icon="close" flat round dense v-close-popup />
              </q-card-section>
              <q-separator />
              <q-card-section class="row items-center">
                <hr />
                <div
                  class="row"
                  style="display: flex; flex-wrap: wrap; width: 1100px"
                >
                  <div style="flex: 1 0 30%; max-width: 30%">
                    <q-card outlined>
                      <q-card-section class="bg-primary text-white">
                        <div class="text-h6">
                          {{ $t("period") }}
                        </div>
                      </q-card-section>
                      <q-card-actions>
                        <div class="row">
                          <q-input
                            v-model="subcorpusData.periodFrom"
                            :label="$t('from')"
                            placeholder="2014"
                            outlined
                            style="width: 48%; margin-right: 4%"
                          />
                          <q-input
                            v-model="subcorpusData.periodTo"
                            :label="$t('to')"
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
                        <div class="text-h6">
                          {{ $t("authors_gender") }}
                        </div>
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
                        <div class="text-h6">
                          {{ $t("authors_course") }}
                        </div>
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
                        <div class="text-h6">
                          {{ $t("text_features") }}
                        </div>
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
                            >{{ $t("type_of_assignment") }}
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
                          >
                            {{ $t("authors_academic_major_2") }}
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
                          >
                            {{ $t("domain") }}
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
                        <div class="text-h6">
                          {{ $t("status") }}
                        </div>
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

          <q-dialog v-model="displaySettingsModal">
            <q-card style="width: 400px; max-width: 50vw">
              <q-card-section class="row items-center q-pb-sm">
                <q-toolbar>
                  <q-toolbar-title class="text-h6">
                    {{ $t("display_options_2") }}
                  </q-toolbar-title>
                  <q-space />
                  <q-btn flat round dense icon="close" v-close-popup />
                </q-toolbar>
              </q-card-section>
              <q-separator />
              <q-card-section>
                <div class="row justify-between items-center">
                  <span style="font-size: 18px">
                    {{ $t("matches_per_page") }}
                  </span>
                  <q-select
                    outlined
                    style="width: 90px"
                    v-model="displayOptionsSettings.matchesPerPage"
                    :options="displayOptionsSettingsOptions.matchesPerPage"
                  />
                </div>
                <div class="row justify-between items-center">
                  <span style="font-size: 18px">
                    {{ $t("sentences_in_expanded_context") }}
                  </span>
                  <q-select
                    outlined
                    style="width: 90px"
                    v-model="displayOptionsSettings.sentencesInExpandedContext"
                    :options="
                      displayOptionsSettingsOptions.sentencesInExpandedContext
                    "
                  />
                </div>
              </q-card-section>
            </q-card>
          </q-dialog>

          <q-card class="rounded-borders">
            <q-card-section class="exactSearch">
              <h3 class="text-h6" style="margin: 0px">
                {{ $t("exact_search") }}
              </h3>
              <q-form @submit="exactSearch" class="q-gutter-md">
                <div class="row q-gutter-md items-center align-items-start">
                  <div class="col">
                    <q-input
                      v-model="exactSearchInput"
                      :placeholder="$t('exact_search')"
                      outlined
                    />
                  </div>
                  <div class="col-auto">
                    <q-btn
                      no-caps
                      type="submit"
                      icon="search"
                      color="primary"
                      :label="$t('search')"
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
              <h3 class="text-h6">
                {{ $t("lexgram_search") }}
              </h3>
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
                          :label="$t('from_2')"
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
                          :label="$t('to_2')"
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
                          {{ $t("wordform") }}
                        </label>
                        <q-input
                          outlined
                          v-model="block.wordform"
                          :placeholder="$t('wordform')"
                          dense
                        />
                      </div>
                      <div class="col-3">
                        <label v-if="!showDeleteButton || !index > 0">
                          {{ $t("part_of_speech") }}
                        </label>
                        <q-input
                          readonly
                          outlined
                          v-model="block.partOfSpeech"
                          :placeholder="$t('part_of_speech')"
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
                                      @click="block.partOfSpeech = ''"
                                    >
                                      {{ $t("part_of_speech") }}
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
                                        <q-radio
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
                                        <q-radio
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
                                        <q-radio
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
                          {{ $t("grammar") }}
                        </label>
                        <q-input
                          readonly
                          outlined
                          v-model="block.grammar"
                          :placeholder="$t('grammar')"
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
                                    <div
                                      class="text-h6"
                                      @click="block.grammar = []"
                                    >
                                      {{ $t("grammar_characteristics") }}
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
                                        <div
                                          class="text-h4"
                                          style="height: 1px"
                                        >
                                          {{ block_.block }}
                                        </div>
                                      </q-card-section>
                                      <q-card-section
                                        style="display: flex; flex-wrap: wrap"
                                      >
                                        <q-list>
                                          <div
                                            class="row"
                                            style="display: flex"
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
                                                  style="height: 25px"
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
                          {{ $t("errors") }}
                        </label>
                        <q-input
                          readonly
                          outlined
                          v-model="block.errors"
                          :placeholder="$t('tags')"
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
                    :label="$t('add_wordform')"
                  />
                  <q-btn
                    unelevated
                    type="submit"
                    :label="$t('search')"
                    color="primary"
                  />
                </div>
              </q-form>
            </q-card-section>
          </q-card>
          <h4></h4>
          <div>
            <div v-for="(result, index) in searchResults" :key="index">
              <q-card
                class="rounded-borders"
                style="margin-top: 10px; position: relative"
              >
                <q-card-section>
                  <div class="row q-gutter-md items-center">
                    <div>
                      <!-- TODO Уточнить, что должно быть в названии -->
                      <h3 class="text-h6">{{ result.documentTitle }}</h3>
                      <p>{{ result.text }}</p>
                    </div>
                    <q-btn
                      flat
                      dense
                      color="primary"
                      style="position: absolute; top: 0; right: 10px"
                      @click="showContext(result.id)"
                    >
                      Show Context
                    </q-btn>
                    <q-dialog v-model="contextVisible">
                      <q-card>
                        <q-card-section class="row items-center q-pb-sm">
                          <label class="text-h6">
                            {{ result.documentTitle }}
                          </label>
                          <q-space />
                          <q-btn icon="close" flat round dense v-close-popup />
                        </q-card-section>
                        <q-card-section>
                          <div
                            v-for="(sentence, i) in context.context"
                            :key="i"
                          >
                            <span
                              v-if="i == context.origin_num"
                              style="font-weight: bold"
                            >
                              {{ sentence }}
                            </span>
                            <span>{{ sentence }}</span>
                          </div>
                        </q-card-section>
                      </q-card>
                    </q-dialog>
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
    const contextVisible = ref(false);
    return { displaySettingsModal, displaySubcorpusModal, contextVisible };
  },
  data() {
    return {
      exactSearchInput: "",
      lexgramBlocks: [
        {
          wordform: "",
          partOfSpeech: "",
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

      displayOptionsSettings: {
        matchesPerPage: 50,
        sentencesInExpandedContext: 2,
      },

      displayOptionsSettingsOptions: {
        matchesPerPage: [10, 20, 50, 100, 250, 500, 1000],
        sentencesInExpandedContext: [1, 2, 3],
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
            block: "Other Features",
            value: [
              {
                name: "Person",
                value: ["1", "2", "3"],
              },
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

      context: null,
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
      console.log(data);
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
        partOfSpeech: "",
        grammar: [],
        errors: [],
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

    async showContext(id) {
      this.context = "";
      this.contextVisible = true;
      const request = await fetch(
        serverAdress +
          `/documents/sentences/${id}/get_context/${this.displayOptionsSettings.sentencesInExpandedContext}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (request.ok) {
        const data = await request.json();
        this.context = data;
      } else {
        console.error(request);
      }
    },
  },
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
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
