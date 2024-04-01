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
                @click="openSubcorpusModal"
              />
              <q-btn
                label="Display Options"
                color="primary"
                @click="openDisplaySettingsModal"
              />
            </q-btn-group>
          </div>
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

export default {
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
      showDeleteButton: true,
      displaySettingsModal: false,
      subcorpusModal: false,
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
    openDisplaySettingsModal() {
      this.displaySettingsModal = true;
    },
    closeDisplaySettingsModal() {
      this.displaySettingsModal = false;
    },
    openSubcorpusModal() {
      this.subcorpusModal = true;
    },
    closeSubcorpusModal() {
      this.subcorpusModal = false;
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
