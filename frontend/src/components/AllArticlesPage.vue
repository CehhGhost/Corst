<template>
  <q-page-container>
    <q-page class="q-pa-xs" style="max-width: 1000px; margin: 0 auto">
      <h1></h1>
      <div v-if="userStatus">
        <div class="q-gutter-xs">
          <q-btn
            push
            to="/createArticle"
            color="primary"
            icon="add"
            :label="$t('add_article')"
            class="button"
            size="large"
          />
        </div>
      </div>
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-if="!loadingComplete" class="text-center text-grey-8">
          {{ $t("loading") }}
          <q-spinner color="primary" size="3em" :thickness="2" />
        </div>
        <div v-else>
          <div v-if="articles.length === 0" class="text-center text-grey-8">
            {{ $t("no_articles_found") }}
          </div>
          <div v-else v-for="(article, i) in articles" :key="i">
            <q-card
              class="rounded-borders"
              style="margin-top: 20px"
              flat
              bordered
            >
              <q-card-section>
                <h3 class="text-h6">{{ article.date.split(" ")[0] }}</h3>
                <div
                  class="row q-gutter-xs items-center"
                  v-html="getCurrentArticleText(article)"
                ></div>

                <div class="q-pa-xs">
                  <div class="row justify-between">
                    <div class="col-auto"></div>
                    <div class="row-auto">
                      <q-btn
                        no-caps
                        unelevated
                        color="secondary"
                        icon="edit"
                        :label="$t('edit')"
                        class="button"
                        style="margin-right: 10px"
                        :to="'/editArticle/' + article.id"
                      />
                      <q-btn
                        unelevated
                        color="negative"
                        icon="delete"
                        class="button"
                        @click="deleteArticle(article.id)"
                      />
                    </div>
                  </div>
                </div>
              </q-card-section>
            </q-card>
          </div>
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
      articles: [],

      loadingComplete: false,
      userStatus: false,
    };
  },
  methods: {
    async loadAllArticles() {
      try {
        const response = await fetch(serverAdress + "/articles", {
          method: "GET",
        });
        if (response.ok) {
          const data = await response.json();
          this.articles = data;
        } else {
          console.log(response);
        }
      } catch (error) {
        console.error(error);
      }
    },
    async deleteArticle(id) {
      try {
        if (!this.userStatus) {
          this.$router.push("/login");
          return;
        }
        const confirmation =
          this.$i18n.locale === "ru"
            ? confirm("Вы уверены, что хотите удалить статью?")
            : confirm("Are you sure you want to delete the article?");
        if (!confirmation) return;
        const response = await fetch(serverAdress + "/articles/" + id, {
          method: "DELETE",
        });
        if (response.ok) {
          this.loadAllArticles();
        } else {
          console.error(response);
        }
      } catch (error) {
        console.error(error);
      }
    },
    getCurrentArticleText(article) {
      return this.$i18n.locale === "en" ? article.textEng : article.textRus;
    },
  },
  async mounted() {
    this.userStatus = await isLogin();
    await this.loadAllArticles();
    this.loadingComplete = true;
  },
};
</script>
