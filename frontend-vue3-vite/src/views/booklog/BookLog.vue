<template>
  <div class="flex mx-5">
    <div class="mx-1 w-1/5 mt-10">
      <div class="mx-3 my-3 border-b-slate-800" v-for="menu in menus" :key="menu.id">
        <p :class="{ 'selected-sub-menu': menu.selected, 'sub-menu': !menu.selected }" @click="select(menu.id)">
          <RouterLink :to="menu.link">{{ menu.name }}</RouterLink>
        </p>
      </div>
    </div>
    <RouterView />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router';
import router from '../../router';

const menus = ref([
  {
    id: 0,
    link: '/booklog/bookshelf',
    name: '책장',
    selected: true,
  },
  {
    id: 1,
    link: '/booklog/book-review',
    name: '리뷰',
    selected: false,
  },
  {
    id: 3,
    link: '/booklog/book-highlight',
    name: '하이라이트',
    selected: false,
  },
  {
    id: 4,
    link: '/booklog/book-favorite',
    name: '좋아하는 문장',
    selected: false,
  },
]);

const init = () => {
  const menu = menus.value.find((menu) => menu.id === 0);
  menu.selected = true;
  router.push(menu.link);
};
init();

const select = (id) => {
  menus.value.forEach((menu) => {
    if (menu.id !== id) menu.selected = false;
    else menu.selected = true;
  });
};
</script>

<style scoped>
.sub-menu {
  @apply border-b border-b-slate-400 py-3 hover:text-slate-500;
}
.selected-sub-menu {
  @apply border-b-4 border-b-slate-800 py-3 font-bold;
}
</style>
