<template>
  <h1 class="text-white text-center font-extrabold font-serif bg-gray-800 py-5 text-3xl">Vue & Tailwindcss 연습공간</h1>
  <nav>
    <ul class="flex bg-slate-600 py-4 text-white justify-center">
      <li v-for="menu in menus" :key="menu.to">
        <button v-if="menu.active" @click="move(menu)" class="mx-2">{{ menu.name }}</button>
      </li>
    </ul>
  </nav>
  <div class="mt-5 mx-10">
    <router-view />
  </div>
</template>
<script setup>
import router from '../../router';

const menus = [
  {
    to: '/learn/learn-js',
    name: 'JavaScript',
    active: false,
  },
  {
    to: '/learn/todo-list',
    name: 'todo-list',
    active: true,
  },
  {
    to: '/learn/vue-2',
    name: 'vue연습',
    active: true,
  },
  {
    to: '/learn/vue-3',
    name: 'vue-3',
    active: false,
  },
  {
    to: '/learn/tailwindcss',
    name: 'Tailwindcss',
    active: true,
  },
  {
    to: '/learn/event',
    name: '대구문화이벤트',
    active: true,
  },
];

const move = (menu) => {
  router.push(menu.to);
};
</script>
<style scoped>
li {
  @apply font-bold text-lg;
}
</style>
