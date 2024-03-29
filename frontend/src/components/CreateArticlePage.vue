<template>
  <div class="create-article">
    <h2>Create Article</h2>
    <q-form @submit="submitForm" class="form">
      <q-date
        minimal
        v-model="selectedDate"
        label="Select Date"
        ref="dateInput"
      />
      <q-editor v-model="russianText" label="Text in Russian" />
      <q-editor v-model="englishText" label="Text in English" />
      <q-btn type="submit" label="Submit" color="primary" class="submit-btn" />
    </q-form>
    <h1></h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedDate: new Date().toISOString().substr(0, 10),
      russianText: "",
      englishText: "",
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.$refs.dateInput.$emit("input", this.selectedDate);
    });
  },
  methods: {
    submitForm() {
      console.log("Submitted Data:");
      console.log("Date:", this.selectedDate);
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

.q-date,
.q-editor {
  margin-bottom: 20px;
}

.submit-btn {
  margin-top: 20px;
}
</style>
