<template>
  <div class="test">
    <h1>This is Vue.js & Pico.css Test Page.</h1>

    <h3>buttons</h3>
    <button>default</button>

    <h3>Table</h3>
    <div class="grid">
      <div>
        <details role="list">
          <summary aria-haspopup="listbox">장르</summary>
          <ul role="listbox">
            <li><a>인문</a></li>
            <li><a>IT</a></li>
            <li><a>과학</a></li>
          </ul>
        </details>
      </div>
      <div><input type="text" placeholder="검색어를 입력하세요" /></div>
      <button type="submit">검색</button>
    </div>
    <table>
      <thead>
        <th>제목</th>
        <th>저자</th>
        <th>등록일</th>
      </thead>
      <tbody>
        <tr v-for="book in testData.books" :key="book.id">
          <td>{{ book.title }}</td>
          <td>{{ book.author }}</td>
          <td>{{ book.createDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <router-link to="/">home</router-link>
</template>
<script setup>
import { RouterLink } from 'vue-router';
import { reactive } from 'vue';

const testData = reactive({
  books: [
    {
      id: 1,
      title: '해방전선',
      author: '윤작가',
      createDate: '2020-02-04',
    },
    {
      id: 2,
      title: '시간은 돈이다. 잘 사용해라',
      author: '윤상규',
      createDate: '2020-02-04',
    },
    {
      id: 3,
      title: '오늘만 사는 놈은 서행차선이다.',
      author: '윤상규',
      createDate: '2020-02-03',
    },
  ],
});
</script>
