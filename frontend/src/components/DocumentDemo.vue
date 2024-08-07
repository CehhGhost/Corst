<template>
  <div class="q-pa-md"></div>
</template>

<script>
import { getAuthorities } from "src/global/globalFunctions";

export default {
  setup() {},
  mounted() {
    getAuthorities();
  },
};
</script>
