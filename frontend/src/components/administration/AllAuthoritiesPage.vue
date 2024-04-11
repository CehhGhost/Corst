<template>
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      ref="tableRef"
      title="Authorities"
      :rows="rows"
      :columns="cols"
      row-key="id"
      style="width: (100% - 200px); margin-left: 200px; margin-top: 50px"
    >
    </q-table>
  </div>
</template>

<script>
import { serverAdress } from "../../global/globalVaribles.js";
import { ref } from "vue";

export default {
  data() {
    const cols = [
      {
        name: "name",
        label: "Name",
        align: "left",
        field: "name",
        sortable: true,
      },
      {
        name: "description",
        label: "Description",
        align: "center",
        field: "description",
        sortable: true,
      },
    ];
    let rows = ref([]);

    return {
      cols,
      rows,
    };
  },
  methods: {
    async getAllAuthorities() {
      try {
        const response = await fetch(serverAdress + "/authorities", {
          method: "GET",
        });
        if (response.ok) {
          return await response.json();
        }
      } catch (error) {
        console.error(error);
      }
    },
    getAllAuthoritiesRows(data) {
      const rows = [];
      for (let i = 0; i < data.length; i++) {
        rows.push({
          name: data[i].name,
          description: data[i].description,
        });
      }
      return rows;
    },
  },
  async mounted() {
    this.rows = this.getAllAuthoritiesRows(await this.getAllAuthorities());
  },
};
</script>
