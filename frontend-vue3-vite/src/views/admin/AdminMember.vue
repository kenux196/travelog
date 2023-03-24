<template>
  <h1 class="text-center text-2xl font-semibold py-3">Admin 회원 관리 페이지</h1>
  <hr class="my-3" />
  <div class="text-center">
    <p class="text-xl font-bold my-3">전체 회원 리스트</p>
    <div class="mx-5">
      <table class="w-full">
        <thead class="bg-gray-700 text-white h-10">
          <th>#</th>
          <th>이름</th>
          <th>이메일</th>
          <th>role</th>
          <th>상태</th>
          <th>등록일</th>
        </thead>
        <tbody class="bg-gray-100">
          <tr v-for="member in members" :key="member.id" class="h-10 border-b-2">
            <td>{{ member.id }}</td>
            <td>{{ member.name }}</td>
            <td>{{ member.email }}</td>
            <td>{{ member.role }}</td>
            <td>{{ member.status }}</td>
            <td>{{ displayDate(member.joinDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { admin } from '../../api';

const members = ref(null);

async function getMembers() {
  admin.getMembers().then((data) => (members.value = data));
}
getMembers();

function displayDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleString();
}
</script>
