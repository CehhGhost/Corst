<template>
  <q-page-container>
    <q-page class="q-pa-md">
      <div class="row q-gutter-md justify-center">
        <div class="col-lg-6 col-md-8 col-sm-10">
          <h3></h3>
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
          <h3></h3>
          <q-card class="rounded-borders">
            <q-card-section class="lexgrammSearch">
              <h3 class="text-h6">Lexgramm Search</h3>
              <q-form @submit="lexgrammSearch" class="q-gutter-md">
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
        </div>
      </div>
    </q-page>
  </q-page-container>
</template>

<script>
export default {
  data() {
    return {
      exactSearchInput: "",
      lexgramBlocks: [],
      showDeleteButton: true,
      displaySettingsModal: false,
      subcorpusModal: false,
    };
  },
  methods: {
    exactSearch() {},
    lexgrammSearch() {},
    addLexgramBlock() {
      this.lexgramBlocks.push({
        wordform: "",
        partOfSpeech: "",
        grammar: "",
        errors: "",
      });
      if (this.lexgramBlocks.length > 1) {
        this.showDeleteButton = true;
      }

      this.lexgramBlocks.push({
        customContent: lexgramFormContent,
      });
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
</style>
