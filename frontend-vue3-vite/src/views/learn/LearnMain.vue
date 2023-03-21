<template>
  <h1 class="text-white text-center font-extrabold font-serif bg-gray-800 py-5 text-3xl">Vue & Tailwindcss 연습공간</h1>
  <nav>
    <ul class="flex justify-center space-x-4">
      <li><router-link to="/learn/learn-js">JavaScript</router-link></li>
      <li><router-link to="/learn/vue-1">vue-1</router-link></li>
      <li><router-link to="/learn/vue-2">vue-2</router-link></li>
      <li><router-link to="/learn/vue-3">vue-3</router-link></li>
      <li><router-link to="/learn/pico">PicoCSS</router-link></li>
      <li><router-link to="/learn/tailwindcss">Tailwindcss</router-link></li>
      <li><router-link to="/learn/event">대구문화이벤트</router-link></li>
    </ul>
  </nav>
  <router-view></router-view>
</template>
<script setup>
import { RouterLink } from 'vue-router';
import router from '../../router';
</script>
<style scoped>
li {
  @apply font-bold text-lg;
}
</style>
