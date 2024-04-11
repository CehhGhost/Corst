<template>
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      ref="tableRef"
      title="Articles"
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
          @click="goToUserPage(props.row.id)"
          style="cursor: pointer"
        >
          <q-item-label>{{ props.row.id }}</q-item-label>
        </q-td>
      </template>

      <template v-slot:body-cell-username="props">
        <q-td
          :props="props"
          @click="goToUserPage(props.row.id)"
          style="cursor: pointer"
        >
          <q-item-label>{{ props.row.username }}</q-item-label>
        </q-td>
      </template>
    </q-table>
    <div style="display: flex; justify-content: flex-end; margin-top: 20px">
      <q-btn
        unelevated
        color="primary"
        icon="add"
        :label="$t('add_user')"
        class="button"
        to="/admin/users/create"
      />
      <q-btn
        unelevated
        color="negative"
        icon="delete"
        class="button"
        :disabled="selected == null || selected.length === 0"
        style="margin-left: 10px; margin-right: 20px"
        @click="deleteUsers()"
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
        name: "username",
        label: "Username",
        align: "left",
        field: "username",
        sortable: true,
      },
      {
        name: "name",
        label: "Name",
        align: "center",
        field: "name",
        sortable: true,
      },
      {
        name: "surname",
        label: "Suranme",
        align: "center",
        field: "surname",
        sortable: true,
      },
      {
        name: "role",
        label: "Role",
        align: "center",
        field: "usersRole",
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
      visibleColumns: ref(["id", "username", "name", "surname", "role"]),
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
    async getAllUsers() {
      try {
        const response = await fetch(serverAdress + "/admin/users");
        const data = await response.json();
        return data;
      } catch (error) {
        console.error(error);
      }
    },
    getAllUserRows(data) {
      const rows = [];
      for (let i = 0; i < data.length; i++) {
        rows.push({
          id: data[i].id,
          username: data[i].username,
          name: data[i].name,
          surname: data[i].surname,
          usersRole: data[i].usersRole,
        });
      }
      return rows;
    },
    goToUserPage(id) {
      this.$router.push(`/admin/users/${id}`);
    },

    deleteUsers() {
      const confirmation =
        this.$i18n.locale === "ru"
          ? confirm("Вы уверены, что хотите удалить выбранных пользователей?")
          : confirm("Are you sure you want to delete users?");
      if (!confirmation) return;
      this.selected.forEach(async (user) => {
        try {
          const response = await fetch(
            serverAdress + "/admin/users/" + user.id,
            {
              method: "DELETE",
            }
          );
          this.rows = this.rows.filter((row) => !this.selected.includes(row));
          this.selected = [];
        } catch (error) {
          console.error(error);
        }
      });
    },
  },
  async mounted() {
    this.rows = this.getAllUserRows(await this.getAllUsers());
  },
};
</script>
