<template>
  <div class="create-article">
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
      <q-editor v-model="russianText" />
      <div class="text-label">Text in English:</div>
      <q-editor v-model="englishText" />
      <q-btn type="submit" label="Submit" color="primary" class="submit-btn" />
    </q-form>
    <h1></h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      date: new Date().toISOString().substr(0, 10),
      russianText: "",
      englishText: "",
    };
  },
  methods: {
    submitForm() {
      console.log("Submitted Data:");
      console.log("Date:", this.date);
      console.log("Russian Text:", this.russianText);
      console.log("English Text:", this.englishText);
      // TODO add further processing logic here
    },
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
