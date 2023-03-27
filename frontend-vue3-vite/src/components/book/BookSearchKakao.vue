<template>
  <div class="text-center">
    <div class="font-bold text-3xl my-4">책 검색(with kakao)</div>
    <div class="flex justify-center">
      <input
        type="text"
        v-model="keyword"
        @keyup.enter="searchBook"
        placeholder="검색할 도서의 제목을 입력하세요"
        class="input input-bordered w-2/4"
      />
      <button @click="searchBook" class="btn mx-2">
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
    <div class="divider">검색 결과</div>
    <div v-if="hasBooks" class="mx-5">
      <div class="flex flex-wrap mt-10">
        <div v-for="book in bookList" :key="book.id" class="mr-10 mb-10">
          <!-- <BookGridItem :book="book" @select:book="selectBook" /> -->
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
import axios from 'axios';
import { computed, ref } from 'vue';
import BookGridItem from '@/components/book/BookGridItem.vue';
import BookCard from './BookCard.vue';

const page = ref(1);
const bookList = ref([]);
const selectedBooks = ref([]);

const hasBooks = computed(() => {
  return bookList.value.length > 0 ? true : false;
});

const init = () => {
  id.value = 0;
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
  getBookInfoFromKakao();
};

const getMore = () => {
  page.value++;
  getBookInfoFromKakao();
};

const isEnd = ref(false);
const getBookInfoFromKakao = () => {
  axios
    .get('https://dapi.kakao.com/v3/search/book', {
      params: {
        sort: 'accuracy', // latest
        page: page.value,
        size: 10,
        target: 'title',
        query: keyword.value,
      },
      headers: {
        Authorization: 'KakaoAK ' + import.meta.env.VITE_KAKAO_REST_API_KEY,
      },
    })
    .then((response) => {
      console.log(response);
      insertBookData(response.data.documents);
      isEnd.value = response.data.meta.is_end;
      console.log(bookList.value);
    })
    .catch((error) => {
      console.log(error);
    });
};

const id = ref(0);
const insertBookData = (books) => {
  books.forEach((book) => {
    book.id = ++id.value;
    book.authors = getAuthors(book.authors);
    book.isbn = getIsbn(book.isbn);
    book.publishedDate = book.datetime;
    bookList.value.push(book);
  });
  console.log(bookList.value);
};

const getAuthors = (authorList) => {
  let authors = '';
  let isFirst = true;
  for (const index in authorList) {
    if (isFirst) {
      authors += authorList[index];
      isFirst = false;
    } else {
      authors += ', ' + authorList[index];
    }
  }
  return authors;
};

const getIsbn = (isbn) => {
  const isbns = isbn.split(' ');
  if (isbns.length < 2) {
    return isbns[0];
  }
  return isbns[1];
};

// 책 등록 api 호출.
const registerBook = () => {
  const bookInfos = selectedBooks.value;
  axios
    .post('/api/books', { bookInfos })
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
