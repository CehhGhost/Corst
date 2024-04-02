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
            <q-card>
              <q-card-section class="row items-center">
                <h4 class="search">Выбрать подкорпус</h4>
                <div class="conatiner mx-4">
                  <h4>Период</h4>
                  <div class="row mb-2 subcorpus-bg">
                    <div class="col-sm-1 mt-1">
                      <h4 style="font-size: 20px">От</h4>
                    </div>
                    <div class="col-sm-3">
                      <label>
                        <input
                          name="years1"
                          type="text"
                          class="form-control input-sm"
                          placeholder="2014"
                          size="100%"
                        />
                      </label>
                    </div>
                    <div class="col-sm-1 mt-1">
                      <h4 style="font-size: 20px">до</h4>
                    </div>
                    <div class="col-sm-3">
                      <label>
                        <input
                          name="years2"
                          type="text"
                          class="form-control input-sm"
                          placeholder="2015"
                          size="100%"
                        />
                      </label>
                    </div>
                  </div>
                  <h4>Пол автора</h4>
                  <div class="row mb-2 subcorpus-bg">
                    <div class="col-sm-4">
                      <input type="radio" name="genAnswer" value=" " />
                      <label>Любой</label>
                    </div>
                    <div class="col-sm-4">
                      <input type="radio" name="genAnswer" value="м" />
                      <label>Мужской</label>
                    </div>
                    <div class="col-sm-4">
                      <input type="radio" name="genAnswer" value="ж" />
                      <label>Женский</label>
                    </div>
                  </div>
                  <h4>Курс</h4>
                  <div class="row mb-2 subcorpus-bg">
                    <div class="col-sm-6">
                      <input
                        type="checkbox"
                        class="chk-all"
                        name="cas"
                        value="all"
                        data-group="g1"
                      />
                      <label>Выбрать все</label><br />

                      <input
                        type="checkbox"
                        name="cas6"
                        value="s6"
                        data-group="g1"
                      />
                      <label>6 курс специалитет</label><br />

                      <input
                        type="checkbox"
                        name="cas5"
                        value="s5"
                        data-group="g1"
                      />
                      <label>5 курс специалитет</label><br />

                      <input
                        type="checkbox"
                        name="cas4"
                        value="s4"
                        data-group="g1"
                      />
                      <label>4 курс специалитет</label><br />

                      <input
                        type="checkbox"
                        name="cas3"
                        value="s3"
                        data-group="g1"
                      />
                      <label>3 курс специалитет</label><br />

                      <input
                        type="checkbox"
                        name="cas2"
                        value="s2"
                        data-group="g1"
                      />
                      <label>2 курс специалитет</label><br />

                      <input
                        type="checkbox"
                        name="cas1"
                        value="s1"
                        data-group="g1"
                      />
                      <label>1 курс специалитет</label>
                    </div>
                    <div class="col-sm-6">
                      <input
                        type="checkbox"
                        name="cab4"
                        value="b4"
                        data-group="g1"
                      />
                      <label>4 курс бакалавриат</label><br />

                      <input
                        type="checkbox"
                        name="cab3"
                        value="b3"
                        data-group="g1"
                      />
                      <label>3 курс бакалавриат</label><br />

                      <input
                        type="checkbox"
                        name="cab2"
                        value="b2"
                        data-group="g1"
                      />
                      <label>2 курс бакалавриат</label><br />

                      <input
                        type="checkbox"
                        name="cab1"
                        value="b1"
                        data-group="g1"
                      />
                      <label>1 курс бакалавриат</label>
                    </div>
                  </div>
                  <h4>Тип работы</h4>
                  <div class="row mb-2 subcorpus-bg">
                    <div class="col-sm-6">
                      <input type="checkbox" class="chk-all" data-group="g2" />
                      <label>Выбрать все</label><br />

                      <input
                        type="checkbox"
                        name="conference"
                        data-group="g2"
                      />
                      <label>Обзор конференции</label><br />

                      <input
                        type="checkbox"
                        name="course_work"
                        data-group="g2"
                      />
                      <label>Курсовая работа</label><br />

                      <input
                        type="checkbox"
                        name="аutobiography"
                        data-group="g2"
                      />
                      <label>Автобиография</label><br />

                      <input type="checkbox" name="VKR" data-group="g2" />
                      <label>ВКР</label><br />

                      <input type="checkbox" name="statement" data-group="g2" />
                      <label>Заявление</label><br />

                      <input type="checkbox" name="essay" data-group="g2" />
                      <label>Эссе</label><br />

                      <input
                        type="checkbox"
                        name="annotation"
                        data-group="g2"
                      />
                      <label>Аннотация</label><br />
                    </div>
                    <div class="col-sm-6">
                      <input type="checkbox" name="paragraph" data-group="g2" />
                      <label>Абзац</label><br />

                      <input
                        type="checkbox"
                        name="course_work_draft"
                        data-group="g2"
                      />
                      <label>Курсовая работа (черновик)</label><br />

                      <input
                        type="checkbox"
                        name="semester_work"
                        data-group="g2"
                      />
                      <label>Семестровая работа</label><br />

                      <input
                        type="checkbox"
                        name="project_summary"
                        data-group="g2"
                      />
                      <label>Аннотация проекта</label><br />

                      <input type="checkbox" name="diploma" data-group="g2" />
                      <label>Диплом</label><br />

                      <input
                        type="checkbox"
                        name="commercial_offer"
                        data-group="g2"
                      />
                      <label>Коммерческое предложение</label><br />

                      <input type="checkbox" name="abstract" data-group="g2" />
                      <label>Реферат</label><br />
                    </div>
                  </div>
                  <h4>Специальность автора</h4>
                  <div class="row mb-2 subcorpus-bg">
                    <div class="col-sm-6">
                      <input type="checkbox" class="chk-all" data-group="g3" />
                      <label>Выбрать все</label><br />

                      <input
                        type="checkbox"
                        name="Культурология"
                        data-group="g3"
                      />
                      <label>Культурология</label><br />

                      <input type="checkbox" name="Экономика" data-group="g3" />
                      <label>Экономика</label><br />

                      <input type="checkbox" name="История" data-group="g3" />
                      <label>История</label><br />

                      <input
                        type="checkbox"
                        name="Журналистика"
                        data-group="g3"
                      />
                      <label>Журналистика</label><br />

                      <input type="checkbox" name="Дизайн" data-group="g3" />
                      <label>Дизайн</label><br />

                      <input
                        type="checkbox"
                        name="Востоковедение"
                        data-group="g3"
                      />
                      <label>Востоковедение</label><br />

                      <input
                        type="checkbox"
                        name="Медиакоммуникации"
                        data-group="g3"
                      />
                      <label>Медиакоммуникации</label><br />

                      <input
                        type="checkbox"
                        name="Политология"
                        data-group="g3"
                      />
                      <label>Политология</label><br />

                      <input
                        type="checkbox"
                        name="Лингвистика"
                        data-group="g3"
                      />
                      <label>Лингвистика</label><br />

                      <input
                        type="checkbox"
                        name="Юриспруденция"
                        data-group="g3"
                      />
                      <label>Юриспруденция</label><br />

                      <input
                        type="checkbox"
                        name="Менеджмент"
                        data-group="g3"
                      />
                      <label>Менеджмент</label><br />
                    </div>
                    <div class="col-sm-6">
                      <input
                        type="checkbox"
                        name="Социология"
                        data-group="g3"
                      />
                      <label>Социология</label><br />

                      <input type="checkbox" name="Логистика" data-group="g3" />
                      <label>Логистика</label><br />

                      <input
                        type="checkbox"
                        name="Математика"
                        data-group="g3"
                      />
                      <label>Математика</label><br />

                      <input
                        type="checkbox"
                        name="Иностранные языки"
                        data-group="g3"
                      />
                      <label>Иностранные языки</label><br />

                      <input
                        type="checkbox"
                        name="Мировая экономика"
                        data-group="g3"
                      />
                      <label>Мировая экономика</label><br />

                      <input
                        type="checkbox"
                        name="Современное искусство"
                        data-group="g3"
                      />
                      <label>Современное искусство</label><br />

                      <input
                        type="checkbox"
                        name="Прикладная математика и информатика"
                        data-group="g3"
                      />
                      <label>Прикладная математика и информатика</label><br />

                      <input
                        type="checkbox"
                        name="Реклама и связи с общественностью"
                        data-group="g3"
                      />
                      <label>Реклама и связи с общественностью</label><br />

                      <input
                        type="checkbox"
                        name="Психология"
                        data-group="g3"
                      />
                      <label>Психология</label><br />

                      <input
                        type="checkbox"
                        name="История искусств"
                        data-group="g3"
                      />
                      <label>История искусств</label><br />

                      <input type="checkbox" name="Филология" data-group="g3" />
                      <label>Филология</label><br />
                    </div>
                  </div>
                  <h4>Научная область</h4>
                  <div class="row mb-2 subcorpus-bg">
                    <div class="col-sm-6">
                      <input type="checkbox" class="chk-all" data-group="g4" />
                      <label>Выбрать все</label><br />

                      <input
                        type="checkbox"
                        name="политология_d"
                        data-group="g4"
                      />
                      <label>Политология</label><br />

                      <input
                        type="checkbox"
                        name="психология_d"
                        data-group="g4"
                      />
                      <label>Психология</label><br />

                      <input
                        type="checkbox"
                        name="разнородная_тематика_d"
                        data-group="g4"
                      />
                      <label>Разнородная тематика</label><br />

                      <input
                        type="checkbox"
                        name="менеджмент_d"
                        data-group="g4"
                      />
                      <label>Менеджмент</label><br />

                      <input
                        type="checkbox"
                        name="журналистика_d"
                        data-group="g4"
                      />
                      <label>Журналистика</label><br />

                      <input type="checkbox" name="право_d" data-group="g4" />
                      <label>Право</label><br />
                    </div>
                    <div class="col-sm-6">
                      <input
                        type="checkbox"
                        name="cоциология_d"
                        data-group="g4"
                      />
                      <label>Социология</label><br />

                      <input
                        type="checkbox"
                        name="риторика_d"
                        data-group="g4"
                      />
                      <label>Риторика</label><br />

                      <input
                        type="checkbox"
                        name="cоциолингвистика_d"
                        data-group="g4"
                      />
                      <label>Социолингвистика</label><br />

                      <input
                        type="checkbox"
                        name="лингвистика_d"
                        data-group="g4"
                      />
                      <label>Лингвистика</label><br />

                      <input
                        type="checkbox"
                        name="образование_d"
                        data-group="g4"
                      />
                      <label>Образование</label><br />

                      <input
                        type="checkbox"
                        name="филология_d"
                        data-group="g4"
                      />
                      <label>Филология</label><br />
                    </div>
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
