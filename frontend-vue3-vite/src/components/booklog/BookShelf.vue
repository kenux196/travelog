<template>
  <div class="mx-5 w-full">
    <div class="text-center font-bold text-3xl text-slate-800 my-5">나의 책장</div>
    <div v-if="myBooks.length" class="flex flex-wrap mt-10">
      <div v-for="book in myBooks" :key="book.id" class="mr-10 mb-10">
        <img :src="book.bookInfo.thumbnail" class="w-full shadow-lg shadow-gray-600" />
        <div class="font-semibold">{{ book.bookInfo.title }}</div>
        <div class="text-xs">{{ book.bookStatus }}</div>
        <div class="text-xs">{{ book.startDate }}</div>
        <div class="text-xs">{{ book.endDate }}</div>
      </div>
    </div>
    <div v-else class="text-center text-xl font-bold text-slate-700">책창에 책이 없군요. 책을 추가해보세요.</div>
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
