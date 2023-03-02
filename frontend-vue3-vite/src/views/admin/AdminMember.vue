<template>
  <h1>Admin 회원 관리 페이지</h1>
  <table>
    <thead>
      <th>이름</th>
      <th>이메일</th>
      <th>role</th>
      <th>등록일</th>
    </thead>
    <tbody>
      <tr v-for="member in members" :key="member.id">
        <td>{{ member.name }}</td>
        <td>{{ member.email }}</td>
        <td>{{ member.role }}</td>
        <td>{{ member.createDate }}</td>
      </tr>
    </tbody>
  </table>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from '../../stores/auth';

const members = ref(null);
const authStore = useAuthStore();

const options = {
  headers: {
    Authorization: `Bearer ${authStore.accessToken}`,
  },
};

async function getMembers() {
  axios.get('http://localhost:8080/admin/member', options).then((res) => {
    members.value = res.data;
  });
}

onMounted(() => {
  getMembers();
});
</script>
