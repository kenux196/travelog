<template>
  <h1>Admin 회원 관리 페이지</h1>
  <table>
    <thead>
      <th>#</th>
      <th>이름</th>
      <th>이메일</th>
      <th>role</th>
      <th>상태</th>
      <th>등록일</th>
    </thead>
    <tbody>
      <tr v-for="member in members" :key="member.id">
        <td>{{ member.id }}</td>
        <td>{{ member.name }}</td>
        <td>{{ member.email }}</td>
        <td>{{ member.role }}</td>
        <td>{{ member.status }}</td>
        <td>{{ displayDate(member.joinDate) }}</td>
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
  axios.get('/api/admin/member', options).then((res) => {
    members.value = res.data;
  });
}

onMounted(() => {
  getMembers();
});

function displayDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleString();
}
</script>
