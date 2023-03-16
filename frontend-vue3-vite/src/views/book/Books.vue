<template>
  <h1>책 검색</h1>
  <input type="search" v-model="keyword" />
  <button @click="searchBook">검색</button>
  <div>
    <div v-if="hasBooks">
      <h4>검색 결과</h4>
      <p>검색 결과가 있습니다.</p>
      <table>
        <thead>
          <th><input type="checkbox" /></th>
          <th>표지</th>
          <th>제목</th>
          <th>작가</th>
          <th>isbn</th>
          <th>발행일</th>
          <th>출판사</th>
        </thead>
        <tbody>
          <tr v-for="book in bookList" :key="book.id">
            <td>
              <input type="checkbox" :id="'check_' + book.id" v-model="book.selected" />
            </td>
            <td>
              <img :src="book.thumbnail" />
            </td>
            <td>{{ book.title }}</td>
            <td>{{ book.authors }}</td>
            <td>{{ book.isbn }}</td>
            <td>{{ getPublishDate(book.datetime) }}</td>
            <td>{{ book.publisher }}</td>
          </tr>
        </tbody>
      </table>
      <p role="button" @click="registerBook">등록</p>
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
      let id = 0;
      bookList.value.forEach((book) => {
        book.id = ++id;
        book.authors = getAuthors(book.authors);
        book.selected = false;
        book.isbn = getIsbn(book.isbn);
      });
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

const getPublishDate = (datetime) => {
  return new Date(datetime).toLocaleDateString();
};

const getIsbn = (isbn) => {
  const isbns = isbn.split(' ');
  if (isbns.length < 2) {
    return isbns[0];
  }
  return isbns[1];
};

const registerBook = () => {
  // 책 등록 api 호출.
  const selectdBooks = bookList.value.filter((book) => book.selected);
  console.log(JSON.stringify(selectdBooks));
  console.log(selectdBooks);
};
</script>
