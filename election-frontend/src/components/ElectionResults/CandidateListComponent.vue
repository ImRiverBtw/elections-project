<template>

  <Loading v-if="loading" />

  <ErrorComponent v-if="err" :err="err.message" />

  <div class="table" v-if="candidates.length > 0">
    <div class="header">
      <p>{{selectedPartyName}}</p>
    </div>
    <table>
      <tbody>
      <tr v-for="candidate in candidates" :key="candidate.id">
        <img src="@/assets/img/default.png" alt="" width="48px" height="48px">
        <th>{{candidate.name}}</th>
        <td>{{candidate.votes}} stemmen</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import {ref, onMounted, watch} from "vue";
import {useCandidateList} from "@/composables/useCandidateList.js";
import Loading from "@/components/Status/Loading.vue";
import ErrorComponent from "@/components/Status/ErrorComponent.vue";

const props = defineProps({
  selectedPartyId: {
    type: String,
    required: true,
  },
  selectedPartyName: {
    type: String,
    required: true
  }
});

const { candidates, err, loading, fetchCandidates } = useCandidateList();
const fetchCandidatesForSelectedParty = () => {
  fetchCandidates(props.selectedPartyId);
};

onMounted(fetchCandidatesForSelectedParty);
watch(() => props.selectedPartyId, fetchCandidatesForSelectedParty);
</script>

<style scoped>

.header {
  background-color: #B9C5E9;
  padding: 18px;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-weight: bold;
  margin-bottom: 4px;
  border-radius: 2px;
}
img{
  width: 50px;
  margin:2px;
  height: 50px;
  border-radius: 50%;
  align-self: center;
}
.table{
  filter: drop-shadow(0 0 0.15rem lightgrey);
  background-color: white;
  border-radius: 2px;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 75vh;
  width: 50%;
  box-sizing: border-box;
  border-collapse: collapse;
}
table {
  box-sizing: border-box;
  border-collapse: collapse;
}


tbody tr {
  border-top: none;
}

th {
  text-align: left;
  padding: 18px;
}

td {
  text-align: right;
  padding: 18px;
}
</style>