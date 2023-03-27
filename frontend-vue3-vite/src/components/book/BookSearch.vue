<template>
  <div class="text-center">
    <div class="font-bold text-3xl my-4">도서 검색</div>
    <div class="flex justify-center">
      <input
        type="text"
        v-model="keyword"
        @keyup.enter="searchBook"
        placeholder="제목을 입력하세요"
        class="input input-bordered w-2/4"
      />
      <button @click="searchBook" class="btn ml-3">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
          />
        </svg>
      </button>
    </div>
  </div>
  <div class="text-center">
    <div class="divider my-5">검색 결과</div>
    <div v-if="hasBooks" class="mx-5">
      <div class="flex flex-wrap my-5">
        <div v-for="book in bookList" :key="book.id" class="mr-10 mb-10">
          <BookCard :book="book" @select:book="selectBook" />
        </div>
      </div>
      <div class="my-4">
        <button class="border-b-4 border-gray-400 text-gray-400 text-lg font-bold" v-show="!isEnd" @click="getMore">
          more
        </button>
      </div>
      <div class="my-4">
        <button @click="registerBook" class="btn px-10">책장에 담기</button>
      </div>
    </div>
    <div v-else class="font-bold text-lg">검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import BookGridItem from '@/components/book/BookGridItem.vue';
import { request } from '@/api';
import BookCard from './BookCard.vue';

const page = ref(1);
const bookList = ref([]);
const selectedBooks = ref([]);

const hasBooks = computed(() => {
  return bookList.value.length > 0 ? true : false;
});

const init = () => {
  page.value = 1;
  bookList.value.length = 0;
  selectedBooks.value.length = 0;
};

const keyword = ref('');
const searchBook = () => {
  if (keyword.value.length <= 0) {
    alert('검색할 책 정보를 입력하세요.');
    return;
  }
  init();
  getBooks();
};

const getMore = () => {
  page.value++;
  getBooks();
};

const isEnd = ref(false);
const getBooks = () => {
  request('get', '/api/books', null, null).then((data) => {
    console.log(data);
    bookList.value = data;
  });
};
init();
getBooks();

// 책 등록 api 호출.
const registerBook = async () => {
  const bookInfos = selectedBooks.value.map((book) => book.id);
  console.log(bookInfos);
  await request(
    'post',
    '/api/book-logs',
    {
      bookIds: bookInfos,
    },
    null,
  )
    .then(() => {
      alert('나의 책장에 담았습니다.');
    })
    .catch((e) => {
      console.log(e.message);
    });
};

const selectBook = (book, checked) => {
  if (checked) {
    selectedBooks.value.push(book);
  } else {
    const index = selectedBooks.value.findIndex((bookItem) => bookItem.id === book.id);
    console.log(index);
    selectedBooks.value.splice(index, 1);
  }
  // print();
};
</script>
<style scoped>
th {
  @apply py-3 text-center;
}
td {
  @apply px-2 py-2 text-left border-b;
}
</style>
