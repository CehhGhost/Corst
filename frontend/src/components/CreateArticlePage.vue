<template>
  <div class="create-article" style="margin-top: 70px" v-if="userStatus">
    <h3>
      {{ $t("create_article") }}
    </h3>
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
      <div class="text-label">{{ $t("text_in_russian") }}:</div>
      <q-editor v-model="textRus" />
      <div class="text-label">{{ $t("text_in_english") }}:</div>
      <q-editor v-model="textEng" />
      <q-btn
        type="submit"
        :label="$t('submit')"
        color="primary"
        class="submit-btn"
      />
    </q-form>
    <h1></h1>
  </div>
</template>

<script>
import { ref } from "vue";
import { serverAdress } from "../global/globalVaribles.js";
import { checkAuthorities } from "../global/globalFunctions.js";

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

      userStatus: false,
    };
  },
  methods: {
    async submitForm() {
      this.userStatus = await checkAuthorities("CREATE_UPDATE_DELETE_ARTICLES");
      if (!this.userStatus) {
        this.$router.push("/login");
      }
      if (!this.textRus || !this.textEng) {
        if (this.$i18n.locale == "ru") alert("Пожалуйста, заполните все поля");
        else alert("Please fill all fields");
        return;
      }
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
  async mounted() {
    if (localStorage.getItem("corst_locale")) {
      this.$i18n.locale = localStorage.getItem("corst_locale");
    }
    this.userStatus = await checkAuthorities("CREATE_UPDATE_DELETE_ARTICLES");
    if (!this.userStatus) {
      this.$router.push("/");
    }
  },
};
</script>

<style scoped>
.create-article {
  max-width: 600px;
  margin: 0 auto;
}

.create-article h3 {
  text-align: center;
}

.form {
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-btn {
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 10px;
}
</style>
