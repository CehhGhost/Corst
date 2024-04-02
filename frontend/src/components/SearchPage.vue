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
          <q-dialog v-model="displaySubcorpusModal" persistent>
            <q-card style="width: 1200px">
              <q-card-section class="row items-center q-pb-none">
                <label class="text-h6">Specify Subcorpus</label>
                <q-space />
                <q-btn icon="close" flat round dense v-close-popup />
              </q-card-section>
              <q-card-section class="row items-center">
                <hr />
                <div class="row">
                  <div>
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
                    <q-card outlined>
                      <q-card-section class="bg-primary text-white">
                        <div class="text-h6">Author Gender</div>
                      </q-card-section>

                      <q-card-actions>
                        <div>
                          <q-checkbox
                            v-model="subcorpusData.genders"
                            val="male"
                            label="Male"
                          ></q-checkbox>
                          <q-checkbox
                            v-model="subcorpusData.genders"
                            val="female"
                            label="Female"
                          ></q-checkbox>
                          <q-checkbox
                            v-model="subcorpusData.genders"
                            val="unknown"
                            label="Unknown"
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
                    class="row align-items-end"
                    v-for="(block, index) in lexgramBlocks"
                    :key="index"
                  >
                    <div class="col-3">
                      <label>
                        Wordform
                        <q-input
                          outlined
                          v-model="block.wordform"
                          placeholder="Wordform"
                          dense
                        />
                      </label>
                    </div>
                    <!-- Include lex select component here -->
                    <div class="col-3">
                      <label>
                        Part of speech
                        <q-input
                          outlined
                          v-model="block.partOfSpeech"
                          placeholder="Part of speech"
                          dense
                        />
                      </label>
                    </div>
                    <!-- Include gram select component here -->
                    <div class="col-3">
                      <label>
                        Grammar
                        <q-input
                          outlined
                          v-model="block.grammar"
                          placeholder="Grammar characteristics"
                          dense
                        />
                      </label>
                    </div>
                    <!-- Include err select component here -->
                    <div class="col-2">
                      <label>
                        Errors
                        <q-input
                          outlined
                          v-model="block.errors"
                          placeholder="Tags"
                          dense
                        />
                      </label>
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
        genders: [],
        domains: [],
        authorsCourses: [],
        authorsAcademicMajors: [],
      },

      showDeleteButton: true,
      searchResults: [],
    };
  },
  methods: {
    async exactSearch() {
      this.searchResults = [];
      const response = await fetch(serverAdress + "/documents/search/certain", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        //TODO Specify subcorpus
        body: JSON.stringify({
          wordform: this.exactSearchInput,
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
        from: "",
        to: "",
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
  margin-top: 23px;
  margin-left: 5px;
}
</style>
