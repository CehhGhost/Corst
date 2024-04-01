<template>
  <div class="create-article" v-if="isLogin()">
    <h2>Create Article</h2>
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

export default {
  setup() {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = String(currentDate.getMonth() + 1).padStart(2, "0");
    const day = String(currentDate.getDate()).padStart(2, "0");
    return {
      date: ref(`${year}/${month}/${day}`),
    };
  },
  data() {
    return {
      textRus: "",
      textEng: "",
    };
  },
  methods: {
    isLogin() {
      if (localStorage.getItem("corst_token") == null) {
        return false;
      } else {
        //TODO Check expired token
        return true;
      }
    },
    async submitForm() {
      console.log(this.date.toString("yyyy-MM-dd"));
      const response = await fetch(serverAdress + "/articles/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("corst_token"),
        },
        body: JSON.stringify({
          date: this.date.toString().replace(/\//g, "-"),
          textRus: this.textRus,
          textEng: this.textEng,
        }),
      });
      if (response.ok) {
        this.$router.push("/news");
      } else {
        console.error(response);
      }
    },
  },
  mounted() {
    if (!this.isLogin()) {
      this.$router.push("/");
    }
  },
};
</script>

<style scoped>
.create-article {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.create-article h2 {
  text-align: center;
}

.form {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-btn {
  margin-top: 20px;
}
</style>
