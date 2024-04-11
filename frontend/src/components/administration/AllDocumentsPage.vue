<template>
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      ref="tableRef"
      title="Documents"
      :rows="rows"
      :columns="cols"
      :visible-columns="visibleColumns"
      row-key="id"
      selection="multiple"
      v-model:selected="selected"
      @selection="handleSelection"
      style="width: (100% - 200px); margin-left: 200px; margin-top: 50px"
    >
      <template v-slot:top>
        <q-space />

        <q-select
          v-model="visibleColumns"
          multiple
          outlined
          dense
          options-dense
          :display-value="$q.lang.table.columns"
          emit-value
          map-options
          :options="cols"
          option-value="name"
          options-cover
          style="min-width: 150px"
        />
      </template>

      <template v-slot:header-selection="scope">
        <q-checkbox v-model="scope.selected" />
      </template>

      <template v-slot:body-selection="scope">
        <q-checkbox
          :model-value="scope.selected"
          @update:model-value="
            (val, evt) => {
              Object.getOwnPropertyDescriptor(scope, 'selected').set(val, evt);
            }
          "
        />
      </template>

      <template v-slot:body-cell-id="props">
        <q-td
          :props="props"
          @click="goToDocPage(props.row.id)"
          style="cursor: pointer"
        >
          <q-item-label>{{ props.row.id }}</q-item-label>
        </q-td>
      </template>

      <template v-slot:body-cell-title="props">
        <q-td
          :props="props"
          @click="goToDocPage(props.row.id)"
          style="cursor: pointer"
        >
          <q-item-label>{{ props.row.title }}</q-item-label>
        </q-td>
      </template>
    </q-table>
    <div style="display: flex; justify-content: flex-end; margin-top: 20px">
      <q-btn
        unelevated
        color="primary"
        icon="add"
        :label="$t('add_document')"
        class="button"
        to="/admin/documents/create"
      />
      <q-btn
        unelevated
        color="negative"
        icon="delete"
        class="button"
        :disabled="selected == null || selected.length === 0"
        style="margin-left: 10px; margin-right: 20px"
        @click="deleteDocuments()"
      />
    </div>
  </div>
</template>

<script>
import { serverAdress } from "../../global/globalVaribles.js";
import { ref, toRaw, nextTick } from "vue";

export default {
  data() {
    const cols = [
      {
        name: "id",
        label: "ID",
        align: "left",
        field: (row) => row.id,
        sortable: true,
      },
      {
        name: "title",
        label: "Title",
        align: "center",
        field: "title",
        sortable: true,
      },
      {
        name: "createdAt",
        label: "Created",
        align: "center",
        field: "createdAt",
        sortable: true,
      },
      {
        name: "genre",
        label: "Genre",
        align: "center",
        field: "genre",
        sortable: true,
      },
      {
        name: "domain",
        label: "Domain",
        align: "center",
        field: "domain",
        sortable: true,
      },
      {
        name: "authorsGender",
        label: "Gender",
        align: "center",
        field: "authorsGender",
        sortable: true,
      },
      {
        name: "authorsCourse",
        label: "Course",
        align: "center",
        field: "authorsCourse",
        sortable: true,
      },
      {
        name: "authorsAcademicMajor",
        label: "Acadeic Major",
        align: "center",
        field: "authorsAcademicMajor",
        sortable: true,
      },
      {
        name: "statusNum",
        label: "Status",
        align: "center",
        field: "statusNum",
        sortable: true,
      },
      {
        name: "ownerUsername",
        label: "Owner",
        align: "center",
        field: "ownerUsername",
        sortable: true,
      },
    ];
    let rows = ref([]);

    const tableRef = ref();
    const selected = ref([]);
    let storedSelectedRow;
    return {
      cols,
      rows,
      tableRef,
      selected,
      visibleColumns: ref([
        "id",
        "title",
        "createdAt",
        "genre",
        "domain",
        "authorsGender",
        "authorsCourse",
        "authorsAcademicMajor",
        "statusNum",
        "ownerUsername",
      ]),
      handleSelection({ rows, added, evt }) {
        if (rows.length !== 1 || evt === void 0) {
          return;
        }
        const oldSelectedRow = storedSelectedRow;
        const [newSelectedRow] = rows;
        const { ctrlKey, shiftKey } = evt;
        if (shiftKey !== true) {
          storedSelectedRow = newSelectedRow;
        }
        nextTick(() => {
          if (shiftKey === true) {
            const tableRows = tableRef.value.filteredSortedRows;
            let firstIndex = tableRows.indexOf(oldSelectedRow);
            let lastIndex = tableRows.indexOf(newSelectedRow);
            if (firstIndex < 0) {
              firstIndex = 0;
            }
            if (firstIndex > lastIndex) {
              [firstIndex, lastIndex] = [lastIndex, firstIndex];
            }
            const rangeRows = tableRows.slice(firstIndex, lastIndex + 1);
            const selectedRows = selected.value.map(toRaw);
            selected.value =
              added === true
                ? selectedRows.concat(
                    rangeRows.filter(
                      (row) => selectedRows.includes(row) === false
                    )
                  )
                : selectedRows.filter(
                    (row) => rangeRows.includes(row) === false
                  );
          } else if (ctrlKey !== true && added === true) {
            selected.value = [newSelectedRow];
          }
        });
      },
    };
  },
  methods: {
    async getAllDocuments() {
      try {
        const response = await fetch(serverAdress + "/documents", {
          method: "GET",
        });
        if (response.ok) {
          const data = await response.json();
          return data;
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },
    getAllDocumentRows(data) {
      const rows = [];
      for (let i = 0; i < data.length; i++) {
        rows.push({
          id: data[i].id,
          title: data[i].title,
          createdAt: new Date(data[i].createdAt).toLocaleString(),
          genre: data[i].genre,
          domain: data[i].domain,
          authorsGender: data[i].authorsGender[0],
          authorsCourse: data[i].authorsCourse,
          authorsAcademicMajor: data[i].authorsAcademicMajor,
          statusNum: data[i].statusNum,
          ownerUsername: data[i].ownerUsername,
        });
      }
      return rows;
    },
    goToDocPage(id) {
      this.$router.push(`/admin/documents/${id}`);
    },

    deleteDocuments() {
      const confirmation =
        this.$i18n.locale === "ru"
          ? confirm("Вы уверены, что хотите удалить документы?")
          : confirm("Are you sure you want to delete the documents?");
      if (!confirmation) return;
      for (let i = 0; i < this.selected.length; i++) {
        fetch(serverAdress + "/documents/delete/" + this.selected[i].id, {
          method: "DELETE",
        });
      }
      this.rows = this.rows.filter((row) => !this.selected.includes(row));
      this.selected = [];
    },
  },
  async mounted() {
    this.rows = this.getAllDocumentRows(await this.getAllDocuments());
  },
};
</script>
