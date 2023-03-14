<template>
  <div>
    <p>Vue.js 연습 페이지</p>
    <ul>
      <li><router-link to="/learn/learn-js">Learn JS</router-link></li>
      <li><router-link to="/learn/test01">test01</router-link></li>
      <li><router-link to="/learn/books">books</router-link></li>
      <li><router-link to="/learn/event">대구문화이벤트</router-link></li>
    </ul>
  </div>
</template>
<script setup>
import { RouterLink } from 'vue-router';
</script>
