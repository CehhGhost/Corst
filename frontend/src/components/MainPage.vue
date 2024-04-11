<template>
  <div>
    <h1 style="max-width: 1200px; margin: 0 auto; margin-top: 50px">CoRST</h1>
    <p
      class="text-h6 q-mt-xs"
      style="max-width: 1200px; margin: 0 auto; padding: 10px"
    >
      Corpus of Russian Student Texts
    </p>

    <q-card
      flat
      bordered
      v-for="(section, index) in sections"
      :key="index"
      class="q-mt-md"
      style="max-width: 1200px; margin: 0 auto; padding: 10px"
    >
      <q-card-section v-if="$i18n.locale === 'ru'">
        <h3>{{ section.headerRus }}</h3>
        <label class="info-value" v-html="section.textRus" />
        <!-- TODO Add html tag -->
      </q-card-section>
      <q-card-section v-else>
        <h3>{{ section.headerEng }}</h3>
        <label v-html="section.textEng" />
      </q-card-section>
    </q-card>
  </div>
</template>

<script>
import { serverAdress } from "src/global/globalVaribles";

export default {
  data() {
    return {
      sections: [],
    };
  },
  methods: {
    async getSections() {
      const response = await fetch(`${serverAdress}/sections`);
      const data = await response.json();
      this.sections = data;
    },
  },
  mounted() {
    this.getSections();
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
  },
};
</script>

<style>
.info-value {
  font-size: 16px;
  white-space: pre-line;
}
</style>
