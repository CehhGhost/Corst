<template>
  <h3></h3>
  <q-page-container>
    <q-page class="q-pa-md">
      <div class="col-lg-6 col-md-8 col-sm-10">
        <div v-for="(document, i) in documents" :key="i">
          <q-card class="rounded-borders">
            <q-card-section>
              <h3 class="text-h6">{{ document.name }}</h3>
              <div class="row q-gutter-md items-center">
                {{ document.text }}
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
      documents: [
        {
          name: "Hello",
          text: "World",
        },
      ],
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
    async loadAllDocuments() {
      try {
        const response = await fetch("http://localhost:8081/documents", {
          method: "GET",
        });
        if (response.ok) {
          const data = await response.json();
          console.log(data);
        } else {
          console.log("error");
          alert("Error connecting to server. Please try again.");
          router.push("/");
        }
      } catch (error) {
        console.error("Error during login:", error);
      }
    },
  },
  mounted() {
    if (this.isLogin) {
    } else {
      alert("No");
      this.$router.push("/");
    }
  },
};
</script>
