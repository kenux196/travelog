<template>
  <div class="text-center mx-5">
    <div class="font-bold text-3xl text-orange-600">BookShelf</div>
    <div class="bg-slate-400">
      <table class="bg-slate-600 w-full">
        <thead class="border-b-4 border-stone-900 text-white">
          <tr>
            <th class="py-3">#</th>
            <th>책</th>
            <th>상태</th>
            <th>시작일</th>
            <th>종료일</th>
            <th>기간</th>
          </tr>
        </thead>
        <tbody class="bg-slate-400">
          <tr v-for="book in myBooks" :key="book.id" class="border-b-2">
            <td class="py-3">{{ book.id }}</td>
            <td>{{ book.bookInfo.title }}</td>
            <td>{{ book.bookStatus }}</td>
            <td>{{ book.startDate ? book.startDate : '-' }}</td>
            <td>{{ book.endDate }}</td>
            <td>{{ book.period }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { request } from '@/api';
import { useUserStore } from '../../stores/user';

const myBooks = ref('');
const userStore = useUserStore();
const requestMyBooks = async () => {
  myBooks.value = await request('get', '/api/book-logs', null, { memberId: userStore.id });
  console.log(myBooks.value);
};

requestMyBooks();
</script>

<style lang="scss" scoped></style>
