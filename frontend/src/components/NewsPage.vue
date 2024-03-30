<template>
  <div v-if="checkLogin()">
    <h1></h1>
    <q-btn
      push
      to="/createArticle"
      color="primary"
      label="Add news"
      class="button"
      size="large"
    />
  </div>
  <q-page-container>
    <q-page class="q-pa-md">
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-for="(article, i) in news" :key="i">
          <q-card class="rounded-borders">
            <q-card-section>
              <h3 class="text-h6">{{ article.date.split(" ")[0] }}</h3>
              <div class="row q-gutter-md items-center">
                {{ article.textRus }}
              </div>
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
      news: [],
      isLogin: this.checkLogin(),
    };
  },
  methods: {
    checkLogin() {
      if (localStorage.getItem("corst_token") == null) {
        return false;
      } else {
        //TODO Check expired token
        return true;
      }
    },
    async loadAllNews() {
      try {
        const response = await fetch("http://localhost:8081/articles", {
          method: "GET",
        });
        if (response.ok) {
          const data = await response.json();
          this.news = data;
        } else {
          console.log(response);
        }
      } catch (error) {
        console.error(error);
      }
    },
  },
  created() {
    this.loadAllNews();
  },
};
</script>

<style scoped></style>
