<template>
  <nav>
    <ul>
      <ul>
        <li><strong>Vue 연습</strong></li>
      </ul>
      <li><router-link to="/learn/learn-js">JavaScript</router-link></li>
      <li><router-link to="/learn/vue-1">vue-1</router-link></li>
      <li><router-link to="/learn/vue-2">vue-2</router-link></li>
      <li><router-link to="/learn/vue-3">vue-3</router-link></li>
      <li><router-link to="/learn/event">대구문화이벤트</router-link></li>
    </ul>
  </nav>
  <router-view></router-view>
</template>
<script setup>
import { RouterLink } from 'vue-router';
</script>
<style scoped></style>
