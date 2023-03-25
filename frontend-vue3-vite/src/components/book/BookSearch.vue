<template>
  <div class="text-center">
    <div class="font-bold text-3xl my-4">책</div>
    <div>
      <input
        type="text"
        v-model="keyword"
        @keyup.enter="searchBook"
        class="border border-solid border-gray-500 py-2 w-2/4 indent-2"
      />
      <button @click="searchBook" class="text-white rouned bg-slate-800 hover:bg-slate-700 p-2 w-1/6 ml-1">검색</button>
    </div>
    <hr class="font-bold my-5 mx-5" />
  </div>
  <div class="text-center">
    <div v-if="hasBooks" class="mx-5">
      <div class="flex flex-wrap mt-10">
        <div v-for="book in bookList" :key="book.id" class="mr-10 mb-10">
          <BookGridItem :book="book" @select:book="selectBook" />
        </div>
      </div>
      <div class="my-4">
        <button class="border-b-4 border-gray-400 text-gray-400 text-lg font-bold" v-show="!isEnd" @click="getMore">
          more
        </button>
      </div>
      <div class="my-4">
        <button @click="registerBook" class="bg-slate-800 hover:bg-slate-700 rounded-lg text-white py-2 px-10">
          책장에 담기
        </button>
      </div>
    </div>
    <div v-else class="font-bold text-lg">검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import BookGridItem from '@/components/book/BookGridItem.vue';
import { request } from '@/api';

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
      bookId: bookInfos,
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
