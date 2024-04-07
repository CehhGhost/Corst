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
            label="Add article"
            class="button"
            size="large"
          />
        </div>
      </div>
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-if="articles.length === 0" class="text-center text-grey-8">
          No documents found.
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
              <div class="row q-gutter-xs items-center">
                {{ article.textRus }}
              </div>

              <div class="q-pa-xs">
                <div class="row justify-between">
                  <div class="col-auto"></div>
                  <div class="row-auto">
                    <q-btn
                      no-caps
                      unelevated
                      color="secondary"
                      icon="edit"
                      label="Edit"
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
        const confirmation = confirm(
          "Are you sure you want to delete this article?"
        );
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
  },
  async mounted() {
    this.userStatus = await isLogin();
    this.loadAllArticles();
  },
};
</script>

<style scoped></style>
