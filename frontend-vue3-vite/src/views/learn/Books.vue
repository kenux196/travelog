<template>
  <h1>책 검색</h1>
  <input type="text" v-model="keyword" />
  <button @click="searchBook">검색</button>
  <div>
    <div v-if="hasBooks">
      <h4>검색 결과</h4>
      <p>검색 결과가 있습니다.</p>
      <table>
        <thead>
          <th>표지</th>
          <th>제목</th>
          <th>작가</th>
          <th>isbn</th>
          <th>가격</th>
        </thead>
        <tbody>
          <tr v-for="(book, index) in bookList" :key="index">
            <td>
              <img :src="book.thumbnail" />
            </td>
            <td>{{ book.title }}</td>
            <td>{{ getAuthors(book.authors) }}</td>
            <td>{{ book.isbn }}</td>
            <td>{{ book.price }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else>검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';

const keyword = ref('');
const bookList = ref('');

const hasBooks = computed(() => {
  console.log('book list = ' + bookList.value.length);
  return bookList.value.length > 0 ? true : false;
});

const searchBook = () => {
  if (keyword.value.length <= 0) {
    alert('검색할 책 정보를 입력하세요.');
    return;
  }
  console.log(keyword.value + ' 책을 검색합니다.');

  axios
    .get('https://dapi.kakao.com/v3/search/book', {
      params: {
        sort: 'accuracy', // latest
        page: 1,
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
      bookList.value = response.data.documents;
      console.log(bookList.value);
    })
    .catch((error) => {
      console.log(error);
    });
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
</script>
