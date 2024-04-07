<template>
  <div class="create-article" style="margin-top: 70px" v-if="userStatus">
    <h2>Edit Article</h2>
    <q-form @submit="submitForm" class="form">
      <q-input filled v-model="date" mask="date" :rules="['date']">
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              cover
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date minimal v-model="date">
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
      <div class="text-label">Text in Russian:</div>
      <q-editor v-model="textRus" />
      <div class="text-label">Text in English:</div>
      <q-editor v-model="textEng" />
      <q-btn type="submit" label="Submit" color="primary" class="submit-btn" />
    </q-form>
    <h1></h1>
  </div>
</template>

<script>
import { ref } from "vue";
import { serverAdress } from "../global/globalVaribles.js";
import { isLogin } from "../global/globalFunctions.js";

export default {
  data() {
    return {
      textRus: "",
      textEng: "",
      date: null,

      userStatus: false,
    };
  },
  methods: {
    async loadArticle() {
      const response = await fetch(
        serverAdress + "/articles/" + this.$route.params.id
      );
      if (response.ok) {
        const article = await response.json();
        return article;
      } else {
        console.error(response);
      }
    },

    async submitForm() {
      this.userStatus = await isLogin();
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      if (!this.textRus || !this.textEng) {
        alert("Please fill all fields");
        return;
      }
      const response = await fetch(
        serverAdress + "/articles/" + this.$route.params.id,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("corst_token"),
          },
          body: JSON.stringify({
            date: this.date.toString().replace(/\//g, "-"),
            textRus: this.textRus,
            textEng: this.textEng,
          }),
        }
      );
      if (response.ok) {
        this.$router.push("/news");
      } else {
        console.error(response);
      }
    },
    parseArtice(data) {
      this.date = data.date;
      this.textRus = data.textRus;
      this.textEng = data.textEng;
    },
  },
  async mounted() {
    this.userStatus = await isLogin();
    if (!this.userStatus) {
      this.$router.push("/");
    } else {
      const article = await this.loadArticle();
      await this.parseArtice(article);
    }
  },
};
</script>

<style scoped>
.create-article {
  max-width: 600px;
  margin: 0 auto;
}

.create-article h2 {
  text-align: center;
}

.form {
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-btn {
  margin-top: 20px;
}
</style>
