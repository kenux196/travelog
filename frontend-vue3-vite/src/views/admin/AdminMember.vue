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
import { admin } from '../../api';

const members = ref(null);

async function getMembers() {
  admin.getMembers().then((data) => (members.value = data));
}

onMounted(() => {
  getMembers();
});

function displayDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleString();
}
</script>
